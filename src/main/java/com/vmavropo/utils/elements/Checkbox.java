package com.vmavropo.utils.elements;

import com.vmavropo.utils.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Checkbox {

    Test test;

    WebDriver driver;

    public Checkbox(Test test) {
        this.test = test;
        driver = test.webDriverFactory().getDriver();
    }

    public void select(By element) {
        if (!driver.findElement(element).isSelected()) {
            driver.findElement(element).click();
        }
    }

    public void deselect(By element) {
        if (driver.findElement(element).isSelected()) {
            driver.findElement(element).click();
        }
    }

}
