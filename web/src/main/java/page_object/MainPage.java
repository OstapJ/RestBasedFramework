package page_object;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends AbstractPage {

//    @FindBy(css = "input.search")
//    public SelenideElement searchTextBox;
//
//    @FindBy(css = "li.grid__item ul")
//    public SelenideElement login;

    public SelenideElement searchTextBox = $("input.search");

    public SelenideElement login = $("li.grid__item ul");
    public SelenideElement searchTextArea = $("#lst-ib");



    public SelenideElement getSearchTextBox() {
        return searchTextBox;
    }

    public void clickAnyAvailableDate() {
//        popupCalendar.$(xpath("(./div[1]/button)[2]")).click();
//        popupCalendar.$("span[data-ng-click]").click();
//        popupCalendar.$("span[data-ng-click]").click();
    }
}
