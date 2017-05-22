package stepDefinitions;

import automation.drivers.CommonActions;
import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.webMVP.WindowFlowController;

public class HomePageStepDefinitions extends CommonActions {


    @Given("^Open the Home Page page$")
    public void launchApplication() throws Throwable {
        new pages.webMVP.ContentListPage().launchApplication();
        Thread.sleep(2000);
    }

    @Given("^Open the Home Page page with invalid URL$")
    public void launchApplicationURL() throws Throwable {
        new pages.webMVP.ContentListPage().launchApplication();
        Thread.sleep(2000);
    }

    @Then("^carousel will be displayed on the home page$")
    public void carousel_will_be_displayed() throws Throwable {
        Thread.sleep(4000);
        new pages.webMVP.HomePage().caroselvalidation();
    }

    @Then("^expired items will not be displayed on home page\\.$")
    public void expired_items_will_be_displayed_in_carosel_items() throws Throwable {
        Thread.sleep(4000);
        new pages.webMVP.HomePage().caroselExpiredValidation();
    }

    @Then("^Valid items will be displayed on home page\\.$")
    public void valid_items_will_be_displayed_in_carosel_items() throws Throwable {
        Thread.sleep(4000);
        new pages.webMVP.HomePage().caroselValidValidation();
    }

    @Then("^Valid Utilities item icons will be displayed on home page\\.$")
    public void valid_Utilities_items_will_be_displayed_on_home_page() throws Throwable {
        Thread.sleep(4000);
        new pages.webMVP.HomePage().utilitiIconValidation();
    }

    @Then("^Valid Utilities item hyperlink will be displayed on home page\\.$")
    public void valid_Utilities_item_hyperlink_will_be_displayed_on_home_page() throws Throwable {
        Thread.sleep(4000);
        new pages.webMVP.HomePage().utilitiHyperLinkValidation();
    }

    @Then("^\"([^\"]*)\" will be displayed with red dot\\.$")
    public void will_be_displayed_with_red_dot(String arg1) throws Throwable {
        Thread.sleep(2000);
        new pages.webMVP.HomePage().errorMessageValidation(arg1);
    }

    @Then("^\"([^\"]*)\" will be displayed with yellow dot\\.$")
    public void will_be_displayed_with_yellow_dot(String arg1) throws Throwable {
        Thread.sleep(2000);
        new pages.webMVP.HomePage().broadcastMessageValidation(arg1);
    }

    @When("^naigate to the content details page$")
    public void naigate_to_the_content_details_page() throws Throwable {
        new pages.webMVP.HomePage().articleTile();
    }

    @When("^click on the topic under the topics section$")
    public void click_on_the_topic_under_the_topics_section() throws Throwable {
        new pages.webMVP.HomePage().topicsValidate();
    }

    @Then("^verify the content list page$")
    public void verify_the_content_list_page() throws Throwable {
        WebDriver driver = getDriver();
        new pages.webMVP.ContentListPage().clickArticleLink();
        new pages.webMVP.ContentListPage().errorMessagePresentValidate();
        new pages.webMVP.ContentListPage().articleValidate();
        new pages.webMVP.ContentListPage().clickProductsLink();
        new pages.webMVP.ContentListPage().errorMessagePresentValidate();
        new pages.webMVP.ContentListPage().clickPromoLink();
        new pages.webMVP.ContentListPage().errorMessagePresentValidate();
        if (driver.getWindowHandles().size() > 1) {
            driver.close();
        }
        new WindowFlowController().windowHanderMethod();
    }
}
