package com.wbeedvc.taf.pageobject;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.wbeedvc.taf.dto.CarDto;
import com.wbeedvc.taf.util.BeanUtilHelper;

import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    public SelenideElement markDropDown = $("#car-1>select"),
                           modelDropDown = $("#car-2>select"),
                           searchButton = $("[class='btn-green-2 btn']");

    public SelenideElement getSearchButton() {
        return $("[class='btn-green-2 btn']");
    }

    public MainPage open() {
        Selenide.open("https://www.onliner.by/");
        return this;
    }

    public CarMarketPage clickSearchButton() {
        getSearchButton().click();
        return new CarMarketPage();
    }

    public void setMarkDropDown(final String mark) {
        markDropDown.selectOption(mark);
    }

    public void setModelDropDown(final String model) {
        modelDropDown.selectOption(model);
    }

    public CarMarketPage searchByCriteria(final CarDto object) {
        BeanUtilHelper.copyProperties(this, object, "Failed to fill search criteria");
        return clickSearchButton();
    }

}
