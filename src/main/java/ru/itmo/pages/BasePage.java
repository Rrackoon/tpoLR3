package ru.itmo.pages;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    protected void click(By locator) {
        waitClickable(locator).click();
    }

    protected void typeText(By locator, String text) {
        WebElement element = waitVisible(locator);
        element.click();
        element.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
    }

    protected String normalizedInputValue(By locator) {
        String value = waitVisible(locator).getDomProperty("value");
        return value == null ? "" : value.trim();
    }

    protected boolean pageSourceContainsIgnoreCase(String text) {
        return driver.getPageSource().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT));
    }

    protected void waitForPageSourceContains(String text) {
        wait.until(driver -> pageSourceContainsIgnoreCase(text));
    }
    protected void refreshPage() {
        driver.navigate().refresh();
    }
}
