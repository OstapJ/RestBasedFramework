package com.epam.rest.page_object;

import static com.codeborne.selenide.Selenide.$;

import org.springframework.stereotype.Component;

import com.codeborne.selenide.SelenideElement;

@Component
public class MainPage extends AbstractPage {

    public SelenideElement markDropDown = $("#car-1>select"),
            modelDropDown = $("#car-2>select"),
            searchButton = $("[class='btn-green-2 btn']");


    public MainPage() {
    }

    @Override public String toString()
    {
        return "MainPage{}";
    }
}
