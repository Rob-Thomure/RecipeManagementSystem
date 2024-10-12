package recipes;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class RecipesController {
    private final RecipesRepository recipesRepository;
    //private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public RecipesController(RecipesRepository recipesRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.recipesRepository = recipesRepository;
        //this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/api/recipe/new")
    public ResponseEntity<Map<String, Long>> saveRecipe(@AuthenticationPrincipal UserDetails userDetails,
                                                        @Valid @RequestBody Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        String createdBy = userDetails.getUsername();
        recipe.setCreatedBy(createdBy);
        recipesRepository.save(recipe);
        Map<String, Long> idResponse = new HashMap<>();
        idResponse.put("id", recipe.getId());
        return ResponseEntity.ok(idResponse);
    }

    @PutMapping(path = "api/recipe/{id}")
    public ResponseEntity<Map<String, Long>> updateRecipe(@AuthenticationPrincipal UserDetails userDetails,
                                                          @PathVariable("id") long id,
                                                          @Valid @RequestBody Recipe recipe) {
        Recipe currentRecipe = recipesRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (userDetails.getUsername().matches(currentRecipe.getCreatedBy())) {
            recipe.setId(id);
            recipe.setDate(LocalDateTime.now());
            recipe.setCreatedBy(userDetails.getUsername());
            recipesRepository.save(recipe);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
        }
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
    public ResponseEntity<Recipe> deleteRecipe(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable("id") long id) {
        Recipe recipe = recipesRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (recipe.getCreatedBy().matches(userDetails.getUsername())) {
            return deleteRecipeInRepository(id);
        }
        else
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
    }

    private ResponseEntity<Recipe> deleteRecipeInRepository(long id) {
        recipesRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/api/register")
    public ResponseEntity<AppUser> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        if (userDoesNotExist(registrationRequest)
                && AppUser.isValidEmail(registrationRequest.email())
                && AppUser.isValidPassword(registrationRequest.password())) {
            AppUser appUser = new AppUser();
            appUser.setUsername(registrationRequest.email());
            appUser.setPassword(passwordEncoder.encode(registrationRequest.password()));
            appUser.setAuthority(registrationRequest.email());
            appUserRepository.save(appUser);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean userDoesNotExist(RegistrationRequest registrationRequest) {
        return !appUserRepository.existsAppUserByUsername(registrationRequest.email());
    }







}
