package sia.reactor.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

//@RestController
//@RequestMapping(path = "/design", produces = "application/json")
//@CrossOrigin(origins="*")
//public class IngredientsController {
//
//    private final IngredientsRepository ingredientsRepository;
//
//    public IngredientsController(IngredientsRepository ingredientsRepository) {
//        this.ingredientsRepository = ingredientsRepository;
//    }
//
//    @GetMapping("/recent")
//    public Flux<Ingredient> ingredients() {
//        return Flux.fromIterable(ingredientsRepository.findAll()).take(12);
//    }
//
//    @GetMapping("/{id}")
//    public Mono<Ingredient> ingredientById(@PathVariable("id") Long id) {
//        return Mono.just(ingredientsRepository.findById(id).get());
//    }
//
//    @PostMapping(consumes="application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<Ingredient> postIngredient(@RequestBody Mono<Ingredient> ingredientMono) {
//        return ingredientsRepository.saveAll(ingredientMono).next();
//    }
//}
