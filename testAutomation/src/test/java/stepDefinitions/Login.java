package stepDefinitions;


import automation.drivers.CommonActions;
import automation.report.ReporterConstants;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;


public class Login extends CommonActions {

    @Before
    public void beforeCucumber(Scenario scenario) {
        CommonActions.scenario = scenario;
        CommonActions.sceanrioTags = scenario.getSourceTagNames();
        testDataFetch();
        getReporter().initTestCase(this.getClass().getName().substring(0, this.getClass().getName().lastIndexOf(".")), scenario.getName(), null, false);
        System.out.println(scenario.getName());
        getReporter().initTestCaseDescription(scenario.getName());
        if ("mobile".equalsIgnoreCase(getBundle().getString("automationName"))) {
            try {
                getAppiumDriver().closeApp();
                getAppiumDriver().closeApp();
            } catch (Exception e) {

                e.printStackTrace();
            }
            getAppiumDriver().launchApp();
        }

        if ("mobileWeb".equalsIgnoreCase(getBundle().getString("automationName"))) {
            getAppiumDriver().get("http://www.google.com/");
        }
    }

    @After
    public void aftercucumber(Scenario scenario) throws Exception {
        try {
            getReporter().calculateTestCaseExecutionTime();
            getReporter().closeDetailedReport();
            getReporter().updateTestCaseStatus();
            //getReporter().calculateSuiteExecutionTime();
            getReporter().createHtmlSummaryReport(ReporterConstants.APP_BASE_URL, false);
            getReporter().closeSummaryReport();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @When("^Enter Credentials and click on submit button$")
    public void enter_Credentials_and_click_on_submit_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //	scenario.write("Use is on the navigate to enter the credential when methods");
        new pages.web.LoginPage().enterCredetails();
    }

    @Then("^Home page will be displayed\\.$")
    public void home_page_will_be_displayed() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //scenario.write("Use is on the navigate to validate home page then method");
        new pages.web.TeamSiteHomePage().pageConfirmation();
        new pages.web.TeamSiteHomePage().searchPath();
        new pages.web.TeamSiteHomePage().openFileEntryForm();
        new pages.web.NewEntryForm().createEntryForm();
    }


}
