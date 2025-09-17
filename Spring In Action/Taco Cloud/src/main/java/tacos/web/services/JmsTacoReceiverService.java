package tacos.web.services;

import jakarta.jms.Destination;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.web.entities.Taco;

@Service
public class JmsTacoReceiverService implements TacoReceiverService {

    private JmsTemplate jmsTemplate;
    private Destination destination;

    public JmsTacoReceiverService(JmsTemplate jmsTemplate, Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    @Override
    public Taco receiveTaco() {
        return (Taco) jmsTemplate.receiveAndConvert(destination);
    }

//    @JmsListener(destination = "tacocloud.taco.queue")
//    public void receiveTaco(Taco taco) {
//    }
}
