package stepDefinitions;

import automation.drivers.CommonActions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Created by vinaykumarp on 1/27/2017.
 */

public class GlobalstepDefinitions extends CommonActions {

    @Given("^Launch any FLP page$")
    public void launchApplication() throws Throwable {
        new pages.webMVP.ContentListPage().launchApplication();
    }

    @Then("^Verify Header section$")
    public void Verify_Header_section() throws Throwable {
        new pages.webMVP.GlobalPage().verifyHeaderSection();
    }

    @Then("^Verify Footer section$")
    public void Verify_Footer_section() throws Throwable {
        new pages.webMVP.GlobalPage().verifyFooterSection();
    }

    @Then("^Verify the Short tile in the Header$")
    public void Verify_the_Short_tile_in_the_Header() throws Throwable {
        new pages.webMVP.GlobalPage().verifyShortTileInHeaderSection();
    }
}
