package com.vmavropo.runners.linear;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(plugin = {"pretty", "json:target/cucumber_linear.json", "junit:target/cucumber-junit-report_linear.xml",
        "rerun:target/failed_scenarios/failed_linear_once.txt"},
        features = {"classpath:features"},
        tags = "(@Linear) and (not @Ignored)",
        glue = {"com.netcompany.intrasoft.stepdefs"},
        monochrome = true)
public class E2ECucumberLinearRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
