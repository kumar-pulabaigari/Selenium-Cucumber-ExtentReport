package stepDefinitions;

import automation.drivers.CommonActions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

//import main.automation.drivers.DriverScript;

public class ContentDetailsStepDefinitions extends CommonActions {

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

    @Given("^Open the Content details page using the url that contains Fridenly url$")
    public void open_the_Content_details_page_using_the_url_that_contains_Fridenly_url() throws Throwable {
        new pages.webMVP.contentDetailsPage().launchApplication();
    }

    @Then("^Validate the high level content details$")
    public void validate_the_high_level_content_details() throws Throwable {
        new pages.webMVP.contentDetailsPage().validateTitle();
        new pages.webMVP.contentDetailsPage().mainHeroImageValidate();

    }

    @Given("^Open the Content details page using the url that contains INVALID Fridenly url$")
    public void open_the_Content_details_page_using_the_url_that_contains_INVALID_Fridenly_url() throws Throwable {
        new pages.webMVP.contentDetailsPage().launchApplication();
    }

    @Then("^Default Page should be displayed\\.$")
    public void error_Message_should_be_displayed() throws Throwable {
        new pages.webMVP.contentDetailsPage().defaultPageURL();
    }

    @Then("^Validate the list of topics.$")
    public void Validate_the_list_of_topics() throws Throwable {
        new pages.webMVP.contentDetailsPage().topicsValidate();
    }

    @Then("^Validate the social sharing sites.$")
    public void Validate_the_social_sharing_sites() throws Throwable {
        new pages.webMVP.contentDetailsPage().socialShareValidate();
    }

    @Then("^Validate the content details$")
    public void Validate_the_content_details() throws Throwable {
        new pages.webMVP.contentDetailsPage().contentody();
    }

    @Then("^Question-\"([^\"]*)\" should be displayed as provided in the test data sheet$")
    public void questionValidation(String number) throws Throwable {

        for (int i = 1; i <= Integer.parseInt(number); i++) {
            new pages.webMVP.contentDetailsPage().questionSelection(Integer.toString(i), false);

        }
        //new pages.webMVP.contentDetailsPage().questionSelection(number, true);
        new pages.webMVP.contentDetailsPage().questionSubmitValidation(false);

    }

    @When("^select the answers for all \"([^\"]*)\"-questions$")
    public void selectQuestions(String number) throws Throwable {

        for (int i = 1; i < Integer.parseInt(number); i++) {
            new pages.webMVP.contentDetailsPage().questionSelection(Integer.toString(i), true);
            new pages.webMVP.contentDetailsPage().questionSubmitValidation(false);
        }
        new pages.webMVP.contentDetailsPage().questionSelection(number, true);
        new pages.webMVP.contentDetailsPage().questionSubmitValidation(true);

    }

    @Then("^Expired articles will not be diplayed.$")
    public void expiredArticles() throws Throwable {
        new pages.webMVP.contentDetailsPage().expiredArticles();
    }

    @When("^click on submit button$")
    public void submitValidation() throws Throwable {
//        new pages.webMVP.contentDetailsPage().questionSubmitValidation(true);
        new pages.webMVP.contentDetailsPage().questionSubmitButton();
    }

    @Then("^Verify post quiz details$")
    public void resultsQuizValidation() throws Throwable {
        Thread.sleep(3000);
        new pages.webMVP.contentDetailsPage().quizResultsSection();
    }

    @When("^select the rate-\"([^\"]*)\" this page and  enter detais, and click on submit button$")
    public void selectRatebutton(String number) throws Throwable {

        new pages.webMVP.contentDetailsPage().rateThisPageValidation(number, true);
        new pages.webMVP.contentDetailsPage().rateSubmitButton(number);
    }

    @Then("^content body validation$")
    public void contentBodyValidation() throws Throwable {
        new pages.webMVP.contentDetailsPage().articleBodyValidation();
    }

    @Then("^Validate the selected-\"([^\"]*)\" rate is confirmed.$")
    public void rateSubmitValidation(String number) throws Throwable {
        new pages.webMVP.contentDetailsPage().rateResultsSection(number);
    }

    @When("^Promo details needs to be displayed, and click on cancel button$")
    public void promoDetailsValidation() throws Throwable {
        new pages.webMVP.contentDetailsPage().promoCodeValidation(true);
    }

    @Then("^Promo code will not be displayed.$")
    public void promoCancelDetails() throws Throwable {
        new pages.webMVP.contentDetailsPage().promoCodeDisplay(false);
    }

    @Then("^Feature Custome details needs to be displayed$")
    public void featureCustomeDetails() throws Throwable {
        new pages.webMVP.contentDetailsPage().featureCustomValidation();
    }

    @Then("^Feature Custome bottom details needs to be displayed$")
    public void featureBottomCustomeDetails() throws Throwable {
        new pages.webMVP.contentDetailsPage().featureCustomBottomValidation();
    }

    @Then("^Feature Custome bottom-video details needs to be displayed$")
    public void featureBottomCustomDeatails() throws Throwable {
        new pages.webMVP.contentDetailsPage().featureCustomVideoBottomValidation();
    }

    @Then("^bread comb validation$")
    public void breadCombValidation() throws Throwable {
        new pages.webMVP.contentDetailsPage().breadCombValidation();
    }

    @Then("^default no of articles in a feature section is (\\d+)\\.$")
    public void default_no_of_articles_in_a_feature_section_is(int arg1) throws Throwable {
        new pages.webMVP.contentDetailsPage().defaultFeatureArticles(arg1);
    }

    @Then("^bread comb validation for one level up$")
    public void bread_comb_validation_for_one_level_up() throws Throwable {
        new pages.webMVP.contentDetailsPage().breadCombOneLevelUp();
    }

    @Then("^Validate the maximum numer of topics\\.$")
    public void validate_the_maximum_numer_of_topics() throws Throwable {
        new pages.webMVP.contentDetailsPage().maximumTopics();
    }

    @Then("^Validate the maximum numer of highlights is less than or equal to (\\d+)\\.$")
    public void validate_the_maximum_numer_of_highlights_is_less_than_or_equal_to(int size) throws Throwable {
        new pages.webMVP.contentDetailsPage().maximumHighLights(size);
    }
}
