package com.vmavropo.ui.authentication;

import com.vmavropo.utils.Test;

public class AuthValidations {

    Test test;


    public AuthValidations(Test test) {
        this.test = test;
    }

    public AuthValidations verifyAuthPageOpens() {
        test.awaitFor().elementToBePresent(AuthElements.USERNAME);
        test.waitFor().elementToBeClickable(AuthElements.SIGN_IN_BUTTON);
        return this;
    }

}
