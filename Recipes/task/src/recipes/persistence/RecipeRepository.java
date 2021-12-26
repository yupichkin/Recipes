package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import recipes.business.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findRecipeById(Long id); //TODO: remove because already exist findByID
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
