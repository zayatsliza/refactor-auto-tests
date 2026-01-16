package projectConstruction1.runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import projectConstruction1.testExtention.TestLoggingExtension;
import projectConstruction1.utils.logging.BaseLogger;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(TestLoggingExtension.class)
public abstract class TestRunner extends BaseLogger {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    public static final String BASE_URL =
            "https://www.greencity.cx.ua/#/greenCity";

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 2);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().window().setSize(new Dimension(1264, 798));
        driver.get(BASE_URL);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
