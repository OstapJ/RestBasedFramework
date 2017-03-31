package com.wbeedvc.test;

import com.codeborne.selenide.CollectionCondition;
import com.jayway.restassured.path.json.JsonPath;
import com.wbeedvc.taf.Configuration;
import com.wbeedvc.taf.annotation.TestData;
import com.wbeedvc.taf.dto.CarDto;
import com.wbeedvc.taf.pageobject.CarMarketPage;
import com.wbeedvc.taf.pageobject.MainPage;
import org.testng.annotations.Test;

public class UserStory2015 extends Configuration {

	@Test(description = "WBEEDVC-117 - Verify that Car search functionality works as expected", groups = {
			"smoke" }, dataProvider = DATA_PROVIDER_METHOD)
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
