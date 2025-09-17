package tacos.web.repository;

import tacos.web.entities.Ingredient;

public interface IJdbcIngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}

