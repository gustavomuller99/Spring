package tacos.web.assemblers;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import tacos.web.DTOs.TacoModel;
import tacos.web.entities.Taco;
import tacos.web.rest.DesignTacoController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class TacoModelAssembler implements RepresentationModelAssembler<Taco, TacoModel> {

    @Override
    public TacoModel toModel(Taco taco) {
        TacoModel tacoModel = new TacoModel(taco);

        tacoModel.add(
                linkTo(methodOn(DesignTacoController.class).get(taco.getId()))
                .withSelfRel());

        return tacoModel;
    }
}
