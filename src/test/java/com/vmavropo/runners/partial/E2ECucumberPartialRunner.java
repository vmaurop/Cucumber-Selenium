package com.vmavropo.runners.partial;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(plugin = {"pretty", "json:target/cucumber_partial.json", "junit:target/cucumber-junit-report_partial.xml",
        "rerun:target/failed_scenarios/failed_partial_once.txt"},
        features = {"classpath:features"},
        tags = "",
        glue = {"com.netcompany.intrasoft.stepdefs"},
        monochrome = true)
public class E2ECucumberPartialRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
