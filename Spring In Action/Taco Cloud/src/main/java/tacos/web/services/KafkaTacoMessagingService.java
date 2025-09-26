package tacos.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaTacoMessagingService {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaTacoMessagingService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        this.kafkaTemplate.send("tacocloud.orders.topic2", message);
    }

//    @KafkaListener(topics = "tacocloud.orders.topic2", groupId = "group")
//    public void receiveMessage(String message) {
//        message.toLowerCase();
//    }
}
