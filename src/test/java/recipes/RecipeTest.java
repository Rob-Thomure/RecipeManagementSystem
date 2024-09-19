package recipes;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class RecipeTest {

    @Test
    public void testDateTimeFormat() {
        LocalDateTime date = LocalDateTime.parse("2021-09-05T18:34:48.227624");
        System.out.println(date);
    }
}
