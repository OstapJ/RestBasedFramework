package com.wbeedvc.taf.pageobject.widget;

import com.codeborne.selenide.SelenideElement;
import com.wbeedvc.taf.pageobject.ProfilePage;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.Wait;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class FaceBookLoginWidget {

    public SelenideElement rootElement;
    public SelenideElement emailInput = $("#email"),
                           passwordInput = $("#pass"),
                           logInButton = $("input[type='submit']");

    public FaceBookLoginWidget(SelenideElement rootElement) {
        this.rootElement = rootElement;
    }

    public FaceBookLoginWidget waitForFacebookLoginForm() {
        emailInput.shouldBe(visible);
        return this;
    }

    public FaceBookLoginWidget logInFaceBook(final String email, final String password) {
        emailInput.setValue(email);
        passwordInput.setValue(password);
        logInButton.click();
        return this;
    }

    public ProfilePage logInViaFaceBook(final String email, final String password) {
        logInFaceBook(email, password);
        switchBetweenWindows();
        return new ProfilePage();
    }

    //TODO: move this method to common class
    public void switchBetweenWindows() {
        String currentWindowHandle = getWebDriver().getWindowHandle();
        Wait().until(ExpectedConditions.numberOfWindowsToBe(2));
        String requiredWindowHandle = getWebDriver().getWindowHandles().stream()
                .filter(window -> !window.equals(currentWindowHandle)).findFirst().get();
        switchTo().window(requiredWindowHandle);
    }
}