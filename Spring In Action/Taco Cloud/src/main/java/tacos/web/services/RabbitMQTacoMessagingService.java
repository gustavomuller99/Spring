package tacos.web.services;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQTacoMessagingService {

    private RabbitTemplate rabbitTemplate;
    private MessageConverter messageConverter;

    public RabbitMQTacoMessagingService(RabbitTemplate rabbitTemplate, MessageConverter messageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
    }

    public void sendMessage(String m) {
        MessageConverter converter = rabbitTemplate.getMessageConverter();
        MessageProperties props = new MessageProperties();
        props.setHeader("X_ORDER_SOURCE", "WEB");
        Message message = converter.toMessage(m, props);
        rabbitTemplate.send("tacocloud.order", message);
    }

    public void sendMessageConvert(String m) {
        rabbitTemplate.convertAndSend("tacocloud.order", m,
                message -> {
                    MessageProperties props = message.getMessageProperties();
                    props.setHeader("X_ORDER_SOURCE", "WEB");
                    return message;
                }
        );
    }

    public String receiveMessage() throws AmqpException {
        Message message = rabbitTemplate.receive("tacocloud.order");
        return message != null
                ? (String) messageConverter.fromMessage(message)
                : null;
    }

    public String receiveMessageConvert() throws AmqpException {
        return (String) rabbitTemplate.receiveAndConvert("tacocloud.order");
    }

    @RabbitListener(queues = "tacocloud.order")
    public void receiveMessageListener(String m) {
        m.toLowerCase();
    }
}
