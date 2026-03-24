package ru.itmo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import ru.itmo.browser.BrowserSupport;
import ru.itmo.browser.BrowserType;
import ru.itmo.browser.DriverFactory;

public abstract class BaseUiTest {
    protected WebDriver driver;
    protected String baseUrl;
    protected BrowserType browserType;

    @BeforeEach
    void setUp() {
        browserType = BrowserType.fromSystemProperty();
        Assumptions.assumeTrue(
                BrowserSupport.isInstalled(browserType),
                browserType + " is not installed in the current environment"
        );
        baseUrl = System.getProperty("baseUrl", "https://www.wolframalpha.com/");
        driver = DriverFactory.createDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
