package ru.itmo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.itmo.pages.HomePage;
import ru.itmo.pages.ResultsPage;

public class NegativeInputUiTest extends BaseUiTest {

    @Test
    void tc10EmptyInputShouldNotBreakPage() {
        HomePage homePage = new HomePage(driver, baseUrl)
                .open()
                .clearInput()
                .clickComputeOnEmptyInput();

        assertTrue(homePage.isInputVisible(), "Поле ввода должно оставаться доступным");
        assertTrue(driver.getCurrentUrl().startsWith("https://www.wolframalpha.com/"), "Сайт должен остаться доступным");
    }

    @Test
    void tc11SpecialSymbolsShouldBeHandledGracefully() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("@#$")
                .submitWithButton()
                .waitUntilOpenedForQuery("@#$");

        assertTrue(resultsPage.getInputText().equals("@#$"), "Спецсимволы должны сохраниться в строке запроса");
        assertTrue(resultsPage.getTitle().contains("@#$"), "Заголовок страницы должен отражать введенный запрос");
    }

    @Test
    void tc12LongQueryShouldNotCrashTheSite() {
        String longQuery = "speed of light ".repeat(20).trim();

        HomePage homePage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery(longQuery);

        assertFalse(homePage.getInputText().isBlank(), "Даже длинный запрос должен сохраниться хотя бы частично");
        assertTrue(homePage.getInputText().length() > 100, "Поле должно принимать длинный пользовательский ввод");
        assertTrue(homePage.isComputeButtonVisible(), "Сайт должен оставаться работоспособным при длинном вводе");
    }
}
