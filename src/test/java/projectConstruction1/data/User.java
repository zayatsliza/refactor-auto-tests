package projectConstruction1.data;

public class User {

    private final String email;
    private final String password;
    private final String expectedMessage;

    public User(String email, String password, String expectedMessage) {
        this.email = email;
        this.password = password;
        this.expectedMessage = expectedMessage;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getExpectedMessage() {
        return expectedMessage;
    }
}
