package recipes;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Controller
public class RecipesController {
    private List<Recipe> recipes;

    {
        this.recipes = new ArrayList<>();
    }

    @PostMapping(path = "/api/recipe/new")
    public ResponseEntity<Map<String, Long>> saveRecipe(@RequestBody Recipe recipe) {
        recipes.add(recipe);
        Map<String, Long> idResponse = new HashMap<>();
        idResponse.put("id", recipe.getId());
        return ResponseEntity.ok(idResponse);
    }

    @GetMapping(path = "/api/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("id") long id) {
        Optional<Recipe> foundRecipe = recipes.stream()
                .filter(recipe -> recipe.getId() == id)
                .findAny();
        return foundRecipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
