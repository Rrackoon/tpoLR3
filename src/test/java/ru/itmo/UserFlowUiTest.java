package ru.itmo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.itmo.pages.HomePage;
import ru.itmo.pages.ResultsPage;

public class UserFlowUiTest extends BaseUiTest {

    @Test
    void tc17RepeatedSearchShouldWork() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("2+2")
                .submitWithButton()
                .waitUntilOpenedForQuery("2+2")
                .searchAgainWithButton("5*5");

        assertTrue(resultsPage.getCurrentUrl().contains("/input"), "Повторный поиск должен оставлять пользователя на странице результатов");
        assertTrue(resultsPage.getInputText().contains("5*5"), "Во втором поиске должен сохраниться новый запрос");
        assertTrue(resultsPage.getTitle().contains("5*5"), "Второй поиск должен открыть страницу нового запроса");
    }

    @Test
    void tc18RefreshShouldPreserveResult() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("2+2")
                .submitWithButton()
                .waitUntilOpenedForQuery("2+2")
                .refreshAndWait();

        assertTrue(resultsPage.getCurrentUrl().contains("/input"), "После обновления должна оставаться страница результатов");
        assertTrue(resultsPage.getInputText().equals("2+2"), "После обновления запрос должен сохраниться");
        assertTrue(resultsPage.getTitle().contains("2+2"), "После обновления заголовок должен соответствовать запросу");
    }

    @Test
    void tc19MultipleQueriesInARowShouldWork() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("capital of Germany")
                .submitWithButton()
                .waitUntilOpenedForQuery("capital of Germany")
                .searchAgainWithButton("weather Paris")
                .searchAgainWithButton("10 USD to EUR");

        assertTrue(resultsPage.getCurrentUrl().contains("/input"), "После серии запросов должна оставаться страница результатов");
        assertTrue(resultsPage.getInputText().contains("10 USD to EUR"), "После серии запросов должен быть виден последний запрос");
        assertTrue(resultsPage.getTitle().contains("10 USD to EUR"), "Последний запрос в серии должен открывать свою страницу");
    }

    @Test
    void tc20FastTypingAndEnterShouldWork() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .submitFastWithEnter("solve x^2=4")
                .waitUntilOpenedForQuery("solve x^2=4");

        assertTrue(resultsPage.getCurrentUrl().contains("/input"), "Быстрый ввод и Enter должны открывать страницу результатов");
        assertTrue(resultsPage.getTitle().contains("solve x^2=4"), "Быстрый ввод и Enter должны открыть нужный запрос");
        assertTrue(resultsPage.getInputText().contains("solve x^2=4"), "После быстрого ввода и Enter запрос должен сохраниться в строке поиска");
    }

    @Test
    void tc23ResultLinkShouldBeAvailableWhenPresent() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("capital of Germany")
                .submitWithButton()
                .waitUntilOpenedForQuery("capital of Germany");

        assertTrue(
                resultsPage.findResultLinkHref("Satellite image").isPresent(),
                "Если в результатах есть ссылка, она должна быть доступна"
        );
        assertTrue(
                resultsPage.findResultLinkHref("Satellite image").orElse("").contains("maps.google.com"),
                "Ссылка результата должна вести на карту"
        );
    }
}
