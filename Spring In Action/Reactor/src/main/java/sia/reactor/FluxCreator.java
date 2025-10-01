package sia.reactor;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class FluxCreator {

    public FluxCreator() {}

    public Flux<String> createFlux() {
        return Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
    }
}
