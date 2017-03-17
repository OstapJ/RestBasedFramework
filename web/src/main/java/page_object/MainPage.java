package page_object;

import org.openqa.selenium.support.FindBy;

import com.codeborne.selenide.SelenideElement;

public class MainPage extends AbstractPage
{

    @FindBy(css = "input.search")
    public SelenideElement searchTextBox;

    @FindBy(css = "li.grid__item ul")
    public SelenideElement login;

    public SelenideElement getSearchTextBox()
    {
        return searchTextBox;
    }

    public void clickAnyAvailableDate()
    {
//        popupCalendar.$(xpath("(./div[1]/button)[2]")).click();
//        popupCalendar.$("span[data-ng-click]").click();
//        popupCalendar.$("span[data-ng-click]").click();
    }
}
