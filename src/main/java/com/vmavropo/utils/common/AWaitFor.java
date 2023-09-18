package com.vmavropo.utils.common;

import com.vmavropo.utils.Test;
import com.vmavropo.utils.config.EnvDataConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class AWaitFor {

    Test test;

    EnvDataConfig envDataConfig;

    WebDriver driver;

    public AWaitFor(Test test) {
        this.test = test;
        envDataConfig = new EnvDataConfig();
        driver = test.webDriverFactory().getDriver();
    }

    public void elementToBePresent(By by) {
        await().atMost(Long.parseLong(test.envDataConfig().getTimeoutElementFind()), SECONDS)
                .until(() ->
                        new Element(test).isElementPresent(by));
    }
}
