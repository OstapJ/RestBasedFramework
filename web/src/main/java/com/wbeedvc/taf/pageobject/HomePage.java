package com.wbeedvc.taf.pageobject;

import com.codeborne.selenide.Selenide;
import com.wbeedvc.taf.pageobject.widget.LoginAndRegistrationWidget;

import static com.codeborne.selenide.Selenide.$;

public class HomePage extends AbstractPage {

    public LoginAndRegistrationWidget loginAndRegistrationWidget;

    public HomePage() {
        loginAndRegistrationWidget = new LoginAndRegistrationWidget($("div[data-component='LoginTab']"));
    }

    public HomePage open() {
        Selenide.open("");
        return this;
    }
}