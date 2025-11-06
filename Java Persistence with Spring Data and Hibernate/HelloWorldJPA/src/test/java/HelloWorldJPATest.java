import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sia.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class HelloWorldJPATest {

    @Test
    public void storeLoadMessage() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch02");

        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Message msg = new Message();
            msg.setText("Hello World");

            em.persist(msg);

            //INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World!')
            em.getTransaction().commit();

            em.getTransaction().begin();

            // SELECT * FROM MESSAGE
            List<Message> msgs = em.createQuery("select m from Message m").getResultList();

            msgs.get(msgs.size() - 1).setText("Hello World 2");

            //UPDATE MESSAGE set TEXT = 'Hello World from JPA!'
            em.getTransaction().commit();

            Assertions.assertAll(
                    () -> Assertions.assertEquals(1, msgs.size()),
                    () -> Assertions.assertEquals("Hello World 2", msgs.get(0).getText())
            );

            em.close();

        } finally {
            emf.close();
        }
    }
}
