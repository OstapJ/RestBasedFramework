package com.wbeedvc.taf.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.wbeedvc.taf.pageobject.widget.AutoSectionWidgetCustom;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CarMarketPage {

	public AutoSectionWidgetCustom autoSectionWidget;
	public ElementsCollection carLinks = $$("table[class='autoba-table adverts-table'] td h2 a");

	public CarMarketPage() {
		autoSectionWidget = new AutoSectionWidgetCustom($("div[class='b-autosections']"));
	}

	public CarViewerPage clickCarLinkByText(final String text) {
		carLinks.filterBy(Condition.text(text)).first().click();
		return new CarViewerPage();
	}
}
