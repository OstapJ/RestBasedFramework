package com.wbeedvc.taf.pageobject.widget;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public class AutoSectionWidgetCustom {

	private SelenideElement rootElement;

	public AutoSectionWidgetCustom(SelenideElement rootElement) {
		this.rootElement = rootElement;
	}

	public ElementsCollection chapterLinks() {
		return rootElement.findAll("li a");
	}
}
