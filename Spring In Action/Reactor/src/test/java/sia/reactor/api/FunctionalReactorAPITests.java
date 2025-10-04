package sia.reactor.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FunctionalReactorAPITests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void functionalReactorTestFunctionalTest() {

        webTestClient.get().uri("/testFunctional")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0]").isEqualTo(1)
                .jsonPath("$[1]").isEqualTo(2)
                .jsonPath("$[2]").isEqualTo(3);

        webTestClient.get().uri("/testFunctional")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(List.class)
                .contains(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Test
    public void functionalReactorTestPostTest() {

        webTestClient.post().uri("/testPostFunctional")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(1), Integer.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Integer.class)
                .isEqualTo(1);
    }

    @Test
    public void functionalWebClientSubscribeTest() {
        WebClient webClient = WebClient.create("http://localhost:8080");

        Flux<Integer> integers = webClient
                .get()
                .uri("/testFunctional")
                .retrieve()
                .bodyToFlux(Integer.class);

        integers.subscribe(integer -> System.out.println(integer));
    }

    @Test
    public void functionalWebClientSubscribePostTest() {
        WebClient webClient = WebClient.create("http://localhost:8080");

        Mono<Integer> data = Mono.just(2);

        Mono<Integer> result = webClient
                .post()
                .uri("/testPostFunctional")
                .body(data, Integer.class)
                .retrieve()
                .bodyToMono(Integer.class);

        result.subscribe(integer -> {
            assert integer == 2;
        });
    }

    @Test
    public void functionalWebClientSubscribeExchangeTest() {
        WebClient webClient = WebClient.create("http://localhost:8080");

        Flux<Integer> integers = webClient
                .get()
                .uri("/testFunctional")
                .exchangeToFlux(cr -> cr.bodyToFlux(Integer.class));

        integers.subscribe(System.out::println);
    }
}
