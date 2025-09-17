package tacos.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import tacos.web.entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {
    
}
