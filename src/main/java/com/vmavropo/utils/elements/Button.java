package com.vmavropo.utils.elements;

import com.vmavropo.utils.Test;
import com.vmavropo.utils.common.AWaitFor;
import com.vmavropo.utils.common.BrowserControls;
import com.vmavropo.utils.common.Element;
import com.vmavropo.utils.common.WaitFor;
import com.vmavropo.utils.config.ResourcesConfig;
import com.vmavropo.utils.config.StaticConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.vmavropo.utils.config.StaticConfig.JS_CLICK;

public final class Button {

    Test test;

    WaitFor waitFor;

    AWaitFor awaitFor;

    WebDriver driver;

    BrowserControls browserControls;

    public Button(Test test) {
        this.test = test;
        waitFor = test.waitFor();
        awaitFor = test.awaitFor();
        driver = test.webDriverFactory().getDriver();
        browserControls = new BrowserControls(test);
    }

    public void click(By by) {
        awaitFor.elementToBePresent(by);
        waitFor.elementToBeClickable(by);
        scrollAndClick(driver.findElement(by));
    }

    public void clickAction(By by) {
        WebElement element = driver.findElement(by);
        waitFor.elementToBeVisible(element);
        Actions action = new Actions(driver);
        action.click(element).perform();
        waitFor.pageToLoad();
    }

    public void clickJS(By by) {
        WebElement element = driver.findElement(by);
        if (element != null) {
            waitFor.XTime(1, TimeUnit.SECONDS);
            waitFor.elementToBeVisible(element);
            ((JavascriptExecutor) driver).executeScript(JS_CLICK, element);

        } else {
            test.logger().error(StaticConfig.ELEMENT_NOT_CLICKABLE);
        }
        waitFor.pageToLoad();
    }

    public void doubleClick(By by) {
        WebElement element = driver.findElement(by);
        waitFor.elementToBeVisible(element);
        Actions action = new Actions(driver);
        action.moveToElement(element).doubleClick().build().perform();
        waitFor.pageToLoad();
    }

    public void clickAndEnter(By by) {
        waitFor.elementToBeVisible(by);
        WebElement element = driver.findElement(by);
        element.sendKeys(Keys.ENTER);
        waitFor.pageToLoad();
    }

    public void upload(By by, String filename) {
        WebElement element = driver.findElement(by);
        upload(element, filename);
    }

    public void upload(WebElement element, String filename) {
        scrollAndClick(element);
        try {
            // Set the target file in clipboard
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(filename.replace(
                    "/", "\\\\")), null);
            // Perform keystrokes for CTRL+V and ENTER keys
            Robot robot = new Robot();
            robot.setAutoDelay(3000);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            robot.setAutoDelay(2000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.setAutoDelay(2000);
        } catch (AWTException ex) {
            test.logger().error(StaticConfig.AWT_EXCEPTION);
        }
        waitFor.pageToLoad();
    }

    private void scrollAndClick(WebElement element) {
        try {
            element.click();
        } catch (WebDriverException ex) {
            By locator = Element.getByFromElement(element);
            new BrowserControls(test).scrollToElementMiddle(locator);
            waitFor.XTime(1, TimeUnit.SECONDS);
            driver.findElement(locator).click();
        }
    }

    private static String getAttachmentFilepath(String fileName) {
        File targetFile = new File(new ResourcesConfig().getInputDir() + fileName);
        Assert.assertTrue(targetFile.exists() && targetFile.isFile(), "Could not locate attachment file: " + fileName);
        return targetFile.getAbsolutePath();
    }
}
