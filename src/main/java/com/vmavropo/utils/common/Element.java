package com.vmavropo.utils.common;

import com.vmavropo.utils.Test;
import com.vmavropo.utils.config.EnvDataConfig;
import com.vmavropo.utils.config.ResourcesConfig;
import com.vmavropo.utils.elements.Button;
import com.vmavropo.utils.elements.Checkbox;
import com.vmavropo.utils.elements.Dropdown;
import com.vmavropo.utils.elements.Input;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.vmavropo.utils.config.StaticConfig.JS_CLICK;

public class Element {

    public static final String GET_BY_FROM_WEB_ELEMENT_FIRST_REGEX = "(?s)(.*)\\]";

    Test test;

    WebDriver driver;

    WaitFor wait;

    EnvDataConfig envDataConfig;

    public Element(Test test) {
        this.test = test;
        envDataConfig = new EnvDataConfig();
        driver = test.webDriverFactory().getDriver();
        wait = new WaitFor(test);
    }

    Input input;

    public Input input() {
        return Objects.requireNonNullElseGet(input, () -> input = new Input(test));
    }

    Button button;

    public Button button() {
        return Objects.requireNonNullElseGet(button, () -> button = new Button(test));
    }

    Dropdown dropdown;

    public Dropdown dropdown() {
        return Objects.requireNonNullElseGet(dropdown, () -> dropdown = new Dropdown(test));
    }

    Checkbox checkbox;

    public Checkbox checkbox() {
        return Objects.requireNonNullElseGet(checkbox, () -> checkbox = new Checkbox(test));
    }

    public boolean isElementPresent(By by) {
        return !driver.findElements(by).isEmpty();
    }

    public boolean isElementVisible(By by) {
        try {
            driver.findElement(by).isDisplayed();
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public WebElement getElement(By by) {
        return driver.findElement(by);
    }

    public List<WebElement> getElements(By by) {
        return driver.findElements(by);
    }

    public static By getByFromElement(WebElement element) {
        By by = null;
        String[] pathVariables = null;

        if (element.toString().contains("->")) {
            pathVariables = (element.toString().split("->")[1].replaceFirst(GET_BY_FROM_WEB_ELEMENT_FIRST_REGEX, "$1" + ""))
                    .split(":");
        } else {
            String target = element.toString().split("'By.")[1];
            target = target.substring(0, target.length() - 1);
            pathVariables = target.split(":");
        }

        String selector = pathVariables[0].trim();
        String value = pathVariables[1].trim();
        if (StringUtils.countMatches(value, "[") != StringUtils.countMatches(value, "]")) {
            value = value.substring(0, value.length() - 1);
        }

        switch (selector) {
            case "id":
                by = By.id(value);
                break;
            case "className":
                by = By.className(value);
                break;
            case "tagName":
                by = By.tagName(value);
                break;
            case "xpath":
                by = By.xpath(value);
                break;
            case "cssSelector":
                by = By.cssSelector(value);
                break;
            case "linkText":
                by = By.linkText(value);
                break;
            case "name":
                by = By.name(value);
                break;
            case "partialLinkText":
                by = By.partialLinkText(value);
                break;
            default:
                throw new IllegalStateException("locator: " + selector + " not found!");
        }
        return by;
    }

    public void upload(By chooseFileButton, String filename) {
        test.waitFor().pageToLoad();
        WebElement inputUpload = driver.findElement(chooseFileButton);
        ResourcesConfig resourcesConfig = new ResourcesConfig();
        if (filename != null && !filename.isEmpty()) {
            inputUpload.sendKeys(resourcesConfig.getInputDir() + "/" + filename);
        }
    }

    public void uploadFromTargetDir(By chooseFileButton, String filename) {
        test.waitFor().pageToLoad();
        WebElement inputUploadTarget = driver.findElement(chooseFileButton);
        ResourcesConfig resourcesConfig = new ResourcesConfig();
        if (filename != null && !filename.isEmpty()) {
            inputUploadTarget.sendKeys(resourcesConfig.getTargetPath() + filename);
        }
    }

    public Boolean attributeContainsText(By by, String attribute, String expectedValue) {
        if (expectedValue != null && !expectedValue.isEmpty()) {
            test.waitFor().pageToLoad();
            test.webDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
            String actualValue = driver.findElement(by).getAttribute(attribute);
            return actualValue.equalsIgnoreCase(expectedValue);
        }
        return false;
    }

    public void click(By by) {
        wait.waitForElementToBeClickable(by);
        WebElement buttonOrLink = driver.findElement(by);
        buttonOrLink.click();
        wait.waitForLoad();
    }

    public void scrollToTheElement(By by) {
        wait.waitForElementToBeDisplayed(by);
        WebElement element = driver.findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        wait.waitForElement(element);
    }

    public void input(By by, String value) {
        if (value != null && !value.isEmpty()) {
            wait.waitForElementToBeDisplayed(by);
            WebElement element = driver.findElement(by);
            element.clear();
            test.webDriverFactory().getWait().until(ExpectedConditions.attributeToBe(driver.findElement(by), "value", ""));
            try {
                element.sendKeys(value);
            } catch (StaleElementReferenceException ex) {
                element.sendKeys(value);
            }
            wait.waitForLoad();
        }
    }

    public void inputByIdJavascript(String id, String value) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("document.getElementById('" + id + "').value = '" + value + "';");
    }

    public void clickJS(By by) {
        wait.waitForElementToBeDisplayed(by);
        WebElement buttonOrLink = driver.findElement(by);
        ((JavascriptExecutor) driver)
                .executeScript(JS_CLICK, buttonOrLink);
        wait.waitForLoad();
    }

    public void dropDownReactNewMDC(By id, String listID, String value) {
        WebElement dropDownReactNewMDC = driver.findElement(id);
        By xpathForSelect = By.xpath("//*[contains(@data-testid, '" + listID + "')]//label[contains(text(),'" + value + "')]/ancestor::mdc-list-item");
        if (value != null && !value.isEmpty()) {
            dropDownReactNewMDC.click();
            wait.waitForElementToBeClickable(xpathForSelect);
            clickReact(xpathForSelect);
        }
    }

    public void clickTabs(String tabText) {
        clickJS(By.xpath("//*[contains(text(),'" + tabText + "')]"));
        wait.waitForLoad();
    }

    public void dropDownReact(By id, String value) {
        WebElement dropdownReact = driver.findElement(id);
        if (value != null && !value.isEmpty()) {
            dropdownReact.click();
            wait.waitForLoad();
            By locator = By.cssSelector("[data-value='" + value + "']");
            clickReact(locator);
        }
    }

    public void clickReact(By by) {
        WebElement buttonOrLink = driver.findElement(by);
        wait.sleep(100, TimeUnit.MILLISECONDS);
        buttonOrLink.click();
        wait.waitForLoad();
    }

    public void datePickReactExport(String xpath, String date) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '" + date + "';", driver.findElement(By.xpath("//*[contains(@test-id,'" + xpath + "')]")));
        click(By.xpath("//*[contains(@test-id,'" + xpath + "')]//label"));
        click(By.xpath("//*[contains(@data-value,'" + date + "')]"));
    }

    public void dropDownActionsReact(By id, String value) {
        WebElement dropdownActions = driver.findElement(id);
        if (value != null && !value.isEmpty()) {
            dropdownActions.click();
            wait.waitForLoad();
            By locator = By.xpath("//*[contains(text(), '" + value + "')]");
            clickJS(locator);
        }
    }

    public void scrollDown() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        if ("chrome".equals(envDataConfig.getBrowser())) {
            jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        } else {
            jse.executeScript("window.scrollBy(0,600)", "");
        }
        wait.waitForLoad();
    }

    public void scrollUp() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        if ("chrome".equals(envDataConfig.getBrowser())) {
            jse.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
        } else {
            jse.executeScript("window.scrollBy(0,-600)", "");
        }
    }

    public boolean getCurrentUrl(String expectedUrl) {
        wait.waitForLoad();
        return driver.getCurrentUrl().contains(expectedUrl);
    }

    public WebElement getElementFromWebElements(By by, int index) {
        List<WebElement> elements = getElements(by);
        return elements.get(index);
    }


    public void acceptPopUp(){
        test.webDriverFactory().getDriver().switchTo().alert().accept();
    }

}
