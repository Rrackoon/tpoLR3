package ru.itmo.pages;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ResultsPage extends SearchPage {
    private static final By POD_HEADERS = By.xpath("//h2/span");
    private static final By RESULTS_MAIN = By.xpath("//main");

    public ResultsPage(WebDriver driver) {
        super(driver);
    }

    public ResultsPage waitUntilOpened() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/input"),
                ExpectedConditions.urlContains("/gateway")));
        waitVisible(INPUT);
        waitVisible(RESULTS_MAIN);
        return this;
    }

    public ResultsPage waitUntilOpenedForQuery(String query) {
        waitUntilOpened();
        String normalizedQuery = normalize(query);
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        wait.until(driver ->
                normalize(driver.getTitle()).contains(normalizedQuery)
                        || driver.getCurrentUrl().contains(encodedQuery));

        wait.until(driver -> {
            String normalizedInput = normalize(getInputText());
            return normalizedInput.equals(normalizedQuery)
                    || normalizedInput.contains(normalizedQuery)
                    || driver.getCurrentUrl().contains(encodedQuery);
        });
        return this;
    }

    public ResultsPage waitForPodContaining(String headerFragment) {
        wait.until(driver -> hasPodContaining(headerFragment));
        return this;
    }

    public boolean hasPod(String header) {
        return !findAll(By.xpath("(//h2/span[normalize-space()='" + header + "']/ancestor::section[@tabindex='0'])[1]")).isEmpty();
    }

    public boolean hasPodContaining(String headerFragment) {
        return !findAll(By.xpath("(//h2/span[contains(normalize-space(),'" + headerFragment + "')]/ancestor::section[@tabindex='0'])[1]"))
                .isEmpty();
    }

    public String getFirstImageAltInPod(String header) {
        By locator = By.xpath("((//h2/span[normalize-space()='" + header + "']/ancestor::section[@tabindex='0'])[1]//img[@alt and normalize-space(@alt)!=''])[1]");
        return waitVisible(locator).getAttribute("alt").trim();
    }

    public boolean anyImageAltContains(String textFragment) {
        String normalizedFragment = normalize(textFragment);
        return findAll(By.xpath("//main//img[@alt and normalize-space(@alt)!='']")).stream()
                .map(element -> element.getAttribute("alt"))
                .map(this::normalize)
                .anyMatch(alt -> alt.contains(normalizedFragment));
    }

    public boolean podHasVisualContentContaining(String headerFragment) {
        String expression = "((//h2/span[contains(normalize-space(),'" + headerFragment + "')]/ancestor::section[@tabindex='0'])[1]//*[self::img or self::canvas or self::svg])";
        return !findAll(By.xpath(expression)).isEmpty();
    }

    public boolean pageContainsText(String text) {
        return pageSourceContainsIgnoreCase(text);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public ResultsPage searchAgainWithButton(String query) {
        typeQuery(query);
        return submitWithButton().waitUntilOpenedForQuery(query);
    }

    public ResultsPage refreshAndWait() {
        refreshPage();
        waitVisible(INPUT);
        waitVisible(RESULTS_MAIN);
        return this;
    }

    public Optional<String> findResultLinkHref(String linkText) {
        String lower = linkText.toLowerCase(Locale.ROOT);
        wait.until(driver -> driver.getPageSource().toLowerCase(Locale.ROOT).contains(lower));
        return findAll(By.xpath("//main//a[@href]")).stream()
                .filter(element -> element.getText().toLowerCase(Locale.ROOT).contains(lower))
                .map(element -> element.getAttribute("href"))
                .filter(href -> href != null && !href.isBlank())
                .findFirst();
    }

    private String normalize(String value) {
        return value == null
                ? ""
                : value.toLowerCase(Locale.ROOT).replaceAll("\\s+", " ").trim();
    }
}
