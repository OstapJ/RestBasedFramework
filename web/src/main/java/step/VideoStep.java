package step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Configuration;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by Ievgen_Ostapenko on 3/17/2017.
 */
public class VideoStep {

    @When("^I navigate to Main page$")
    public void navigateTo() {
		Configuration.browser = "chrome";
        open("http://ecsa00400275.epam.com:4503/content/ellen/en/content.html");
    }

    @When("^I search Video with the following values:$")
    public void searchVideoByTags(final DataTable table) {
		$("input.search").setValue(table.getGherkinRows().get(1).getCells().get(0));

    }

    @Then("^I should see the video with the following values:$")
    public void verifyVideoTags(final DataTable table) {

    }
}
