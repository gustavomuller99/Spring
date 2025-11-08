import configuration.SpringDataConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sia.Message;
import sia.repositories.MessageRepository;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringDataConfiguration.class)
public class HelloWorldSpringDataJPATest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void storeLoadMessage() {
        Message message = new Message();
        message.setText("Hello World from Spring Data JPA");

        messageRepository.save(message);

        List<Message> messages = (List<Message>) messageRepository.findAll();

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, messages.size()),
                () -> Assertions.assertEquals("Hello World from Spring Data JPA", messages.get(0).getText())
        );
    }

}
