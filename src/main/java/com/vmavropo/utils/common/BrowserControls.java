package com.vmavropo.utils.common;

import com.vmavropo.utils.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class BrowserControls {

    public static final int SCROLL_DOWN_450_PIXELS = 450;

    public static final int SCROLL_UP_1000_PIXELS = -1000;

    public static final int TWO_SECONDS = 2;

    public static final String SCROLL_INTO_VIEW = "arguments[0].scrollIntoView(true);";

    Test test;

    WaitFor waitFor;

    WebDriver driver;

    public BrowserControls(Test test) {
        this.test = test;
        waitFor = test.waitFor();
        driver = test.webDriverFactory().getDriver();
    }

    public void scrollUp() {
        scroll(SCROLL_UP_1000_PIXELS);
    }

    public void scrollDown() {
        scroll(SCROLL_DOWN_450_PIXELS);
        waitFor.XTime(TWO_SECONDS, TimeUnit.SECONDS);
    }

    public void slowScrollToTop() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        while (castObjectToDouble(jse.executeScript("return window.pageYOffset;")) > 0) {
            scrollUp();
            waitFor.pageToLoad();
        }
    }

    public void slowScrollToBottom() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        Long pageBottom = (Long) jse.executeScript("return document.body.scrollHeight");

        while ((Long) jse.executeScript("return window.pageYOffset;") < pageBottom) {
            scrollDown();
            waitFor.pageToLoad();
        }
    }

    public void scrollToElement(By locator) {
        waitFor.pageToLoad();
        WebElement element = driver.findElement(locator);
        scrollToElement(element);
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(SCROLL_INTO_VIEW, element);
        waitFor.pageToLoad();
    }

    public void scrollToElementAction(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        waitFor.pageToLoad();
    }

    public void scrollToElementMiddle(By locator) {
        WebElement element = driver.findElement(locator);
        scrollToElementMiddle(element);
    }

    public void scrollToElementMiddle(WebElement element) {
        String scrollElementIntoMiddle = "var viewPortHeight = " +
                "Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
        waitFor.elementToBeVisible(element);
        waitFor.XTime(1, TimeUnit.SECONDS);
    }

    private void scroll(int amount) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0," + amount + ")", "");
        waitFor.XTime(2, TimeUnit.SECONDS);
    }

    public void refreshPage() {
        driver.navigate().refresh();
        waitFor.pageToLoad();
    }

    public void goBack() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.history.go(-1)");
    }

    public void zoomBrowser(Float zoom) throws AWTException {
        Robot robot = new Robot();
        // zoom in
        if (zoom > 0) {
            for (int i = 0; i < zoom; i++) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_EQUALS);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_EQUALS);
            }
        } // zoom out
        else if (zoom < 0) {
            zoom = Math.abs(zoom);
            for (int i = 0; i < zoom; i++) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_MINUS);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_MINUS);
            }
        }
    }

    public void openNewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        goToNewTab();
    }

    /**
     * This method closes the opened tab and returns focus on the first tab
     */
    public void closeCurrentTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        Assert.assertFalse(tabs.size() == 1, "Warning! Only one tab is open! By closing it, the " +
                "whole browser will be closed!");
        driver.close();
        driver.switchTo().window(driver.getWindowHandles().stream().findFirst().get());
    }

    /**
     * This method navigates to the opened tab and returns focus on this one.
     */
    public void goToNewTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        Assert.assertFalse(tabs.size() == 1, "Warning! Only one tab is open!");
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        waitFor.pageToLoad();
    }

    public void closeOtherTabs() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(originalHandle);
    }

    @SuppressWarnings("deprecation")
    private static Double castObjectToDouble(Object object) {
        assert Objects.nonNull(object);
        return new Double(object.toString());
    }
}
