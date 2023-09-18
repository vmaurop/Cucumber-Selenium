package com.vmavropo.utils.factory;

import com.vmavropo.ui.authentication.Auth;
import com.vmavropo.utils.Test;
import com.vmavropo.utils.browsers.Chrome;
import com.vmavropo.utils.browsers.Edge;
import com.vmavropo.utils.browsers.Firefox;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class WebDriverFactory {


    Test test;

    WebDriver driver;

    WebDriverWait waitDriver;

    FluentWait<WebDriver> fluentWait;

    public WebDriverFactory(Test test) {
        this.test = test;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return waitDriver;
    }

    public FluentWait<WebDriver> getFluentWait() {
        return fluentWait;
    }

    public WebDriverFactory openBrowser() {
        if (driver == null) {
            switch (test.envDataConfig().getBrowser()) {
                case "chrome": {
                    Chrome chrome = new Chrome();
                    driver = chrome.start();
                    break;
                }
                case "firefox": {
                    Firefox firefox = new Firefox();
                    driver = firefox.start();
                    break;
                }
                case "edge": {
                    Edge edge = new Edge();
                    driver = edge.start();
                    break;
                }
                default:
                    throw new IllegalStateException("Invalid BROWSER value on env.properties");
            }
        }
        Duration timeout = Duration.ofSeconds(Long.parseLong(test.envDataConfig().getTimeoutElementFind()));
        waitDriver = new WebDriverWait(driver, timeout);
        fluentWait = new FluentWait<>(driver).withTimeout(timeout);
        test.context().setUiTest(true);
        return this;
    }

    public Auth openApplication(String applicationName) {
        test.webDriverFactory().getDriver().get(test.envDataConfig().getUrl(applicationName));
        return new Auth(test);
    }

    public void closeBrowser(Scenario scenario) {
        if (scenario.isFailed())
            try {
                File screenshot =
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                scenario.attach(Files.readAllBytes(screenshot.toPath()), "image/png", "");
            } catch (IOException | ClassCastException e) {
                e.printStackTrace();
            }
        driver.quit();
    }


}
