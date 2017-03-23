package com.epam.rest.page_object;

import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarMarketPage extends AbstractPage {

//    @Autowired
    public AutoSectionWidget autoSectionWidget = new AutoSectionWidget();


    //    public ElementsCollection carLinks = $$("table[class='autoba-table adverts-table'] td h2 a");

    public ElementsCollection carLinks = $$("table[class='autoba-table adverts-table'] td h2 a");
//    @FindBy(css = "div[class='b-autosections']")
//    public AutoSectionWidget autoSectionWidget;

//    public AutoSectionWidget autoSectionWidget = $(By.cssSelector("div[class='b-autosections']"));

    public CarMarketPage()
    {
    }


    public void clickCarLinkByText(final String text){
        carLinks.filterBy(Condition.text(text)).first().click();
    }
}
