package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends SearchPage {
    private static final By NATURAL_LANGUAGE_TAB = By.xpath("//button[.//span[normalize-space()='Natural Language']]");

    private final String baseUrl;

    public HomePage(WebDriver driver, String baseUrl) {
        super(driver);
        this.baseUrl = baseUrl;
    }

    public HomePage open() {
        driver.get(baseUrl);
        waitVisible(INPUT);
        waitVisible(NATURAL_LANGUAGE_TAB);
        return this;
    }

    @Override
    public HomePage typeQuery(String query) {
        super.typeQuery(query);
        return this;
    }

    @Override
    public HomePage clearInput() {
        super.clearInput();
        return this;
    }

    public boolean hasAutocompleteSuggestion(String suggestionText) {
        return pageSourceContainsIgnoreCase(suggestionText);
    }

    public HomePage waitForAutocompleteSuggestion(String suggestionText) {
        waitForPageSourceContains(suggestionText);
        return this;
    }

    public HomePage clickComputeOnEmptyInput() {
        click(COMPUTE_BUTTON);
        return this;
    }
}
