package tacos.web.DTOs;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import tacos.web.entities.Ingredient;

public class IngredientModel extends RepresentationModel<IngredientModel> {

    @Getter
    private final String name;

    @Getter
    private final Ingredient.Type type;

    public IngredientModel(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
