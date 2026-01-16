package projectConstruction1.data;

import java.util.stream.Stream;

public class UserRepository {

    private UserRepository() {
    }

    public static Stream<User> validUsers() {
        return Stream.of(
                new User("wogib54456@emaxasp.com", "Qwerty1!", null),
                new User("mojib64712@emaxasp.com", "qWERTY1!", null)
        );
    }

    public static Stream<User> invalidEmailUsers() {
        return Stream.of(
                new User("samplestesgreencity.com", "Qwerty1!",
                        "Please check that your e-mail address is indicated correctly"),
                new User("user@", "Qwerty1!",
                        "Please check that your e-mail address is indicated correctly")
        );
    }

    public static Stream<User> generalErrorUsers() {
        return Stream.of(
                new User("mojib64712@emaxasp.com", "Qwerty1!",
                        "Bad password"),
                new User("disova5432@gavrom.com", "2345ERTYhgjkl!",
                        "Bad email or password")
        );
    }
}

