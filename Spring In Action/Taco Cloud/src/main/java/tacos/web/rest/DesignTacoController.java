package tacos.web.rest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.web.DTOs.TacoModel;
import tacos.web.assemblers.TacoModelAssembler;
import tacos.web.entities.Taco;
import tacos.web.repository.TacoRepository;
import org.springframework.hateoas.CollectionModel;
import tacos.web.services.JmsTacoReceiverService;
import tacos.web.services.RabbitMQTacoMessagingService;
import tacos.web.services.TacoMessagingService;
import tacos.web.services.TacoReceiverService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin("*")
public class DesignTacoController {

    private final TacoRepository tacoRepository;
    private final TacoMessagingService tacoMessagingService;
    private final TacoReceiverService tacoReceiverService;
    private final RabbitMQTacoMessagingService rabbitMQTacoMessagingService;

    public DesignTacoController(TacoRepository tacoRepository, TacoMessagingService tacoMessagingService, TacoReceiverService tacoReceiverService, RabbitMQTacoMessagingService rabbitMQTacoMessagingService) {
        this.tacoRepository = tacoRepository;
        this.tacoMessagingService = tacoMessagingService;
        this.tacoReceiverService = tacoReceiverService;
        this.rabbitMQTacoMessagingService = rabbitMQTacoMessagingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> get(@PathVariable Long id) {
        Optional<Taco> taco = tacoRepository.findById(id);
        return taco.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/recent")
    public CollectionModel<TacoModel> recentTacos() {
        Pageable pageRequest = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepository.findAll(pageRequest).toList();

        // using EntityModel
//        List<EntityModel<Taco>> tacoModels = tacos.stream()
//                .map(taco ->
//                        EntityModel.of(taco,
//                        WebMvcLinkBuilder
//                                .linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class).get(taco.getId())) // link para o taco
//                                .withSelfRel()))
//                .toList();
//
//        for (EntityModel<Taco> entityModel : tacoModels) {
//            assert entityModel.getContent() != null;
//            for (Ingredient ingredient : entityModel.getContent().getIngredients()) {
//                entityModel.add(WebMvcLinkBuilder
//                        .linkTo(WebMvcLinkBuilder.methodOn(IngredientController.class).getIngredient(ingredient.getId()))
//                        .withRel(ingredient.getName()));
//            }
//        }

        TacoModelAssembler tacoModelAssembler = new TacoModelAssembler();
        List<TacoModel> tacoModels = tacos.stream()
                .map(tacoModelAssembler::toModel).toList();

        CollectionModel<TacoModel> collectionModel = CollectionModel.of(tacoModels);
        collectionModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class).recentTacos())
                .withRel("recent"));

        return collectionModel;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        tacoRepository.save(taco);
        return taco;
    }

    @PutMapping(consumes = "application/json")
    public Taco putTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    public Taco patchTaco(@PathVariable Long id, @RequestBody Taco patch) {
        Taco taco = tacoRepository.findById(id).get();

        if (patch.getName() != null) {
            taco.setName(patch.getName());
        }
        if (patch.getIngredients() != null) {
            taco.setIngredients(patch.getIngredients());
        }
        if (patch.getCreatedAt() != null) {
            taco.setCreatedAt(patch.getCreatedAt());
        }

        tacoRepository.save(taco);
        return taco;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaco(@PathVariable Long id) {
        try {
            tacoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException _) { }
    }

    @PostMapping(path = "/sendTaco")
    public ResponseEntity<Object> sendTaco() {
        Taco taco = tacoRepository.findById(1L).get();
        tacoMessagingService.sendTaco(taco);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/receiveTaco")
    public ResponseEntity<Object> post() {
        Taco taco = tacoReceiverService.receiveTaco();
        return ResponseEntity.ok().body(taco);
    }

    @PostMapping(path = "/sendTacoRabbitMQ")
    public ResponseEntity<Object> sendMessageRabbitMQ() {
        rabbitMQTacoMessagingService.sendMessageConvert("newMessage");
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/getTacoRabbitMQ")
    public ResponseEntity<Object> getMessageRabbitMQ() {
        var result = rabbitMQTacoMessagingService.receiveMessage();
        return ResponseEntity.ok().body(result);
    }
}
