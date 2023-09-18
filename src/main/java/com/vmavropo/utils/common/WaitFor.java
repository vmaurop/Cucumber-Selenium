package com.vmavropo.utils.common;

import com.vmavropo.utils.Test;
import com.vmavropo.utils.config.EnvDataConfig;
import com.vmavropo.utils.config.StaticConfig;
import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WaitFor {

    public static final int EXTRA_WAIT_PAGE_TO_LOAD = 200;

    public static final int INTERVAL_LOADING_IMAGE = 250;

    public static final int EXTRA_WAIT_FOR_ELEMENT_TO_BE_CLICKABLE = 500;

    public static final int INTERVAL_JQUERY_PRIMEFACES = 200;

    public static final String COMPLETE_READY_STATE = "complete";

    Test test;

    EnvDataConfig envDataConfig;

    WebDriver driver;

    public WaitFor(Test test) {
        this.test = test;
        envDataConfig = new EnvDataConfig();
        driver = test.webDriverFactory().getDriver();
    }

    public void pageToLoad() {
        ExpectedCondition<Boolean> expectation =
                driver -> {
                    assert driver != null;
                    return ((JavascriptExecutor) test.webDriverFactory().getDriver())
                            .executeScript("return document.readyState").toString().equals(COMPLETE_READY_STATE);
                };
        try {
            test.webDriverFactory().getWait().until(expectation);
            loadingImage();
            JQueryAndPrimeFaces();
            XTime(EXTRA_WAIT_PAGE_TO_LOAD, TimeUnit.MILLISECONDS);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public void loadingImage() {
        final By loadingImageLocator = By.xpath("//div[contains(@style,'visibility: visible')]//img[contains(@src,'loader')]");
        if (!test.webDriverFactory()
                .getDriver()
                .findElements(loadingImageLocator)
                .isEmpty()) {
            test.webDriverFactory()
                    .getFluentWait()
                    .pollingEvery(Duration.ofMillis(INTERVAL_LOADING_IMAGE))
                    .until(ExpectedConditions.invisibilityOfElementLocated(loadingImageLocator));
        }
    }

    public void elementToBeVisible(WebElement element) {
        if (element != null) {
            test.webDriverFactory().getWait()
                    .until(ExpectedConditions.visibilityOf(element));
        } else {
            test.logger().error(StaticConfig.ELEMENT_NOT_PRESENT);
        }
    }

    public void XTime(int timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void elementToBeClickable(By by) {
        try {
            test.webDriverFactory().getFluentWait()
                    .until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            test.logger().error(StaticConfig.ELEMENT_NOT_CLICKABLE);
        }

    }

    public void dropdownElementToBeClickable(By by) {
        XTime(EXTRA_WAIT_FOR_ELEMENT_TO_BE_CLICKABLE, TimeUnit.MILLISECONDS);
        elementToBeClickable(by);
    }

    public void elementToBeDisplayed(By by) {
        test.webDriverFactory().getFluentWait()
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void JQueryAndPrimeFaces() {
        final String JS_JQUERY_DEFINED = "return typeof jQuery != 'undefined';";
        final String JS_PRIMEFACES_DEFINED = "return typeof PrimeFaces != 'undefined';";
        final String JS_JQUERY_ACTIVE = "return jQuery.active != 0;";
        final String JS_PRIMEFACES_QUEUE_NOT_EMPTY = "return !PrimeFaces.ajax.Queue.isEmpty();";

        test.webDriverFactory()
                .getFluentWait()
                .pollingEvery(Duration.ofMillis(INTERVAL_JQUERY_PRIMEFACES))
                .until(input -> {
                    boolean ajax = false;
                    boolean jQueryDefined = executeBooleanJavascript(input, JS_JQUERY_DEFINED);
                    boolean primeFacesDefined = executeBooleanJavascript(input, JS_PRIMEFACES_DEFINED);

                    if (jQueryDefined) {
                        // jQuery is still active
                        ajax |= executeBooleanJavascript(input, JS_JQUERY_ACTIVE);
                    }
                    if (primeFacesDefined) {
                        // PrimeFaces queue isn't empty
                        ajax |= executeBooleanJavascript(input, JS_PRIMEFACES_QUEUE_NOT_EMPTY);
                    }
                    // continue if all ajax request are processed
                    return !ajax;
                });
    }

    private boolean executeBooleanJavascript(WebDriver input, String javascript) {
        return (Boolean) ((JavascriptExecutor) input).executeScript(javascript);
    }

    //Explicit Wait Methods

    public void elementInvisible(WebElement element) {
        if (element != null) {
            test.webDriverFactory().getWait().until(ExpectedConditions.invisibilityOf(element));
        } else {
            test.logger().error(StaticConfig.ELEMENT_NOT_VISIBLE + envDataConfig.getTimeoutElementFind() + StaticConfig.SECONDS);
        }

    }

    public void attributeToContainText(By by, String attribute, String expectedValue) {
        test.webDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
        test.webDriverFactory().getWait().until(ExpectedConditions.attributeContains(by, attribute, expectedValue));
    }

    public void elementToBePresent(By by) {
        test.webDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void elementToBeVisible(By by) {
        test.webDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForLoad() {
        ExpectedCondition<Boolean> expectation =
                driver -> {
                    assert driver != null;
                    return ((JavascriptExecutor) test.webDriverFactory().getDriver())
                            .executeScript("return document.readyState").toString().equals("complete");
                };
        try {
            test.webDriverFactory().getWait().until(expectation);
            waitForJQueryAndPrimeFaces();
            waitForLoadingImage();
            sleep(300, TimeUnit.MILLISECONDS);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public void waitForJQueryAndPrimeFaces() {
        final String JS_JQUERY_DEFINED = "return typeof jQuery != 'undefined';";
        final String JS_PRIMEFACES_DEFINED = "return typeof PrimeFaces != 'undefined';";
        final String JS_JQUERY_ACTIVE = "return jQuery.active != 0;";
        final String JS_PRIMEFACES_QUEUE_NOT_EMPTY = "return !PrimeFaces.ajax.Queue.isEmpty();";

        test.webDriverFactory()
                .getFluentWait()
                .pollingEvery(Duration.ofMillis(200))
                .until(input -> {
                    boolean ajax = false;
                    boolean jQueryDefined = executeBooleanJavascript(input, JS_JQUERY_DEFINED);
                    boolean primeFacesDefined = executeBooleanJavascript(input, JS_PRIMEFACES_DEFINED);

                    if (jQueryDefined) {
                        // jQuery is still active
                        ajax |= executeBooleanJavascript(input, JS_JQUERY_ACTIVE);
                    }
                    if (primeFacesDefined) {
                        // PrimeFaces queue isn't empty
                        ajax |= executeBooleanJavascript(input, JS_PRIMEFACES_QUEUE_NOT_EMPTY);
                    }
                    // continue if all ajax request are processed
                    return !ajax;
                });
    }

    public void waitForLoadingImage() {
        test.webDriverFactory()
                .getFluentWait()
                .pollingEvery(Duration.ofMillis(250))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@style,'visibility: visible')]//img[contains(@src,'loader')]")));
    }

    public void sleep(int timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitForElement(WebElement element) {
        if (element != null) {
            test.webDriverFactory().getWait()
                    .until(ExpectedConditions.visibilityOf(element));
        } else {
            System.out.println("ERROR Element is NOT present.");
        }
    }

    public void waitForElementToBeDisplayed(By by) {
        test.webDriverFactory().getFluentWait()
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForElementToBeClickable(By by) {
        try {
            test.webDriverFactory().getFluentWait()
                    .until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            System.out.println("ERROR Element is NOT present.");
        }
    }

    public void waitForElementToBeClickable(WebElement element) {
        try {
            test.webDriverFactory().getFluentWait()
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            System.out.println("ERROR Element is NOT present.");
        }
    }

    public void waitForElementToHasValue(By by) {
        waitForElementToBeDisplayed(by);
        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .until(() ->
                        driver.findElement(by).getText().length() != 1);
    }

    public void waitForElementVisible(By by) {
        test.webDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
