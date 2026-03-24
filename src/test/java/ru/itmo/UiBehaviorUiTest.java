package ru.itmo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.itmo.pages.HomePage;
import ru.itmo.pages.ResultsPage;

public class UiBehaviorUiTest extends BaseUiTest {

    @Test
    void tc13InputFieldShouldExist() {
        HomePage homePage = new HomePage(driver, baseUrl).open();

        assertTrue(homePage.isInputVisible(), "Поле ввода должно существовать");
    }

    @Test
    void tc14SearchButtonShouldStartSearch() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("capital of Germany")
                .submitWithButton()
                .waitUntilOpenedForQuery("capital of Germany");

        assertTrue(resultsPage.getCurrentUrl().contains("/input"), "Кнопка поиска должна открывать страницу результатов");
        assertTrue(resultsPage.getInputText().equals("capital of Germany"), "После нажатия кнопки запрос должен сохраниться");
    }

    @Test
    void tc15EnterShouldStartSearch() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("speed of light")
                .submitWithEnter()
                .waitUntilOpenedForQuery("speed of light");

        assertTrue(resultsPage.getCurrentUrl().contains("/input"), "Нажатие Enter должно открывать страницу результатов");
        assertTrue(resultsPage.getTitle().contains("speed of light"), "После Enter должен открыться нужный запрос");
    }

    @Test
    void tc16AutocompleteShouldAppear() {
        HomePage homePage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("cap")
                .waitForAutocompleteSuggestion("capacitor");

        assertTrue(homePage.hasAutocompleteSuggestion("capacitor"), "Autocomplete должен предлагать релевантные варианты");
    }
}
