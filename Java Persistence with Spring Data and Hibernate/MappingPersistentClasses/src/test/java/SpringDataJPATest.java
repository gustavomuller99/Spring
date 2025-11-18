import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sia.generator.model.Item;
import sia.generator.repositories.ItemRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringDataConfiguration.class})
public class SpringDataJPATest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testJPA() {

    }

    @Test
    public void testSpringDataJPA() {
        Item item = new Item();

        item.setName("Item 1");
        item.setAuctionEnd(tomorrow());

        itemRepository.save(item);

        List<Item> items = (List<Item>) itemRepository.findAll();

        assertAll(
                () -> assertEquals(1, items.size()),
                () -> assertEquals("Item 1", items.get(0).getName())
        );
    }

    static Date tomorrow() {
        return new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
    }
}
