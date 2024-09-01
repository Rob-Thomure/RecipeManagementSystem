package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.Objects;

public class Recipe {

    @JsonIgnore
    private long id;

    private String name;
    private String description;
    private String[] ingredients;
    private String[] directions;

    private static long idIndex;

    public Recipe() {
        this.id = ++idIndex;
    }

    public Recipe(String name, String description, String[] ingredients, String[] directions) {
        this.id = ++idIndex;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static long getIdIndex() {
        return idIndex;
    }

    public static void setIdIndex(long idIndex) {
        Recipe.idIndex = idIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getDirections() {
        return directions;
    }

    public void setDirections(String[] directions) {
        this.directions = directions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && Objects.equals(name, recipe.name) &&
                Objects.equals(description, recipe.description) &&
                Objects.deepEquals(ingredients, recipe.ingredients) &&
                Objects.deepEquals(directions, recipe.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, Arrays.hashCode(ingredients), Arrays.hashCode(directions));
    }
}
