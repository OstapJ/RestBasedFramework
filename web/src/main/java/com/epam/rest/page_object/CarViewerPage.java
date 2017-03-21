package com.epam.rest.page_object;

import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import org.springframework.stereotype.Component;

@Component
public class CarViewerPage extends AbstractPage {

    public ElementsCollection costLabels = $$("div[class='autoba-hd-details']").get(0).findAll("[class='other-costs']>span");

}
