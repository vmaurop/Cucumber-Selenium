package com.vmavropo.runners.parallel;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(plugin = {"pretty", "json:target/cucumber_parallel_failed_once.json",
        "junit:target/cucumber_parallel-junit-report_failed_once.xml", "rerun:target/failed_scenarios/failed_parallel_twice.txt"},
        features = {"@target/failed_scenarios/failed_parallel_once.txt"},
        glue = {"com.netcompany.intrasoft.stepdefs"},
        monochrome = true)
public class E2EOnceFailedScenariosCucumberParallelRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
