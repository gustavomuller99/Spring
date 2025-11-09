import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sia.Item;
import sia.Item_;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TestModelValidation {

    private static EntityManagerFactory emf;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("ch03.metamodel");
    }

    @Test
    public void testValidation() {
        javax.validation.ValidatorFactory factory = javax.validation.Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Item item = new Item();
        item.setName("Some Item");
        item.setAuctionEnd(new Date());

        Set<javax.validation.ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        javax.validation.ConstraintViolation<Item> constraintViolation = constraintViolations.iterator().next();

        String failedProperty = constraintViolation.getPropertyPath().iterator().next().getName();

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, constraintViolations.size()),
                () -> Assertions.assertEquals("auctionEnd", failedProperty),
                () -> {
                    if (Locale.getDefault().getLanguage().equals("en"))
                        Assertions.assertEquals("must be a future date",
                                constraintViolation.getMessage());
                }
        );
    }

    @Test
    public void testDynamicMetamodel() {
        Metamodel metamodel = emf.getMetamodel();
        Set<ManagedType<?>> managedTypes = metamodel.getManagedTypes();
        ManagedType<?> managedType = managedTypes.iterator().next();

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, managedTypes.size()),
                () -> Assertions.assertEquals(
                        Type.PersistenceType.ENTITY,
                        managedType.getPersistenceType()));

        SingularAttribute<?, ?> singleAttribute = managedType.getSingularAttribute("id", String.class);
        Assertions.assertFalse(singleAttribute.isOptional());

        SingularAttribute<?, ?> nameAttribute = managedType.getSingularAttribute("name");
        Assertions.assertAll(
                () -> Assertions.assertEquals(String.class, nameAttribute.getJavaType()),
                () -> Assertions.assertEquals(
                        Attribute.PersistentAttributeType.BASIC,
                        nameAttribute.getPersistentAttributeType()
                ));

        SingularAttribute<?, ?> dateAttribute = managedType.getSingularAttribute("auctionEnd");
        Assertions.assertAll(
                () -> Assertions.assertEquals(Date.class, dateAttribute.getJavaType()),
                () -> Assertions.assertFalse(dateAttribute.isCollection()),
                () -> Assertions.assertFalse(dateAttribute.isAssociation())
        );
    }

    @Test
    public void accessStaticMetamodel() {
        EntityManager em = emf.createEntityManager();
        deleteItems(em);
        persistItems(em);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> fromItem = query.from(Item.class);
        query.select(fromItem);
        List<Item> items = em.createQuery(query).getResultList();

        Assertions.assertEquals(2, items.size());
    }

    @Test
    public void testItemsPattern() {
        EntityManager em = emf.createEntityManager();
        deleteItems(em);
        persistItems(em);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> fromItem = query.from(Item.class);
        Path<String> namePath = fromItem.get("name");
        query.where(cb.like(namePath, cb.parameter(String.class, "pattern")));
        List<Item> items = em.createQuery(query).setParameter("pattern", "%Item 1%").getResultList();

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, items.size()),
                () -> Assertions.assertEquals("Item 1", items.iterator().next().getName()));
    }

    @Test
    public void testItemsPatternWithGeneratedClass() {
        EntityManager em = emf.createEntityManager();
        deleteItems(em);
        persistItems(em);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> fromItem = query.from(Item.class);
        Path<String> namePath = fromItem.get(Item_.name);
        query.where(cb.like(namePath, cb.parameter(String.class, "pattern")));
        List<Item> items = em.createQuery(query).setParameter("pattern", "%Item 1%").getResultList();

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, items.size()),
                () -> Assertions.assertEquals("Item 1", items.iterator().next().getName()));
    }

    private void deleteItems(EntityManager em) {
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Item> query = cb.createCriteriaDelete(Item.class);
        query.from(Item.class);
        em.createQuery(query).executeUpdate();
        em.getTransaction().commit();
    }

    private void persistItems(EntityManager em) {
        em.getTransaction().begin();
        Item item1 = new Item();
        item1.setName("Item 1");
        item1.setAuctionEnd(tomorrow());

        Item item2 = new Item();
        item2.setName("Item 2");
        item2.setAuctionEnd(tomorrow());

        em.persist(item1);
        em.persist(item2);
        em.getTransaction().commit();
    }

    private Date tomorrow() {
        return new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
    }
}
