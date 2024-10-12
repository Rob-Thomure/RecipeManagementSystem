package recipes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppUserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    RecipesRepository appUserService;

    @Autowired
    RecipesRepository recipesRepository;

    private static final AppUser appUser1 = new AppUser("emailOne", "123");
    private static final AppUser appUser3 = new AppUser("emailThree", "123");

    @Test
    @DisplayName("Post /api/register saves user ")
    void postUser_savesUser_returnsOK() {
        String url = "http://localhost:" + port + "/api/register";
        //ResponseEntity<AppUser> response = restTemplate.postForEntity(url, );


        Iterable<Recipe> iterable = recipesRepository.findAll();
        iterable.forEach(System.out::println);
    }

    @Test()
    @DisplayName("Test User DB Contents")
    void testUserDbContents() {
        System.out.println("******");
        //Iterable<AppUser> users = appUserService.findAll();
        //users.forEach(System.out::println);
    }



}
