package com.vmavropo.runners.linear;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(plugin = {"pretty", "json:target/cucumber_linear_failed_once.json",
        "junit:target/cucumber_linear-junit-report_failed_once.xml", "rerun:target/failed_scenarios/failed_linear_twice.txt"},
        features = {"@target/failed_scenarios/failed_linear_once.txt"},
        glue = {"com.netcompany.intrasoft.stepdefs"},
        monochrome = true)
public class E2EOnceFailedScenariosCucumberLinearRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
