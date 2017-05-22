package stepDefinitions;

import automation.drivers.CommonActions;
import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ContentCategoriesStepDefinitions extends CommonActions {


    @Given("^Open the Content categories page$")
    public void launchApplication() throws Throwable {
        new pages.webMVP.ContentListPage().launchApplication();
    }

    @Given("^Open the Content Category Page page with invalid URL$")
    public void launchApplicationURL() throws Throwable {
        new pages.webMVP.ContentListPage().launchApplication();
        Thread.sleep(2000);
    }

    @Then("^carousel will be displayed\\.$")
    public void carousel_will_be_displayed() throws Throwable {
        new pages.webMVP.ContentCategoriesPage().caroselvalidation();
    }

    @Then("^expired items will not be displayed in carosel items\\.$")
    public void expired_items_will_be_displayed_in_carosel_items() throws Throwable {
        new pages.webMVP.ContentCategoriesPage().caroselExpiredValidation();
    }

    @Then("^Valid items will be displayed in carosel items\\.$")
    public void valid_items_will_be_displayed_in_carosel_items() throws Throwable {
        new pages.webMVP.ContentCategoriesPage().caroselValidValidation();
    }

    @Then("^verify content category product tile is dispayed$")
    public void VerifyProductTile() throws Throwable {
        new pages.webMVP.ContentCategoriesPage().productTile();
    }

    @Then("^verify content category promotion tile is dispayed$")
    public void VerifyPromotionTile() throws Throwable {
        new pages.webMVP.ContentCategoriesPage().promotionTileUpdated();
    }

    @Then("^verify content category article tile is dispayed$")
    public void VerifyArticleTile() throws Throwable {
        new pages.webMVP.ContentCategoriesPage().articleTile();
    }

    @Then("^verify DBS Currency Converter$")
    public void VerifyDBSCurrConverter() throws Throwable {
        new pages.webMVP.ContentCategoriesPage().dbsCurrencyConverter();
    }

    @When("^Slide up details needs to be displayed, and click on cancel button$")
    public void promoDetailsValidation() throws Throwable {
        new pages.webMVP.contentDetailsPage().promoCodeValidation(true);
    }

    @Then("^Slide up code will not be displayed.$")
    public void promoCancelDetails() throws Throwable {
        new pages.webMVP.contentDetailsPage().promoCodeDisplay(false);
    }

    @Then("^Verify Top 5 Performing Funds are displayed$")
    public void Verify_Top_5_Performing_Funds_are_displayed() throws Throwable {
        new pages.webMVP.ContentCategoriesPage().VerifyTop5PerformingFunds();
    }
}
