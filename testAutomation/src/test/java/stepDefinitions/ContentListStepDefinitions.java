package stepDefinitions;

import automation.drivers.CommonActions;
import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ContentListStepDefinitions extends CommonActions {

//	@Before
//	public void beforeCucumber(Scenario scenario) {
//		CommonActions.scenario = scenario;
//		testDataFetch();
// 	     getReporter().initTestCase(this.getClass().getName().substring(0,this.getClass().getName().lastIndexOf(".")), scenario.getName(), null, true);
//		System.out.println(scenario.getName());
//	}
//	
//	@After
//	public void aftercucumber(Scenario scenario) throws Exception{
//		try {
//			getReporter().calculateTestCaseExecutionTime();
//			getReporter().closeDetailedReport();
//			getReporter().updateTestCaseStatus();
//			getReporter().calculateSuiteExecutionTime();	
//			getReporter().createHtmlSummaryReport(ReporterConstants.APP_BASE_URL,true);
//			getReporter().closeSummaryReport();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
//		
//	}

    @Given("^Open the Content list page using the url$")
    public void launchApplication() throws Throwable {
        new pages.webMVP.ContentListPage().launchApplication();
    }

    @Then("^Verify that \"([^\"]*)\" message is getting displayed\\.$")
    public void verify_that_message_is_getting_displayed(String sErrorMessage) throws Throwable {
        new pages.webMVP.ContentListPage().validateTopicError(sErrorMessage);
    }

    @Then("^Article should be displayed\\.$")
    public void article_should_be_displayed() throws Throwable {
        new pages.webMVP.ContentListPage().topicsHeaderValidator();
        new pages.webMVP.ContentListPage().articleValidate();
    }

    @When("^Click on the Article filter link$")
    public void click_on_the_Article_filter_link() throws Throwable {
        new pages.webMVP.ContentListPage().topicsHeaderValidator();
        new pages.webMVP.ContentListPage().verifyAllHyperlinkHighlight();
        new pages.webMVP.ContentListPage().clickArticleLink();
    }

    @When("^Click on the Product filter link$")
    public void click_on_the_Product_filter_link() throws Throwable {
        new pages.webMVP.ContentListPage().topicsHeaderValidator();
        new pages.webMVP.ContentListPage().verifyAllHyperlinkHighlight();
        new pages.webMVP.ContentListPage().clickProductsLink();
    }

    @Then("^Product should be displayed\\.$")
    public void product_should_be_displayed() throws Throwable {
        new pages.webMVP.ContentListPage().topicsHeaderValidator();
        new pages.webMVP.ContentListPage().productsValidate();
    }

    @When("^Click on the Promo filter link$")
    public void click_on_the_Promo_filter_link() throws Throwable {
        new pages.webMVP.ContentListPage().verifyAllHyperlinkHighlight();
        new pages.webMVP.ContentListPage().topicsHeaderValidator();
        new pages.webMVP.ContentListPage().clickPromoLink();
        Thread.sleep(3000);
    }

    @Then("^Promo should be displayed\\.$")
    public void promo_should_be_displayed() throws Throwable {
        new pages.webMVP.ContentListPage().topicsHeaderValidator();
        new pages.webMVP.ContentListPage().promoValidate();
    }

    @Then("^Promo error -\"([^\"]*)\" message should be displayed\\.$")
    public void promo_error_message_should_be_displayed(String arg1) throws Throwable {
        Thread.sleep(1000);
        new pages.webMVP.ContentListPage().validateErrorMessage(arg1);
    }

    @Then("^Page Navigation will be displayed\\.$")
    public void page_Navigation_will_be_displayed() throws Throwable {
        Thread.sleep(1000);
        new pages.webMVP.ContentListPage().pageNavigationYes();
    }

    @Then("^Page Navigation will not be displayed\\.$")
    public void page_Navigation_will_not_be_displayed() throws Throwable {
        Thread.sleep(1000);
        new pages.webMVP.ContentListPage().pageNavigationNo();
    }

    @Then("^Articles should be in decesnding order in article\\.$")
    public void articles_should_be_in_decesnding_order() throws Throwable {
        Thread.sleep(1000);
        new pages.webMVP.ContentListPage().articleOrder();
    }

    @Then("^Articles should be in decesnding order in all\\.$")
    public void articles_should_be_in_decesnding_order_in_all() throws Throwable {
        Thread.sleep(1000);
        new pages.webMVP.ContentListPage().allArticleOrder();
    }

    @Then("^Filter functionality validation$")
    public void filter_functionality_validation() throws Throwable {
        Thread.sleep(1000);
        new pages.webMVP.ContentListPage().filterValidation();
    }
}
