package projectConstruction1.functions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import projectConstruction1.utils.WaitActions;

public class GuestUserFunctions {

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

    public GuestUserFunctions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.waitActions = new WaitActions(driver, wait);
    }

    public void openSignInForm() {
        waitActions.clickable(signInIcon).click();
    }

    public void login(String email, String password) {
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
}
