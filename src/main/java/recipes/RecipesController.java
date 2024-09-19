package recipes;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class RecipesController {
    private final RecipesRepository recipesRepository;
    public RecipesController(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    @PostMapping(path = "/api/recipe/new")
    public ResponseEntity<Map<String, Long>> saveRecipe(@Valid @RequestBody Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        recipesRepository.save(recipe);
        Map<String, Long> idResponse = new HashMap<>();
        idResponse.put("id", recipe.getId());
        return ResponseEntity.ok(idResponse);
    }

    @PutMapping(path = "api/recipe/{id}")
    public ResponseEntity<Map<String, Long>> updateRecipe(@PathVariable("id") long id, @Valid @RequestBody Recipe recipe) {
        recipesRepository.findById(id).orElseThrow(NoSuchElementException::new);
        recipe.setId(id);
        recipe.setDate(LocalDateTime.now());
        recipesRepository.save(recipe);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/api/recipe/search/")
    public ResponseEntity<List<Recipe>> searchRecipe(@RequestParam(value = "category", required = false) String category,
                                                     @RequestParam(value = "name", required = false) String name) {
        List<Recipe> recipes = recipesRepository.findAll();
        if (isZeroParameters(category, name) || isMoreThanOneParameter(category, name))
            return ResponseEntity.badRequest().build();
        if (null != category)
            return ResponseEntity.ok(recipes.stream()
                    .filter(recipe -> recipe.getCategory().equalsIgnoreCase(category))
                    .sorted(Comparator.comparing(Recipe::getDate).reversed())
                    .toList());
        else
            return ResponseEntity.ok(recipes.stream()
                    .filter(recipe -> recipe.getName().toLowerCase().contains(name.toLowerCase()))
                    .sorted(Comparator.comparing(Recipe::getDate).reversed())
                    .toList());
    }

    private boolean isZeroParameters(String category, String name) {
        return  null == category && null == name;
    }

    private boolean isMoreThanOneParameter(String category, String name) {
        return null != category && null != name;
    }

    @GetMapping(path = "/api/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("id") long id) {
        Optional<Recipe> optionalRecipe = recipesRepository.findById(id);
        return optionalRecipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/api/recipe/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable("id") long id) {
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
