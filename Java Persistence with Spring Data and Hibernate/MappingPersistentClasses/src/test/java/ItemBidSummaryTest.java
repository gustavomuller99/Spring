import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;
import sia.subselect.model.Bid;
import sia.subselect.model.Item;
import sia.subselect.model.ItemBidSummary;


public class ItemBidSummaryTest {

    @Test
    public void itemBidSummaryTest() {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("ch05.generator");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Item item = new Item();
            item.setName("Some Item");
            item.setAuctionEnd(SpringDataJPATest.tomorrow());

            Bid bid1 = new Bid(new BigDecimal(1000.0), item);
            Bid bid2 = new Bid(new BigDecimal(1100.0), item);

            em.persist(item);
            em.persist(bid1);
            em.persist(bid2);

            em.getTransaction().commit();
            em.getTransaction().begin();

            TypedQuery<ItemBidSummary> query =
                    em.createQuery("select ibs from ItemBidSummary ibs where ibs.itemId = :id",
                            ItemBidSummary.class);
            ItemBidSummary itemBidSummary =
                    query.setParameter("id", 1000L).getSingleResult();

            assertAll(
                    () -> assertEquals(1000, itemBidSummary.getItemId()),
                    () -> assertEquals("Some Item", itemBidSummary.getName()),
                    () -> assertEquals(2, itemBidSummary.getNumberOfBids())
            );

            em.getTransaction().commit();

        } finally {
            em.close();
            emf.close();
        }
    }
}