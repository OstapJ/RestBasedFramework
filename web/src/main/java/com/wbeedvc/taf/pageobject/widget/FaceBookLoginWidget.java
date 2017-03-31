package com.wbeedvc.taf.pageobject.widget;

import com.codeborne.selenide.SelenideElement;
import com.wbeedvc.taf.pageobject.AbstractPage;
import com.wbeedvc.taf.pageobject.ProfilePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class FaceBookLoginWidget extends AbstractPage{

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
}