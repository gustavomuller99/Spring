package sia.reactor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootApplication
public class ReactorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactorApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            Flux<String> fruitFlux = Flux
                    .just("Apple", "Orange", "Grape", "Banana", "Strawberry");

            fruitFlux.subscribe(System.out::println);

            StepVerifier.create(fruitFlux)
                    .expectNext("Apple")
                    .expectNext("Orange")
                    .expectNext("Grape")
                    .expectNext("Banana")
                    .expectNext("Strawberry")
                    .verifyComplete();
        };
    }
}
