import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestSamples3 {
    @FindBy(css = "[role=\"menu\"] img.ubs-header-sing-in-img")
    private WebElement signInButton;
    @FindBy(css = "app-sign-in h1")
    private WebElement welcomeText;
    @FindBy(css = "app-sign-in h2")
    private WebElement signInDetailsText;
    @FindBy(css = ".sign-in-form label[for=email]")
    private WebElement emailLabel;
    @FindBy(id = "email")
    private WebElement emailInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement signInSubmitButton;
    @FindBy(css=".mat-simple-snackbar > span")
    private WebElement result;
    @FindBy(css = ".alert-general-error")
    private WebElement errorMessage;
    @FindBy(xpath = "//*[@id=\"pass-err-msg\"]/app-error/div")
    private WebElement errorPassword;
    @FindBy(css= "[role=\"menu\"] ul#header_user-wrp")
    private WebElement userMenuButton;
    @FindBy(xpath="//li[@aria-label='sign-out']")
    private WebElement signOutOption;
    @FindBy(xpath = "//*[@id=\"email-err-msg\"]/app-error/div")
    private WebElement errorEmail;

    private static WebDriver driver;
    private static WebDriverWait wait;

    private static final String BASE_URL = "https://www.greencity.cx.ua/#/greenCity";

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 2); // BLOCK

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.get(BASE_URL);
        driver.manage().window().setSize(new Dimension(1264, 798));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @BeforeEach
    public void initPageElements() {
        PageFactory.initElements(driver, this);
    }

    @BeforeEach
    void ensureLoggedOut() throws InterruptedException {
        driver.get(BASE_URL);

        if (driver.findElements(By.cssSelector("[role=\"menu\"] img.ubs-header-sing-in-img")).isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(userMenuButton));
            userMenuButton.click();
            signOutOption.click();
            wait.until(ExpectedConditions.visibilityOf(signInButton));
        }
    }

    @Test
    public void verifyTitle() {
        assertThat(driver.getTitle(), containsString("GreenCity"));
    }

    @ParameterizedTest
    @CsvSource({
            "wogib54456@emaxasp.com, Qwerty1!",
            "mojib64712@emaxasp.com, qWERTY1!"
    })
    public void signIn(String email, String password) {
        signInButton.click();
        wait.until(ExpectedConditions.visibilityOf(welcomeText));
        assertThat(welcomeText.getText(), is("Welcome back!"));
        assertThat(signInDetailsText.getText(), is("Please enter your details to sign in."));
        assertThat(emailLabel.getText(), is("Email"));

        emailInput.sendKeys(email);
        assertThat(emailInput.getAttribute("value"), is(email));

        passwordInput.sendKeys(password);
        assertThat(passwordInput.getAttribute("value"), is(password));

        signInSubmitButton.click();
    }

    @ParameterizedTest
    @CsvSource({
            "samplestesgreencity.com, uT346^^^erw, Please check that your e-mail address is indicated correctly",
            "user@, Qwerty1!, Please check that your e-mail address is indicated correctly"
    })
    public void signInNotValid(String email, String password, String message) {
        wait.pollingEvery(Duration.ofMillis(500)).until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        assertThat(errorEmail.getText(), is(message));
    }

    @ParameterizedTest
    @CsvSource({
            "Email is required., This field is required"
    })
    void signInWithEmptyFields(String emailErrorMsg, String passwordErrorMsg) {
        signInButton.click();
        emailInput.click();
        passwordInput.click();
        signInSubmitButton.click();

        wait.until(ExpectedConditions.visibilityOf(errorEmail));
        wait.until(ExpectedConditions.visibilityOf(errorPassword));

        assertThat(errorEmail.getText(), is(emailErrorMsg));
        assertThat(errorPassword.getText(), is(passwordErrorMsg));
        assertThat(String.valueOf(!signInSubmitButton.isEnabled()), true);
    }

    @ParameterizedTest
    @CsvSource({
            "mojib64712@emaxasp.com, Qwerty1!, Bad password",
            "disova5432@gavrom.com, 2345ERTYhgjkl!, Bad email or password"
    })
    void signInWithGeneralErrors(String email, String password, String errorMsg) {
        signInButton.click();
        emailInput.sendKeys(email);
        assertThat(emailInput.getAttribute("value"), is(email));
        passwordInput.sendKeys(password);
        assertThat(passwordInput.getAttribute("value"), is(password));
        signInSubmitButton.click();
        assertThat(String.valueOf(errorMessage.isDisplayed()), true);
        wait.until(ExpectedConditions.textToBePresentInElement(errorMessage, errorMsg));
        assertThat(errorMessage.getText(),is(errorMsg));
    }

    @AfterEach
    void logOut() {
        driver.get(BASE_URL);

        if (driver.findElements(By.cssSelector("[role=\"menu\"] img.ubs-header-sing-in-img")).isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(userMenuButton));
            userMenuButton.click();
            signOutOption.click();
            wait.until(ExpectedConditions.visibilityOf(signInButton));
        }
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}