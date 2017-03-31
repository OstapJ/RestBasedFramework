package com.wbeedvc.test;

import com.wbeedvc.taf.Configuration;
import com.wbeedvc.taf.pageobject.HomePage;
import com.wbeedvc.taf.pageobject.ProfilePage;
import com.wbeedvc.taf.pageobject.widget.LoginAndRegistrationWidget;

import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.Wait;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;


public class UserStory372 extends Configuration {

    @Test(description = "WBEEDVC-117: As web site user I want to log in using my email and password. Successful Login"
            , groups = {"smoke"})
    public void verifySuccessfulLogin() {

        String registeredEmail = "autoTestUser1@gmail.com";
        String registeredPassword = "autoTestUser1@";

        LoginAndRegistrationWidget loginAndRegistrationWidget = new HomePage().
                open().
                loginAndRegistrationWidget.
                logIn(registeredEmail, registeredPassword);
        loginAndRegistrationWidget.passwordInput.should(not(visible));
        //TODO: wait to be removed, after any component can be checked for visibility on the page
        Wait().until(urlContains(ProfilePage.RELATIVE_PATH));
    }
}