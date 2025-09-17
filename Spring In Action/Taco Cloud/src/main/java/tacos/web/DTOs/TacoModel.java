package tacos.web.DTOs;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import tacos.web.assemblers.IngredientModelAssembler;
import tacos.web.entities.Taco;
import java.util.Date;
import java.util.List;

public class TacoModel extends RepresentationModel<TacoModel> {

    @Getter
    private final String name;

    @Getter
    private final Date createdAt;

    @Getter
    private final List<IngredientModel> ingredients;

    public TacoModel(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();

        IngredientModelAssembler ingredientModelAssembler = new IngredientModelAssembler();
        this.ingredients = taco.getIngredients()
                .stream()
                .map(ingredientModelAssembler::toModel)
                .toList();
    }
}
