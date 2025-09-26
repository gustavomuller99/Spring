package sia.tacocloudemailintegration.Integration;

import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import jakarta.mail.Message;
import org.springframework.stereotype.Component;
import sia.tacocloudemailintegration.Data.Order;

@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<Order> {

    @Override
    protected AbstractIntegrationMessageBuilder<Order> doTransform(Message mailMessage) {
        Order tacoOrder = processPayload(mailMessage);
        return MessageBuilder.withPayload(tacoOrder);
    }

    private Order processPayload(Message mailMessage) {
        return new Order("email");  // placeholder
    }
}
