package com.vmavropo.utils.elements;

import com.vmavropo.utils.Test;
import com.vmavropo.utils.common.BrowserControls;
import com.vmavropo.utils.common.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Dropdown {

    Test test;

    Element element;

    BrowserControls browserControls;

    WebDriverWait webDriverWait;

    public Dropdown(Test test) {
        this.test = test;
        element = test.element();
        browserControls = new BrowserControls(test);
        webDriverWait = test.webDriverFactory().getWait();
    }

    public void select(By by, String option) {
        if (option != null && !option.isEmpty()) {
            element.button().click(by);
            webDriverWait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(@class,'dropdown-menu') and contains(@class,'open')]" +
                                    "//a[contains(.,'" + option + "')]")));
            element.button().click(By.xpath("//*[contains(@class,'dropdown-menu') and contains(@class,'open')]" +
                    "//a[contains(.,'" + option + "')]"));
        }
    }

    public void select(By by, String selectId, String option) {
        if (option != null && !option.isEmpty()) {
            element.button().click(by);
            String dropdownOptionXpath = "//*[contains(@class,'" + selectId + "') and contains(@class,'open')]" +
                    "//*[contains(@class,'dropdown-menu') and contains(@class,'open')]" +
                    "//a[contains(.,'" + option + "')]";
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dropdownOptionXpath)));
            element.button().click(By.xpath(dropdownOptionXpath));
        }
    }

    public void select(String dataId, String option) {
        if (option != null && !option.isEmpty()) {
            element.button().click(By.xpath("//button[@data-id='" + dataId + "']"));
            test.awaitFor().elementToBePresent(By.xpath("//button[@data-id='" + dataId + "' and @aria-expanded='true']" +
                    "/following-sibling::*[contains(@class,'dropdown-menu') and contains(@class,'open')]"));
            element.getElement(By.xpath("//button[@data-id='" + dataId + "']" +
                    "/following-sibling::*[contains(@class,'dropdown-menu') and contains(@class,'open')]" +
                    "//a[contains(.,'" + option + "')]")).click();
        }
    }

    public void selectMultiple(By by, String selectId, String option) {
        if (option != null && !option.isEmpty()) {
            if (option.equals("Select All")) {
                element.button().click(by);
                element.button().click(By.cssSelector("select#" + selectId + " + * button.bs-select-all "));
                element.button().click(by);
            } else {
                if (option.contains(",")) {
                    String[] optionList = option.split(",");
                    element.button().click(by);
                    for (String opt : optionList) {
                        webDriverWait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//*[contains(@class,'dropdown-menu') and contains(@class,'open')]" +
                                                "//a[contains(.,'" + opt.trim() + "')]")));
                        element.button().click(By.xpath("//*[contains(@class,'dropdown-menu') and contains(@class,'open')]" +
                                "//a[contains(.,'" + opt.trim() + "')]"));
                    }
                    element.button().click(by);
                } else {
                    select(by, option);
                    element.button().click(by);
                }
            }
        }
    }
}
