package com.wbeedvc.test;

import com.wbeedvc.taf.Configuration;
import com.wbeedvc.taf.pageobject.HomePage;
import com.wbeedvc.taf.pageobject.widget.LoginAndRegistrationWidget;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.exactText;

public class UserStory361 extends Configuration {

    @Test(description = "WBEEDVC-113 - Verify successful registration", groups = {"smoke"})
    public void verifySuccessfulRegistration() {

        String randomEmail = new StringBuilder().
                append("autotest").
                append(RandomStringUtils.randomAlphanumeric(10)).
                append("@gmail.com").toString();
        String password = "Autotest2017@";

        LoginAndRegistrationWidget loginAndRegistrationWidget = new HomePage().
                open().
                loginAndRegistrationWidget.
                registerUser(randomEmail, password);
        loginAndRegistrationWidget.submitButton.shouldHave(exactText("Login"));
    }

    @Test(description = "WBEEDVC-113 - Verify registration is failed when email is incorrect", groups = {
            "smoke"})
    public void verifyRegistrationFailure() {

        String invalidEmail = "autotest99gmail.com";

        LoginAndRegistrationWidget loginAndRegistrationWidget = new HomePage().
                open().
                loginAndRegistrationWidget.
                clickSignUpLink().
                waitForRegistrationForm().
                enterEmail(invalidEmail).
                clickPasswordInput();
        loginAndRegistrationWidget.emailValidationMessage.shouldHave(exactText("Invalid email address"));
    }
}