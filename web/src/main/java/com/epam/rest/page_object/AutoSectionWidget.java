package com.epam.rest.page_object;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ElementsContainer;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

@Component
public class AutoSectionWidget extends ElementsContainer {

    public AutoSectionWidget() {
    }

    // @FindBy(css = "ul[class='b-autosections-list'] li a")
    // public ElementsCollection chapterLinks;

    private SelenideElement autoSectionWidget = $("ul[class='b-autosections-list']");
    public ElementsCollection chapterLinks = autoSectionWidget.findAll("li a");

}
