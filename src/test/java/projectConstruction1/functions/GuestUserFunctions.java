package projectConstruction1.functions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import projectConstruction1.utils.logging.BaseLogger;
import projectConstruction1.utils.wait.WaitActions;

import static projectConstruction1.runner.TestRunner.BASE_URL;

public class GuestUserFunctions extends BaseLogger {

    private final WebDriver driver;
    private final WaitActions waitActions;

    private final By signInIcon =
            By.cssSelector("[role='menu'] img.ubs-header-sing-in-img");
    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    private final By submitButton =
            By.xpath("//button[@type='submit']");
    private final By emailError =
            By.cssSelector("#email-err-msg app-error div");
    private final By generalError =
            By.cssSelector(".alert-general-error");
    private final By userMenuIcon =
            By.cssSelector("[role='menu'] ul#header_user-wrp");
    private final By signOut =
            By.xpath("//li[@aria-label='sign-out']");

    public GuestUserFunctions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.waitActions = new WaitActions(driver, wait);
    }

    public void openSignInForm() {
        waitActions.clickable(signInIcon).click();
    }

    public void login(String email, String password) {
        log.info("Logging in user: {}", email);
        waitActions.visible(emailInput).sendKeys(email);
        waitActions.textPresent(emailInput, email);
        waitActions.visible(passwordInput).sendKeys(password);
        waitActions.visible(submitButton).click();
    }

    public String getEmailError() {
        return waitActions.visible(emailError).getText();
    }

    public String getGeneralError() {
        return waitActions.visible(generalError).getText();
    }

    public void logoutIfNeeded() {
        driver.get(BASE_URL);
        if (driver.findElements(signInIcon).isEmpty()) {
            waitActions.clickable(userMenuIcon).click();
            waitActions.clickable(signOut).click();
            waitActions.visible(signInIcon);
        }
    }
}
