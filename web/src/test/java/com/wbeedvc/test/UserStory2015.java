package com.wbeedvc.test;

import com.codeborne.selenide.CollectionCondition;
import com.wbeedvc.taf.Configuration;
import com.wbeedvc.taf.annotation.TestData;
import com.wbeedvc.taf.dto.CarDto;
import com.wbeedvc.taf.pageobject.CarMarketPage;
import com.wbeedvc.taf.pageobject.MainPage;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by Ievgen_Ostapenko on 3/23/2017.
 */
public class UserStory2015 extends Configuration {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserStory2015.class);


	@Test(description = "WBEEDVC-117 - Verify that Car search functionality works as expected", groups = {"smoke"}, dataProvider = DATA_PROVIDER_METHOD)
	@TestData("verifyCarSearch.json")
	public void verifyCarSearch(final JsonPath expData) throws IOException {
		LOGGER.info("Just wanna check that Logger works as expected");
		CarDto car = expData.getObject(CarDto.NAME, CarDto.class);
		CarMarketPage carMarketPage = new MainPage().
				open().
				searchByCriteria(car);
		carMarketPage.clickCarLinkByText(car.getMarkDropDown()).
				costLabels.shouldHave(CollectionCondition.texts(car.getCarPrices()));
	}

	@Test(dataProvider = DATA_PROVIDER_METHOD)
	@TestData("verifyCarSearch.json")
	public void verifyCarSearchDouble(final JsonPath expData) throws IOException {
		CarDto car = expData.getObject(CarDto.NAME, CarDto.class);
		CarMarketPage carMarketPage = new MainPage().
				open().
				searchByCriteria(car);
		carMarketPage.clickCarLinkByText(car.getMarkDropDown()).
				costLabels.shouldHave(CollectionCondition.texts(car.getCarPrices()));
	}
}
