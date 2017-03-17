package step;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import page_object.MainPage;

/**
 * Created by Ievgen_Ostapenko on 3/17/2017.
 */
public class VideoStep {

	MainPage mainPage = page(MainPage.class);


	@When("^I navigate to Main page$")
    public void navigateTo() {
        open("http://ecsa00400275.epam.com:4503/content/ellen/en/content.html");
    }

    @When("^I search Video with the following values:$")
    public void searchVideoByTags(final DataTable table) {
		mainPage.getSearchTextBox().setValue(table.getGherkinRows().get(0).getCells().get(0));
    }

    @Then("^I should see the video with the following values:$")
    public void verifyVideoTags(final DataTable table) {

    }
}
