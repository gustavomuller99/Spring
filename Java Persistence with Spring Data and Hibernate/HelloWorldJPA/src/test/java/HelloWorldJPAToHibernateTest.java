import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sia.Message;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HelloWorldJPAToHibernateTest {

    private static SessionFactory getSessionFactory(EntityManagerFactory emf) {
        return emf.unwrap(SessionFactory.class);
    }

    private static EntityManagerFactory getEntityManagerFactory() {
        Configuration cfg = new Configuration();
        cfg.configure("").addAnnotatedClass(Message.class);

        Map<String, String> properties = new HashMap<String, String>();
        Enumeration<?> propertyNames = cfg.getProperties().propertyNames();

        while (propertyNames.hasMoreElements()) {
            String key = (String) propertyNames.nextElement();
            properties.put(key, cfg.getProperties().getProperty(key));
        }

        return Persistence.createEntityManagerFactory("ch02", properties);
    }

}
