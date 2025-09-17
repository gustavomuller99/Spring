package tacos.web.assemblers;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import tacos.web.DTOs.IngredientModel;
import tacos.web.entities.Ingredient;
import tacos.web.rest.IngredientController;

public class IngredientModelAssembler implements RepresentationModelAssembler<Ingredient, IngredientModel> {

    @Override
    public IngredientModel toModel(Ingredient ingredient) {
        IngredientModel ingredientModel = new IngredientModel(ingredient);

        ingredientModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(IngredientController.class).getIngredient(ingredient.getId()))
                .withSelfRel());

        return ingredientModel;
    }
}
