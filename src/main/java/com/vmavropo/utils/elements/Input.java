package com.vmavropo.utils.elements;

import com.vmavropo.utils.Test;
import com.vmavropo.utils.common.WaitFor;
import com.vmavropo.utils.config.EnvDataConfig;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;


public final class Input {

    Test test;

    EnvDataConfig envDataConfig;

    WaitFor waitFor;

    WebDriver driver;

    public Input(Test test) {
        this.test = test;
        envDataConfig = test.envDataConfig();
        waitFor = test.waitFor();
        driver = test.webDriverFactory().getDriver();
    }

    /**
     * This method validates the plain text area is empty
     *
     * @param locator [By]
     */
    public void assertEmptyText(By locator) {
        WebElement element = driver.findElement(locator);
        assertEmptyText(element);
    }

    /**
     * This method validates the plain text area is empty
     *
     * @param element [WebElement]
     */
    public void assertEmptyText(WebElement element) {
        Assert.assertEquals(getValue(element), StringUtils.EMPTY);
    }

    /**
     * This method validates the expected text
     *
     * @param locator      [By] The locator of the target input field
     * @param expectedText [String] The expected text of the element
     */
    public void assertText(By locator, String expectedText) {
        WebElement element = driver.findElement(locator);
        assertText(element, expectedText);
    }

    /**
     * This method validates the expected text
     *
     * @param element      [WebElement] The target input field element
     * @param expectedText [String] The expected text of the element
     */
    public void assertText(WebElement element, String expectedText) {
        Assert.assertEquals(getValue(element), expectedText);
    }

    public void assertIsDisabled(WebElement element, boolean disabled) {
        Assert.assertNotEquals(element.isEnabled(), disabled);
    }

    public String getText(By locator) {
        WebElement element = driver.findElement(locator);
        return getText(element);
    }

    public String getText(WebElement element) {
        waitFor.elementToBeVisible(element);
        return element.getText().trim();
    }

    public String getValue(By locator) {
        WebElement element = driver.findElement(locator);
        return getValue(element);
    }

    public String getValue(WebElement element) {
        waitFor.elementToBeVisible(element);
        return element.getAttribute("value");
    }

    public void setText(By locator, String text) {
        if (text != null && !text.isEmpty()) {
            WebElement element = driver.findElement(locator);
            waitFor.elementToBeVisible(element);
            element.clear();
            element.sendKeys(text);
        }
    }

    public void setText(WebElement element, String text) {
        if (text != null && !text.isEmpty()) {
            waitFor.elementToBeVisible(element);
            element.clear();
            element.sendKeys(text);
        }
    }

    public void setDate(By locator, String date) {
        if (date != null && !date.isEmpty()) {
            WebElement element = driver.findElement(locator);
            setDate(element, date);
        }
    }

    public void setDate(WebElement element, String date) {
        if (date != null && !date.isEmpty()) {
            waitFor.elementToBeVisible(element);
            element.clear();
            element.sendKeys(date);
            element.sendKeys(Keys.TAB);
        }
    }

    public void clearText(By locator) {
        WebElement element = driver.findElement(locator);
        clearText(element);
    }

    public void clearText(WebElement element) {
        waitFor.elementToBeVisible(element);
        element.clear();
    }

    public void clearTextUsingBackspace(By locator) {
        WebElement element = driver.findElement(locator);
        clearTextUsingBackspace(element);
    }

    public void clearTextUsingBackspace(WebElement element) {
        waitFor.elementToBeVisible(element);
        while (!getValue(element).isEmpty()) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    public void selectOptionWithDownAndEnter(By by, String text) {
        WebElement element = driver.findElement(by);
        element.sendKeys(text);
        element.sendKeys(Keys.DOWN);
        element.sendKeys(Keys.ENTER);
    }

    public void selectOptionWithDownAndEnterAndTab(By by, String text) {
        WebElement element = driver.findElement(by);
        element.sendKeys(text);
        waitFor.sleep(2, TimeUnit.SECONDS);
        element.sendKeys(Keys.DOWN);
        waitFor.sleep(1, TimeUnit.SECONDS);
        element.sendKeys(Keys.ENTER);
        waitFor.sleep(1, TimeUnit.SECONDS);
        element.sendKeys(Keys.TAB);
    }
}
