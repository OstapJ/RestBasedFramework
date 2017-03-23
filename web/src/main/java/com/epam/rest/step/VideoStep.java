package com.epam.rest.step;

import static com.codeborne.selenide.Selenide.open;

import javax.annotation.PostConstruct;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Screenshots;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.codeborne.selenide.Configuration;
import com.epam.rest.page_object.CarMarketPage;
import com.epam.rest.page_object.CarViewerPage;
import com.epam.rest.page_object.MainPage;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.context.annotation.Lazy;

/**
 * Created by Ievgen_Ostapenko on 3/17/2017.
 */
public class VideoStep extends AbstractStepDefinition{

//    @Autowired
//    MainPage mainPage;
//
//    @Autowired
//    CarMarketPage carMarketPage;
//
//    @Autowired
//    CarViewerPage carViewerPage;

    MainPage mainPage = new MainPage();
    CarMarketPage carMarketPage = new CarMarketPage();
    CarViewerPage carViewerPage = new CarViewerPage();

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoStep.class);

    @PostConstruct
    public void init(){
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
        Configuration.browser = "chrome";

    }

    @When("^I navigate to Main page$")
    public void navigateToHome() {
        open("https://www.onliner.by/");
    }

    @When("^I search Video with the following values:$")
    public void searchCar()  {
        mainPage.selectMark("BMW");
        mainPage.selectModelDropDown("   120");
        mainPage.clickSearchButton();
        LOGGER.info("RP_MESSAGE#FILE#{}#{}", Screenshots.takeScreenShotAsFile().getAbsolutePath(),
                "Proof the screenshot could be saved in RP");
//        carMarketPage.autoSectionWidget.chapterLinks.get(0).click();
    }

    @Then("^I should see the video with the following values:$")
    public void selectCar() {
        carMarketPage.clickCarLinkByText("BMW 120");
    }



        @Then("^I should see the video with the following values:$")
    public void verifyCarPrice() {
            carViewerPage.costLabels.shouldHave(CollectionCondition.texts("8250 $", "7670 €"));

        }
}
