package com.vmavropo.utils;

import com.vmavropo.utils.common.*;
import com.vmavropo.utils.config.EnvDataConfig;
import com.vmavropo.utils.config.ResourcesConfig;
import com.vmavropo.utils.factory.ContextFactory;
import com.vmavropo.utils.factory.PageObjectFactory;
import com.vmavropo.utils.factory.WebDriverFactory;
import com.vmavropo.utils.factory.WebServiceFactory;

import java.util.Objects;

public class Test {

    PageObjectFactory pageObjectFactory;

    WebServiceFactory webServiceFactory;

    ContextFactory contextFactory;

    WebDriverFactory webDriverFactory;

    Logger logger;

    EnvDataConfig envDataConfig;

    ResourcesConfig resourceConfig;

    WaitFor waitFor;

    AWaitFor awaitFor;

    Element element;

    TestData testData;

    public Test() {
        logger = new Logger();
        contextFactory = new ContextFactory();
        pageObjectFactory = new PageObjectFactory(Test.this);
        webServiceFactory = new WebServiceFactory(Test.this);
        webDriverFactory = new WebDriverFactory(Test.this);
        logger = new Logger();
    }

    public PageObjectFactory ui() {
        return pageObjectFactory;
    }

    public WebServiceFactory api() {
        return webServiceFactory;
    }

    public ContextFactory context() {
        return contextFactory;
    }

    public WebDriverFactory webDriverFactory() {
        return webDriverFactory;
    }

    public Logger logger() {
        return logger;
    }

    public EnvDataConfig envDataConfig() {
        return Objects.requireNonNullElseGet(envDataConfig, () -> envDataConfig = new EnvDataConfig());
    }

    public ResourcesConfig resourceConfig() {
        return Objects.requireNonNullElseGet(resourceConfig, () -> resourceConfig = new ResourcesConfig());
    }

    public WaitFor waitFor() {
        return Objects.requireNonNullElseGet(waitFor, () -> waitFor = new WaitFor(this));
    }

    public AWaitFor awaitFor() {
        return Objects.requireNonNullElseGet(awaitFor, () -> awaitFor = new AWaitFor(this));
    }

    public Element element() {
        return Objects.requireNonNullElseGet(element, () -> element = new Element(Test.this));

    }

    public TestData testData() {
        return Objects.requireNonNullElseGet(testData, () -> testData = new TestData(Test.this));
    }

}
