package com.epam.rest.step;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import page_object.MainPage;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

/**
 * Created by Ievgen_Ostapenko on 3/17/2017.
 */
public class VideoStep {

    MainPage mainPage = new MainPage();
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoStep.class);

    @When("^I navigate to Main page$")
    public void navigateTo() {
        System.setProperty("webdriver.chrome.driver", "E:\\Work\\chromedriver_win32\\chromedriver.exe");
        Configuration.browser = "chrome";
        open("https://www.onliner.by/");
    }

    @When("^I search Video with the following values:$")
    public void searchVideoByTags(final DataTable table) throws InterruptedException {
//        mainPage.searchTextArea.setValue("parkland").pressEnter();
        $("#car-1>select").selectOptionByValue("BMW");
        $("#car-2>select").selectOption("   120");
        $("[class='btn-green-2 btn']").click();
        $$("table[class='autoba-table adverts-table'] td h2 a").filterBy(Condition.text("BMW 120")).first().click();
        File screenshot = Screenshots.takeScreenShotAsFile();
        LOGGER.info("RP_MESSAGE#FILE#{}#{}", screenshot.getAbsolutePath(), "Proof the screenshot could be saved in RP");
        $$("div[class='autoba-hd-details']").get(0).findAll("[class='other-costs']>span")
                .shouldHave(CollectionCondition.texts("9100 $", "8454 €"));



        System.out.println("HH");
    }

    @Then("^I should see the video with the following values:$")
    public void verifyVideoTags(final DataTable table) {

    }
}
