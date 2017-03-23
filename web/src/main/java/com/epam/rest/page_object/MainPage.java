package com.epam.rest.page_object;

import static com.codeborne.selenide.Selenide.$;

import org.springframework.stereotype.Component;

import com.codeborne.selenide.SelenideElement;

@Component
public class MainPage extends AbstractPage {

    public SelenideElement markDropDown = $("#car-1>select");
    public SelenideElement modelDropDown = $("#car-2>select");
    public SelenideElement searchButton = $("[class='btn-green-2 btn']");


    public MainPage() {
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public void selectMark(final String mark){
        markDropDown.selectOption(mark);
    }

    public void selectModelDropDown(final String model){
        modelDropDown.selectOption(model);
    }

    @Override public String toString()
    {
        return "MainPage{}";
    }
}
