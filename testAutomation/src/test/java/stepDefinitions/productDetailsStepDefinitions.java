package stepDefinitions;

import automation.drivers.CommonActions;
import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Created by vinaykumarp on 1/30/2017.
 */
public class productDetailsStepDefinitions extends CommonActions {
    @Given("^Open the product details page$")
    public void open_the_product_details_page() throws Throwable {
        new pages.webMVP.ContentListPage().launchApplication();
    }

    @Then("^No error message will be displayed$")
    public void no_error_message_will_be_displayed() throws Throwable {
        new pages.webMVP.productDetailsPage().noErrorMessage();
    }

    @Then("^URL will not be redirected to default page\\.$")
    public void url_will_not_be_redirected() throws Throwable {
        new pages.webMVP.productDetailsPage().currentURLnotDefault();
    }

    @Then("^error message will be displayed$")
    public void error_message_will_be_displayed() throws Throwable {
        new pages.webMVP.productDetailsPage().NavigationErrorMessage();
    }

    @Then("^URL will be redirected to default page\\.$")
    public void url_will_be_redirected_to_default_page() throws Throwable {
        new pages.webMVP.productDetailsPage().currentURLIsDefault();
    }

    @Then("^Validate the list of topics for products\\.$")
    public void Validate_the_list_of_topics() throws Throwable {
        new pages.webMVP.productDetailsPage().topicsValidateForProducts();
    }

    @Then("^bread comb validation for product details$")
    public void breadCombValidation() throws Throwable {
        new pages.webMVP.productDetailsPage().breadCombValidation();
    }

    @Then("^Hero banner validation for product details$")
    public void heroBannerValidation() throws Throwable {
        new pages.webMVP.productDetailsPage().heroBannerValidation();
        new pages.webMVP.productDetailsPage().breadCombValidation();
    }

    @Then("^Select Product$")
    public void Select_Product() throws Throwable {
        new pages.webMVP.productDetailsPage().SelectProduct();
    }

    @Then("^Verify Product Details Page$")
    public void Verify_Product_Details_Page() throws Throwable {
        new pages.webMVP.productDetailsPage().VerifyProductDetails();
    }

    @Then("^Verify Poll Component$")
    public void Verify_Poll_Component() throws Throwable {
        new pages.webMVP.productDetailsPage().VerifyPollComponent();
    }

    @Then("^Verify Sticky Navigation$")
    public void Verify_Sticky_Navigation() throws Throwable {
        new pages.webMVP.productDetailsPage().VerifyStickyNavigation();
    }

    @Then("^Verify featured section$")
    public void Verify_featured_section() throws Throwable {
        new pages.webMVP.productDetailsPage().VerifyFeaturedSection();
    }

    @Then("^Verify pid for featured section$")
    public void Verify_pid_for_featured_section() throws Throwable {
        new pages.webMVP.productDetailsPage().VerifyPIDforFeatures();
    }
}
