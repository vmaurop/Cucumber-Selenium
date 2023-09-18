package com.vmavropo.api;

import com.vmavropo.utils.Test;
import com.vmavropo.utils.common.HttpStatusCode;
import org.testng.Assert;

public class CommonAPIValidations {

    Test test;


    CommonAPIValidations(Test test) {
        this.test = test;
    }

    public void verifyStatus(String status) {
        String message = "Response: " + test.context().getResponse().asString();
        Assert.assertEquals(test.context().getResponse().statusCode(), HttpStatusCode.getStatusCode(status), message);
    }

}
