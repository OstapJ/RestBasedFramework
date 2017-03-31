package com.wbeedvc.taf.pageobject;

import com.wbeedvc.taf.pageobject.widget.FaceBookLoginWidget;

import static com.codeborne.selenide.Selenide.$;

public class FaceBookLoginPage extends AbstractPage {

    public FaceBookLoginWidget faceBookLoginWidget;

    public FaceBookLoginPage() {
        this.faceBookLoginWidget = new FaceBookLoginWidget($("#loginform"));
    }
}