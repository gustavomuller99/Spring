package sia.reactor.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

//@Configuration
//public class IngredientsFunctionalController {
//
//    @Autowired
//    private IngredientsRepository ingredientsRepository;
//
//    @Bean
//    public RouterFunction<?> routerFunction() {
//        return route(GET("/functional/recent"), this::recents)
//                .andRoute(POST("/functional"), this::postIngredient);
//    }
//
//    public Mono<ServerResponse> recents(ServerRequest request) {
//        return ServerResponse.ok()
//                .body(ingredientsRepository.findAll().take(12), Ingredient.class);
//    }
//
//    public Mono<ServerResponse> postIngredient(ServerRequest request) {
//        Mono<Ingredient> ingredient = request.bodyToMono(Ingredient.class);
//        Mono<Ingredient> savedIngredient = ingredientsRepository.save(ingredient);
//        return ServerResponse
//                .created(URI.create(
//                        "http://localhost:8080/functional" +
//                                savedIngredient.getId()))
//                .body(savedIngredient, Ingredient.class);
//    }
//}
