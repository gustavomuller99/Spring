package tacos.web.services;

import jakarta.jms.Destination;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.web.entities.Taco;

@Service
public class JmsTacoMessagingService implements TacoMessagingService {

    private final JmsTemplate jmsTemplate;
    private final Destination destination;

    public JmsTacoMessagingService(JmsTemplate jmsTemplate, Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    @Override
    public void sendTaco(Taco taco) {
        jmsTemplate.convertAndSend(destination, taco,
                message -> {
                    message.setStringProperty("SOURCE", "WEB");
                    return message;
                });
    }


}
