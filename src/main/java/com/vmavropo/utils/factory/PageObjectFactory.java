package com.vmavropo.utils.factory;

import com.vmavropo.ui.authentication.Auth;
import com.vmavropo.utils.Test;
import com.vmavropo.utils.common.BrowserControls;

import java.util.Objects;

public class PageObjectFactory {

    Test test;

    BrowserControls browser;

    Auth auth;

    public PageObjectFactory(Test test) {
        this.test = test;
    }

    public BrowserControls browser() {
        return Objects.requireNonNullElseGet(browser, () -> browser = new BrowserControls(test));
    }

    public Auth auth() {
        return Objects.requireNonNullElseGet(auth, () -> auth = new Auth(test));
    }

}
