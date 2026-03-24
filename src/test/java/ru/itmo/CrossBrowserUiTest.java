package ru.itmo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import ru.itmo.browser.BrowserType;
import ru.itmo.pages.HomePage;
import ru.itmo.pages.ResultsPage;

public class CrossBrowserUiTest extends BaseUiTest {

    @Test
    void tc21ChromeSearchSmokeShouldWork() {
        Assumptions.assumeTrue(browserType == BrowserType.CHROME, "Тест предназначен для Chrome");

        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("2+2")
                .submitWithButton()
                .waitUntilOpenedForQuery("2+2");

        assertTrue(resultsPage.getCurrentUrl().contains("/input"), "В Chrome поиск должен открывать страницу результатов");
        assertTrue(resultsPage.getTitle().contains("2+2"), "В Chrome заголовок должен соответствовать запросу");
    }

    @Test
    void tc22FirefoxSearchSmokeShouldWork() {
        Assumptions.assumeTrue(browserType == BrowserType.FIREFOX, "Тест предназначен для Firefox");

        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("2+2")
                .submitWithButton()
                .waitUntilOpenedForQuery("2+2");

        assertTrue(resultsPage.hasPod("Result:"), "В Firefox поиск должен работать");
    }
}
