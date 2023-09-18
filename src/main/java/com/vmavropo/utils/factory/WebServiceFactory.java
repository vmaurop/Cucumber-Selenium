package com.vmavropo.utils.factory;

import com.vmavropo.api.CommonAPI;
import com.vmavropo.utils.Test;

import java.util.Objects;

public class WebServiceFactory {

    Test test;

    CommonAPI commonAPI;

    public WebServiceFactory(Test test) {
        this.test = test;
    }

    public CommonAPI commonAPI() {
        return Objects.requireNonNullElseGet(commonAPI, () -> commonAPI = new CommonAPI(test));
    }

}
