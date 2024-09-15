package recipes;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RecipesController {
    private final RecipesRepository recipesRepository;
    public RecipesController(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    @PostMapping(path = "/api/recipe/new")
    public ResponseEntity<Map<String, Long>> saveRecipe(@Valid @RequestBody Recipe recipe) {
        recipesRepository.save(recipe);
        Map<String, Long> idResponse = new HashMap<>();
        idResponse.put("id", recipe.getId());
        return ResponseEntity.ok(idResponse);
    }

    @GetMapping(path = "/api/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("id") long id) {
        Optional<Recipe> optionalRecipe = recipesRepository.findById(id);
        return optionalRecipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/api/recipe/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable("id") long id) {
        ResponseEntity<Recipe> responseEntity;
        if (recipesRepository.existsById(id))
            return deleteRecipeInRepository(id);
        else
            return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Recipe> deleteRecipeInRepository(long id) {
        recipesRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
