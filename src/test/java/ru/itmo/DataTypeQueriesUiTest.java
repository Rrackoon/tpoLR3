package ru.itmo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.itmo.pages.HomePage;
import ru.itmo.pages.ResultsPage;

public class DataTypeQueriesUiTest extends BaseUiTest {

    @Test
    void tc06GeographyQueryShouldReturnBerlin() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("capital of Germany")
                .submitWithButton()
                .waitUntilOpenedForQuery("capital of Germany");

        assertTrue(resultsPage.hasPod("Result:"), "Должен быть pod Result");
        assertTrue(
                resultsPage.getFirstImageAltInPod("Result:").contains("Berlin"),
                "Результат географического запроса должен содержать Berlin"
        );
    }

    @Test
    void tc07WeatherQueryShouldShowWeatherBlocks() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("weather Paris")
                .submitWithButton()
                .waitUntilOpenedForQuery("weather Paris");

        assertTrue(resultsPage.pageContainsText("weather"), "На странице должны присутствовать погодные данные");
        assertTrue(
                resultsPage.pageContainsText("forecast")
                        || resultsPage.hasPodContaining("Today")
                        || resultsPage.hasPodContaining("Tonight"),
                "Должен отображаться блок прогноза погоды"
        );
    }

    @Test
    void tc08CurrencyConversionShouldShowEuroResult() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("10 USD to EUR")
                .submitWithButton()
                .waitUntilOpenedForQuery("10 USD to EUR");

        assertTrue(resultsPage.hasPod("Result:"), "Должен быть pod Result");
        assertTrue(
                resultsPage.getFirstImageAltInPod("Result:").contains("€"),
                "Результат конвертации должен содержать евро"
        );
    }

    @Test
    void tc09ScientificQueryShouldShowValueDescription() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("speed of light")
                .submitWithButton()
                .waitUntilOpenedForQuery("speed of light");

        assertTrue(resultsPage.getTitle().contains("speed of light"), "Должна открыться страница научного запроса");
        assertTrue(resultsPage.pageContainsText("m/s"), "Научный запрос должен возвращать значение в m/s");
    }
}
