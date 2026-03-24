package ru.itmo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.itmo.pages.HomePage;
import ru.itmo.pages.ResultsPage;

public class BasicFunctionalityUiTest extends BaseUiTest {

    @Test
    void tc01SimpleCalculationShouldReturnFour() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("2+2")
                .submitWithButton()
                .waitUntilOpenedForQuery("2+2");

        assertTrue(resultsPage.hasPod("Result:"), "Должен быть pod Result");
        assertTrue(resultsPage.getFirstImageAltInPod("Result:").equals("4"), "Результат 2+2 должен быть равен 4");
    }

    @Test
    void tc02MultiplicationShouldReturnTwentyFive() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("5*5")
                .submitWithButton()
                .waitUntilOpenedForQuery("5*5");

        assertTrue(resultsPage.getInputText().equals("5*5"), "В строке поиска должен сохраниться исходный запрос");
        assertTrue(resultsPage.pageContainsText("25"), "Результат 5*5 должен содержать 25");
    }

    @Test
    void tc03IntegralShouldDisplayIndefiniteIntegralPod() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("integrate x^2")
                .submitWithButton()
                .waitUntilOpenedForQuery("integrate x^2");

        assertTrue(
                resultsPage.hasPodContaining("integral")
                        || resultsPage.pageContainsText("x^3/3")
                        || resultsPage.pageContainsText("1/3 x^3")
                        || resultsPage.anyImageAltContains("x^3/3")
                        || resultsPage.anyImageAltContains("1/3 x^3")
                        || resultsPage.anyImageAltContains("⅓ x^3"),
                "Для интеграла должен появиться pod или результат с x^3/3"
        );
    }

    @Test
    void tc04EquationShouldDisplayRoots() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("solve x^2=4")
                .submitWithButton()
                .waitUntilOpenedForQuery("solve x^2=4");

        assertTrue(resultsPage.hasPod("Result:"), "Должен быть pod Result");
        assertTrue(
                resultsPage.getFirstImageAltInPod("Result:").contains("± 2"),
                "Решение уравнения должно содержать x = ± 2"
        );
    }

    @Test
    void tc05PlotShouldDisplayVisualGraph() {
        ResultsPage resultsPage = new HomePage(driver, baseUrl)
                .open()
                .typeQuery("plot x^2")
                .submitWithButton()
                .waitUntilOpenedForQuery("plot x^2")
                .waitForPodContaining("Plot");

        assertTrue(resultsPage.hasPodContaining("Plot"), "Должен быть pod Plot");
        assertTrue(resultsPage.podHasVisualContentContaining("Plot"), "В pod Plot должен быть графический элемент");
    }
}
