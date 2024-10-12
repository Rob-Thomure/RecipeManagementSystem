package recipes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

public class RecipeTest {

    @Test
    public void testDateTimeFormat() {
        LocalDateTime date = LocalDateTime.parse("2021-09-05T18:34:48.227624");
        System.out.println(date);
    }

    @Test
    public void testEmail() {
        String email = "Cook_Programmer@somewhere4.com";
        Assertions.assertTrue(validEmail(email));
    }



    private boolean validEmail(String email) {
        String[] splitAtSymbol = email.split("@");
        System.out.println("**** " + splitAtSymbol.length);
        if (splitAtSymbol.length != 2)
            return false;
        System.out.println("**** " + splitAtSymbol[1]);
        String[] splitAtDot = splitAtSymbol[1].split("\\.");
        System.out.println("**** " + splitAtDot.length);
        System.out.println(Arrays.toString(splitAtDot));
        return splitAtDot.length == 2;
    }

    @Test
    public void testBlank() {
        System.out.println("******");
        System.out.println("     ".isBlank());
    }


}
