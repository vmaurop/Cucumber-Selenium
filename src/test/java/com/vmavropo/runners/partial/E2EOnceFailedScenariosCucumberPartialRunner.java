package com.vmavropo.runners.partial;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(plugin = {"pretty", "json:target/cucumber_partial_failed_once.json",
        "junit:target/cucumber_partial-junit-report_failed_once.xml"},
        features = {"@target/failed_scenarios/failed_partial_once.txt"},
        glue = {"com.netcompany.intrasoft.stepdefs"},
        monochrome = true)
public class E2EOnceFailedScenariosCucumberPartialRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
