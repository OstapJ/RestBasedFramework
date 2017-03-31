package com.wbeedvc.taf.pageobject;

import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.Wait;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AbstractPage {

    public void switchBetweenWindows() {
        String currentWindowHandle = getWebDriver().getWindowHandle();
        Wait().until(ExpectedConditions.numberOfWindowsToBe(2));
        String requiredWindowHandle = getWebDriver().getWindowHandles().stream()
                .filter(window -> !window.equals(currentWindowHandle)).findFirst().get();
        switchTo().window(requiredWindowHandle);
    }
}