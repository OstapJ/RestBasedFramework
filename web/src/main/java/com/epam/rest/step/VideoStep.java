package com.epam.rest.step;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.epam.rest.page_object.CarMarketPage;
import com.epam.rest.page_object.CarViewerPage;
import com.epam.rest.page_object.MainPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

import java.lang.reflect.InvocationTargetException;

import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Ievgen_Ostapenko on 3/17/2017.
 */
public class VideoStep extends AbstractStepDefinition {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoStep.class);

	MainPage mainPage = new MainPage();
	CarMarketPage carMarketPage = new CarMarketPage();
	CarViewerPage carViewerPage = new CarViewerPage();

	public void init() {
		System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
		Configuration.browser = "chrome";

	}

	@When("^I navigate to Main page$")
	public void navigateToHome() {
		open("https://www.onliner.by/");
	}

	@When("^I search Video with the following values:$")
	public void searchCar() throws InvocationTargetException, IllegalAccessException {
		CarDto carDto = new CarDto();
		carDto.setMarkDropDown("BMW");
		carDto.setModelDropDown("   120");
		mainPage.fillSearchCriteria(carDto);
		mainPage.clickSearchButton();
		LOGGER.info("RP_MESSAGE#FILE#{}#{}", Screenshots.takeScreenShotAsFile().getAbsolutePath(),
				"Proof the screenshot could be saved in RP");
		//		carMarketPage.autoSectionWidget.chapterLinks.get(0).click();
	}

	@Then("^I should see the video with the following values:$")
	public void selectCar() {
		//		carMarketPage = new CarMarketPage();
		carMarketPage.clickCarLinkByText("BMW 120");
	}

	@Then("^I should see the video with the following values:$")
	public void verifyCarPrice() {
		carMarketPage.autoSectionWidget.chapterLinks().get(0).click();

		carViewerPage.costLabels.shouldHave(CollectionCondition.texts("8250 $", "7670 €"));

	}

	public class CarDto {
		private String markDropDown;
		private String modelDropDown;

		public String getMarkDropDown() {
			return markDropDown;
		}

		public void setMarkDropDown(String markDropDown) {
			this.markDropDown = markDropDown;
		}

		public String getModelDropDown() {
			return modelDropDown;
		}

		public void setModelDropDown(String modelDropDown) {
			this.modelDropDown = modelDropDown;
		}
	}

}
