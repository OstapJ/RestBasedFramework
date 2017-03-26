package com.epam.runner;

import com.codeborne.selenide.CollectionCondition;
import com.epam.rest.Configuration;
import com.epam.rest.annotation.TestData;
import com.epam.rest.dto.CarDto;
import com.epam.rest.page_object.CarMarketPage;
import com.epam.rest.page_object.MainPage;
import com.epam.rest.step.VideoStep;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Ievgen_Ostapenko on 3/23/2017.
 */
public class UserStory2015 extends Configuration {

    private VideoStep videoStep = new VideoStep();

    @Test
    public void searchCar() throws InvocationTargetException, IllegalAccessException {
        videoStep.init();
        videoStep.navigateToHome();
        videoStep.searchCar();
        videoStep.selectCar();
        videoStep.verifyCarPrice();
    }

    @Test(dataProvider = DATA_PROVIDER_METHOD)
    @TestData("verifyCarSearch.json")
    public void verifyCarSearch(final JsonPath expData) {
        CarDto car = expData.getObject(CarDto.NAME, CarDto.class);
        CarMarketPage carMarketPage = new MainPage().
                open().
                searchByCriteria(car);
        carMarketPage.clickCarLinkByText(car.getMarkDropDown()).
                costLabels.shouldHave(CollectionCondition.texts(car.getCarPrices()));

    }
}
