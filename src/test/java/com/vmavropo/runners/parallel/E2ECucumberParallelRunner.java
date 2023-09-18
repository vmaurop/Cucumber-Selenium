package com.vmavropo.runners.parallel;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(plugin = {"pretty", "json:target/cucumber_parallel.json", "junit:target/cucumber_parallel-junit-report.xml",
        "rerun:target/failed_scenarios/failed_parallel_once.txt", "timeline:target/timeline"},
        features = {"classpath:features"},
        tags = "(not @Linear) and (not @Ignored)",
        glue = {"com.netcompany.intrasoft.stepdefs"},
        monochrome = true)
public class E2ECucumberParallelRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
