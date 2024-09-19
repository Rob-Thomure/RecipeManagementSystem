package recipes;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipesRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findAll();
}
