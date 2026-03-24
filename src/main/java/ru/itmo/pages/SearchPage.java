package ru.itmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class SearchPage extends BasePage {
    protected static final By INPUT =
            By.xpath("//textarea[@aria-label='WolframAlpha input field']");
    protected static final By COMPUTE_BUTTON =
            By.xpath("//button[@aria-label='Compute input button']");

    protected SearchPage(WebDriver driver) {
        super(driver);
    }

    public String getInputText() {
        return normalizedInputValue(INPUT);
    }

    public boolean isInputVisible() {
        return waitVisible(INPUT).isDisplayed();
    }

    public boolean isComputeButtonVisible() {
        return waitVisible(COMPUTE_BUTTON).isDisplayed();
    }

    public SearchPage typeQuery(String query) {
        typeText(INPUT, query);
        return this;
    }

    public SearchPage clearInput() {
        typeText(INPUT, "");
        return this;
    }

    public ResultsPage submitWithButton() {
        click(COMPUTE_BUTTON);
        return new ResultsPage(driver);
    }

    public ResultsPage submitWithEnter() {
        waitVisible(INPUT).sendKeys(Keys.ENTER);
        return new ResultsPage(driver);
    }

    public ResultsPage submitFastWithEnter(String query) {
        WebElement element = waitVisible(INPUT);
        element.click();
        element.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys(query, Keys.ENTER);
        return new ResultsPage(driver);
    }
}
