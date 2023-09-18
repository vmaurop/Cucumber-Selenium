package com.vmavropo.ui.authentication;

import com.vmavropo.utils.Test;
import com.vmavropo.utils.common.WaitFor;
import lombok.Getter;

public class Auth {

    Test test;
    @Getter
    AuthValidations validate;

    WaitFor wait;


    public Auth(Test test) {
        this.test = test;
        validate = new AuthValidations(test);
        wait = new WaitFor(test);
    }

    public void login(String userRole) {
        test.waitFor().elementToBeDisplayed(AuthElements.USERNAME);
        test.element().input().setText(AuthElements.USERNAME, test.envDataConfig().getUsername(userRole));
        test.element().input().setText(AuthElements.PASSWORD, test.envDataConfig().getPassword(userRole));
        test.element().button().click(AuthElements.SIGN_IN_BUTTON);
    }

}
