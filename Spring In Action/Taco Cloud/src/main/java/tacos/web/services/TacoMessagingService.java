package tacos.web.services;

import tacos.web.entities.Taco;

public interface TacoMessagingService {
    void sendTaco(Taco taco);
}
