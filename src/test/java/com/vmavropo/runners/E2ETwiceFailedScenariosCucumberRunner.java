package com.vmavropo.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(plugin = {"pretty", "json:target/cucumber_failed_twice.json",
        "junit:target/cucumber-junit-report_failed_twice.xml"},
        features = {"@target/failed_scenarios/failed_parallel_twice.txt", "@target/failed_scenarios/failed_linear_twice.txt"},
        glue = {"com.netcompany.intrasoft.stepdefs"},
        monochrome = true)
public class E2ETwiceFailedScenariosCucumberRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
