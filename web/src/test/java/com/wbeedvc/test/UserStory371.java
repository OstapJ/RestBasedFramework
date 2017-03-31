package com.wbeedvc.test;

import com.wbeedvc.taf.Configuration;
import com.wbeedvc.taf.pageobject.HomePage;
import com.wbeedvc.taf.pageobject.ProfilePage;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.Wait;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

public class UserStory371 extends Configuration {

    @Test(description = "WBEEDVC-118: As web site user I want to log in using my Facebook account. Successful login"
            , groups = {"smoke"})
    public void verifySuccessfulLoginViaFaceBook() {

        String facebookEmail = "Autoedvc1@gmail.com";
        String facebookPassword = "Autoedvc1Autoedvc1";

        ProfilePage profilePage = new HomePage().
                open().
                loginAndRegistrationWidget.
                openFaceBookLoginPage().
                faceBookLoginWidget.
                logInViaFaceBook(facebookEmail, facebookPassword);
        //TODO: wait to be removed, after any component can be checked for visibility on the page
        Wait().until(urlContains(ProfilePage.RELATIVE_PATH));
    }
}