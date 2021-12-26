package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.business.Recipe;
import recipes.business.RecipeService;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class Controller {
    @Autowired
    RecipeService recipeService;

    @PostMapping("/new")
    public Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails details) {
        recipe.setOwnerEmail(details.getUsername());
        Recipe savedRecipe = recipeService.saveRecipe(recipe);
        return Map.of("id", savedRecipe.getId()); //return json with single field - "id"
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> putRecipe(@Valid @RequestBody Recipe recipe, @PathVariable Long id, @AuthenticationPrincipal UserDetails details) {
        if (!recipeService.isRecipeExistInDb(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!recipeService.isOwnerOfRecipe(details.getUsername(), id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        recipe.setOwnerEmail(details.getUsername());

        recipeService.updateRecipeById(recipe, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable Long id, @AuthenticationPrincipal UserDetails details) {
        if(recipeService.isRecipeExistInDb(id)) {
            return recipeService.getRecipeById(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/search/", params = "category")
    public List<Recipe> getRecipesByCategory( @RequestParam String category, @AuthenticationPrincipal UserDetails details) {
        return recipeService.findRecipesByCategory(category);
    }

    @GetMapping(value = "/search/", params = "name")
    public List<Recipe> getRecipesByNameContaining(@RequestParam String name, @AuthenticationPrincipal UserDetails details) {
        return recipeService.findRecipesByNameContaining(name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal UserDetails details) {
        if(!recipeService.isRecipeExistInDb(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(!recipeService.isOwnerOfRecipe(details.getUsername(), id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        recipeService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
