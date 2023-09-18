package com.vmavropo.utils.factory;

import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import lombok.Data;

@Data
public class ContextFactory {

    private Scenario scenario;

    private Boolean uiTest;

    Response response;
}
