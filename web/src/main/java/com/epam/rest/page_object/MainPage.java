package com.epam.rest.page_object;

import com.codeborne.selenide.SelenideElement;
import com.epam.rest.step.VideoStep;
import com.epam.rest.util.BeanUtilHelper;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends AbstractPage {

	public SelenideElement markDropDown = $("#car-1>select");
	public SelenideElement modelDropDown = $("#car-2>select");
	public SelenideElement searchButton = $("[class='btn-green-2 btn']");

	public void clickSearchButton() {
		searchButton.click();
	}

	public void setMarkDropDown(final String mark) {
		markDropDown.selectOption(mark);
	}

	public void setModelDropDown(final String model) {
		modelDropDown.selectOption(model);
	}

	public void fillSearchCriteria(final VideoStep.CarDto object) {
		BeanUtilHelper.copyProperties(this, object, "Failed to fill search criteria");
	}

	@Override public String toString() {
		return "MainPage{}";
	}
}
