package recipes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private long id;

    private String username;

    private String password;

    private String authority;


    public AppUser() {
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean isValidEmail(String email) {
        if (null == email || email.isBlank())
            return false;
        String[] splitAtSymbol = email.split("@");
        if (splitAtSymbol.length != 2)
            return false;
        String[] splitAtDot = splitAtSymbol[1].split("\\.");
        return splitAtDot.length == 2;
    }

    public static boolean isValidPassword(String email) {
        return email.length() >= 8 && !email.isBlank();
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
