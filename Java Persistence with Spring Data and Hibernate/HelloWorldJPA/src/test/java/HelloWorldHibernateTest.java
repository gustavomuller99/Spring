import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sia.Message;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class HelloWorldHibernateTest {

    private static SessionFactory createSessionFactory() {
        Configuration cfg = new Configuration();
        cfg.configure().addAnnotatedClass(Message.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        return cfg.buildSessionFactory(serviceRegistry);
    }

    @Test
    public void storeLoadMessage() {

        try (SessionFactory sessionFactory = createSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            Message message = new Message();
            message.setText("Hello World from Hibernate");

            session.persist(message);

            // INSERT into MESSAGE (ID, TEXT)
            // values (1, 'Hello World from Hibernate!')
            session.getTransaction().commit();

            session.beginTransaction();

            CriteriaQuery<Message> criteriaQuery = session.getCriteriaBuilder().createQuery(Message.class);
            criteriaQuery.from(Message.class);

            // SELECT * from MESSAGE
            List<Message> messages = session.createQuery(criteriaQuery).getResultList();

            session.getTransaction().commit();

            Assertions.assertAll(
                    () -> Assertions.assertEquals(1, messages.size()),
                    () -> Assertions.assertEquals("Hello World from Hibernate", messages.get(0).getText())
            );
        }
    }

}
