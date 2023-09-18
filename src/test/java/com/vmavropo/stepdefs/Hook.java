package com.vmavropo.stepdefs;

import com.vmavropo.utils.Test;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hook {

    Test test;

    public Hook(Test test) {
        this.test = test;
    }


    @Before(value = "@UI", order = 1)
    public void beforeUITest(Scenario scenario) {
        test.context().setScenario(scenario);
        test.webDriverFactory().openBrowser();
        test.webDriverFactory().getDriver().manage().window().maximize();
        test.context().setUiTest(true);
    }

    @Before(value = "@API", order = 1)
    public void beforeTest(Scenario scenario) {
        test.context().setScenario(scenario);
        test.context().setUiTest(false);
    }

    @After(value = "@UI", order = 1)
    public void afterTest(Scenario scenario) {
        if (test.context().getUiTest()) {
            test.webDriverFactory().closeBrowser(scenario);
        }
        test.context().setUiTest(false);
    }

}
