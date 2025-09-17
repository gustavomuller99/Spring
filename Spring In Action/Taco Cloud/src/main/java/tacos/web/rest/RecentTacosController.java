package tacos.web.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import tacos.web.DTOs.TacoModel;
import tacos.web.assemblers.TacoModelAssembler;
import tacos.web.entities.Taco;
import tacos.web.repository.TacoRepository;

import java.util.List;

@RepositoryRestController
public class RecentTacosController {
    private TacoRepository tacoRepository;

//    @Bean
//    public RepresentationModelProcessor<PagedModel<TacoModel>> tacoProcessor(EntityLinks links) {
//        return model -> {
//            model.add(links.linkFor(Taco.class).slash("recent").withRel("recents"));
//            return model;
//        };
//    }

    public RecentTacosController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(value = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<TacoModel>> recentTacos() {
        Pageable pageRequest = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepository.findAll(pageRequest).toList();

        TacoModelAssembler tacoModelAssembler = new TacoModelAssembler();
        List<TacoModel> tacoModels = tacos.stream()
                .map(tacoModelAssembler::toModel).toList();

        CollectionModel<TacoModel> collectionModel = CollectionModel.of(tacoModels);
        collectionModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class).recentTacos())
                .withRel("recent"));

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
}
