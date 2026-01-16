package projectConstruction1.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import projectConstruction1.data.User;
import projectConstruction1.functions.GuestUserFunctions;
import projectConstruction1.runner.TestRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SignInTest extends TestRunner {

    private GuestUserFunctions signIn;

    @BeforeEach
    void init() {
        signIn = new GuestUserFunctions(driver, wait);
        driver.get(BASE_URL);
    }

    @ParameterizedTest
    @MethodSource("projectConstruction1.data.UserRepository#validUsers")
    void signInValid(User user) {
        // Arrange
        signIn.openSignInForm();

        // Act
        signIn.login(user.getEmail(), user.getPassword());

        // Assert
        assertThat(driver.getCurrentUrl(), is(BASE_URL));
    }

    @ParameterizedTest
    @MethodSource("projectConstruction1.data.UserRepository#invalidEmailUsers")
    void signInInvalid(User user) {
        signIn.openSignInForm();
        signIn.login(user.getEmail(), user.getPassword());

        assertThat(
                signIn.getEmailError(),
                is(user.getExpectedMessage())
        );
    }

    @ParameterizedTest
    @MethodSource("projectConstruction1.data.UserRepository#generalErrorUsers")
    void signInInvalidGeneral(User user) {
        signIn.openSignInForm();
        signIn.login(user.getEmail(), user.getPassword());

        assertThat(
                signIn.getGeneralError(),
                is(user.getExpectedMessage())
        );
    }

    @Test
    void verifyTitle() {
        Assertions.assertTrue(driver.getTitle().contains("GreenCity"));
    }
}
