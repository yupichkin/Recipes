package recipes.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.persistence.RecipeRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).get();
    }

    public List<Recipe> findRecipesByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }
    public List<Recipe> findRecipesByNameContaining(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public boolean isRecipeExistInDb(Long id) {
        return recipeRepository.findById(id).isPresent();
    }

    public boolean isOwnerOfRecipe(String ownerEmail, Long id) {
        Optional<Recipe> oldRecipe = recipeRepository.findById(id);
        if(oldRecipe.isEmpty()) {
            return false; //not found
        }
        return ownerEmail.equals(oldRecipe.get().getOwnerEmail());
    }

    public void updateRecipeById(Recipe recipe, Long id) {
        Optional<Recipe> oldRecipe = recipeRepository.findById(id);
        if(oldRecipe.isEmpty()) {
            return; //not found
        }
        recipe.setDate(LocalDateTime.now().toString());
        recipe.setId(oldRecipe.get().getId());
        recipeRepository.save(recipe);
    }


    public Recipe saveRecipe(Recipe recipe) {
        Recipe savedRecipe = new Recipe(recipe.getOwnerEmail(),
                recipe.getName(),
                recipe.getCategory(),
                LocalDateTime.now().toString(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getDirections());
        return recipeRepository.save(savedRecipe);
    }

    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }


}
