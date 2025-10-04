package sia.reactor.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class FunctionalReactorAPI {

    @Bean
    public RouterFunction<ServerResponse> helloRouterFunction() {
        return route(GET("/hello"),
                request -> ok().body(Mono.just("Hello World!"), String.class))
                .andRoute(GET("/bye"),
                        request -> ok().body(Mono.just("See ya!"), String.class))
                .andRoute(GET("/testFunctional"),
                        this::testFunctional)
                .andRoute(POST("/testPostFunctional"),
                        this::testPostFunctional);
    }

    public Mono<ServerResponse> testFunctional(ServerRequest request) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return ServerResponse.ok()
                .body(Flux.fromIterable(numbers), Integer.class);
    }

    public Mono<ServerResponse> testPostFunctional(ServerRequest request) {
        Mono<Integer> saveInteger = request.bodyToMono(Integer.class);
        return ServerResponse.created(URI.create("http://localhost:8080/functional"))
                .body(saveInteger, Integer.class);
    }
}