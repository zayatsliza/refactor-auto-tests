package projectConstruction1.utils.wait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitActions {

    private final WebDriverWait wait;

    public WaitActions(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
    }

    public WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean textPresent(By locator, String text) {
        return wait.until(
                ExpectedConditions.textToBePresentInElementValue(locator, text)
        );
    }
}
