import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestSamples3_1 {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;

    private static final String BASE_URL =
            "https://www.greencity.cx.ua/#/greenCity";

    private final By signInIcon =
            By.cssSelector("[role='menu'] img.ubs-header-sing-in-img");

    private final By welcomeTitle =
            By.cssSelector("app-sign-in h1");

    private final By signInSubtitle =
            By.cssSelector("app-sign-in h2");

    private final By emailInput =
            By.id("email");

    private final By passwordInput =
            By.id("password");

    private final By submitButton =
            By.xpath("//button[@type='submit']");

    private final By emailError =
            By.cssSelector("#email-err-msg app-error div");

    private final By passwordError =
            By.cssSelector("#pass-err-msg app-error div");

    private final By generalError =
            By.cssSelector(".alert-general-error");

    private final By userMenu =
            By.cssSelector("[role='menu'] ul#header_user-wrp");

    private final By signOut =
            By.xpath("//li[@aria-label='sign-out']");


    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 2);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().window().setSize(new Dimension(1264, 798));
        driver.get(BASE_URL);
    }


    private WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement jsFind(By locator) {
        return (WebElement) js.executeScript(
                "return document.querySelector(arguments[0])",
                ((By.ByCssSelector) locator).toString().replace("By.cssSelector: ", "")
        );
    }

    private void openSignIn() {
        waitClickable(signInIcon).click();
        waitVisible(welcomeTitle);
    }

    private void fillCredentials(String email, String password) {
        waitVisible(emailInput).sendKeys(email);
        waitVisible(passwordInput).sendKeys(password);
    }

    private void submitSignIn() {
        waitVisible(submitButton).click();
    }

    private void logoutIfNeeded() {
        driver.get(BASE_URL);
        if (driver.findElements(signInIcon).isEmpty()) {
            waitClickable(userMenu).click();
            waitClickable(signOut).click();
            waitVisible(signInIcon);
        }
    }


    @BeforeEach
    void beforeEach() {
        logoutIfNeeded();
    }

    @Test
    void verifyTitle() {
        assertThat(driver.getTitle(), containsString("GreenCity"));
    }

    @ParameterizedTest
    @CsvSource({
            "wogib54456@emaxasp.com, Qwerty1!",
            "mojib64712@emaxasp.com, qWERTY1!"
    })
    void signInValid(String email, String password) {

        openSignIn();

        assertThat(waitVisible(welcomeTitle).getText(),
                is("Welcome back!"));
        assertThat(waitVisible(signInSubtitle).getText(),
                is("Please enter your details to sign in."));

        fillCredentials(email, password);
        submitSignIn();
    }

    @ParameterizedTest
    @CsvSource({
            "samplestesgreencity.com, Qwerty1!, Please check that your e-mail address is indicated correctly",
            "user@, Qwerty1!, Please check that your e-mail address is indicated correctly"
    })
    void signInInvalidEmail(String email, String password, String message) {

        openSignIn();
        fillCredentials(email, password);

        assertThat(waitVisible(emailError).getText(), is(message));
    }

    @Test
    void signInWithEmptyFields() {

        openSignIn();
        waitClickable(emailInput).click();
        waitClickable(passwordInput).click();
        submitSignIn();

        assertThat(waitVisible(emailError).getText(),
                is("Email is required."));
        assertThat(waitVisible(passwordError).getText(),
                is("This field is required"));
        assertThat(waitVisible(submitButton).isEnabled(), is(false));
    }

    @ParameterizedTest
    @CsvSource({
            "mojib64712@emaxasp.com, Qwerty1!, Bad password",
            "disova5432@gavrom.com, 2345ERTYhgjkl!, Bad email or password"
    })
    void signInWithGeneralError(String email, String password, String error) {

        openSignIn();
        fillCredentials(email, password);
        submitSignIn();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                generalError, error));

        assertThat(waitVisible(generalError).getText(), is(error));
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}