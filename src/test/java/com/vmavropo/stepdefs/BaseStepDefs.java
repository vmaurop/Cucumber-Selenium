package com.vmavropo.stepdefs;

import com.vmavropo.utils.Test;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BaseStepDefs {

    Test test;

    public BaseStepDefs(Test test) {
        this.test = test;
    }

    @When("^the user logs into (.+) with (.+) capabilities$")
    public void theUserLogsIntoWithCapabilities(String app, String userRole) {
        test.webDriverFactory()
                .openApplication(app);
        test.ui()
                .auth()
                .login(userRole);
    }

    @Then("^Response status is (.+)$")
    public void responseStatusIsStatus(String status) {
        test.api()
                .commonAPI()
                .getValidate()
                .verifyStatus(status);
    }


    @Given("^navigate to home page (.+)$")
    public void navigateToHomePage(String app) {
        test.webDriverFactory()
                .openApplication(app);
    }

}
