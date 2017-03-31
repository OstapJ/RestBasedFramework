package com.wbeedvc.taf.pageobject.widget;

import com.codeborne.selenide.SelenideElement;
import com.wbeedvc.taf.pageobject.FaceBookLoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginAndRegistrationWidget {

    private static final String REGISTER_BUTTON_TEXT = "Register";

    public SelenideElement rootElement;
    public SelenideElement emailInput = $("input[name='email']"),
                           emailValidationMessage = $("input[name='email']~span.form__text-error"),
                           passwordInput = $("input[name='password']"),
                           submitButton = $("button[type='submit']"),
                           signUpLink = $(".btn_link[href='#/registration']"),
                           loginViaFacebookLink = $("a.fb-login");

    public LoginAndRegistrationWidget(SelenideElement rootElement) {
        this.rootElement = rootElement;
    }

    public LoginAndRegistrationWidget enterEmail(final String email) {
        emailInput.setValue(email);
        return this;
    }

    public LoginAndRegistrationWidget enterPassword(final String password) {
        passwordInput.setValue(password);
        return this;
    }

    public LoginAndRegistrationWidget clickSubmitButton() {
        submitButton.click();
        return this;
    }

    public LoginAndRegistrationWidget clickSignUpLink() {
        signUpLink.click();
        return this;
    }

    public LoginAndRegistrationWidget clickPasswordInput() {
        passwordInput.click();
        return this;
    }

    public LoginAndRegistrationWidget registerUser(final String email, final String password) {
        return clickSignUpLink().
                waitForRegistrationForm().
                logIn(email, password);
    }

    public LoginAndRegistrationWidget waitForRegistrationForm() {
        submitButton.shouldHave(exactText(REGISTER_BUTTON_TEXT));
        return this;
    }

    public LoginAndRegistrationWidget logIn(final String email, final String password) {
        return enterEmail(email).
                enterPassword(password).
                clickSubmitButton();
    }

    public FaceBookLoginPage openFaceBookLoginPage() {
        loginViaFacebookLink.click();
        switchBetweenWindows();
        return new FaceBookLoginPage();
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