package tacos.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacos.web.entities.Ingredient;
import tacos.web.repository.IngredientRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/ingredient", produces = "application/json")
public class IngredientController {
    public final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("{id}")
    public Ingredient getIngredient(@PathVariable String id) {
        return ingredientRepository.findById(id).get();
    }

    @GetMapping("/getAll")
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }
}
