import org.junit.jupiter.api.Test;
import sia.generator.model.Item;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JPATest {

    @Test
    public void storeLoadItem() {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("ch05.generator");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Item item = new Item();
            item.setName("Some Item");
            item.setAuctionEnd(SpringDataJPATest.tomorrow());

            em.persist(item);

            em.getTransaction().commit();
            em.getTransaction().begin();

            List<Item> items =
                    em.createQuery("select i from Item i", Item.class).getResultList();
            //SELECT * from ITEM

            em.getTransaction().commit();

            assertAll(
                    () -> assertEquals(1, items.size()),
                    () -> assertEquals("Some Item", items.get(0).getName())
            );

        } finally {
            em.close();
            emf.close();
        }
    }
}
