package com.epam.rest.page_object;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.epam.rest.page_object.widget.AutoSectionWidgetCustom;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CarMarketPage extends AbstractPage {

	public AutoSectionWidgetCustom autoSectionWidget;
	public ElementsCollection carLinks = $$("table[class='autoba-table adverts-table'] td h2 a");

	public CarMarketPage() {
		autoSectionWidget = new AutoSectionWidgetCustom($("div[class='b-autosections']"));
	}

	public void clickCarLinkByText(final String text) {
		carLinks.filterBy(Condition.text(text)).first().click();
	}
}
