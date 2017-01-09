package com.hom.stepDefinitions;

import java.util.Collection;

import com.hom.automationDigital.extentReport.ExtentManager;
import com.hom.automationDigital.extentReport.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class StepDefinitionFeature1 extends com.hom.automationDigital.commonFunctions.CommonFunctions {

	@Before
	public void before(Scenario scenario) {
		this.scenario = scenario;
		ExtentTestManager.startTest(scenario.getName());

		Collection<String> category = scenario.getSourceTagNames();
		ExtentTestManager.assignCategory(category);

		ExtentTestManager.getTest().log(LogStatus.INFO, "scenario started..." + scenario.getName());
	}

	@Given("^User is on a google page$")
	public void given() {
		ExtentTestManager.write(scenario, LogStatus.INFO, "User is on given");
		navigateURL("http://www.google.com/");
		screenShotDriver();
	}

	@Then("^Title should be \"([^\"]*)\"\\.$")
	public void title_should_be(String expectedTitle) throws Throwable {
		String actulTitle = getTitle();
		if (expectedTitle.trim().contains(actulTitle.trim())) {
			ExtentTestManager.write(scenario, LogStatus.PASS,
					"Title has been matched with expected title is" + expectedTitle);
		} else {
			ExtentTestManager.write(scenario, LogStatus.FAIL, "Title hasn't been matched with expected title is"
					+ expectedTitle + " , actual title is " + actulTitle);
		}
	}

	@Given("^Select the Browser \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
	public void select_the_Browser(String BrowserName, String BrowserVersion, String Environment,
		String OperatingSystem, String PlatformName) {
		setDriver(BrowserName, BrowserVersion, Environment, OperatingSystem, PlatformName, scenario);
		ExtentTestManager.write(scenario, LogStatus.INFO, "Select the browser method");
		loadExcelFile();
	}

	@Given("^User is on a provided URL page$")
	public void user_is_on_a_provided_URL_page() throws Throwable {
		navigateURL(projectFile.getStringFromMap(scenario, "URL"));
		screenShotDriver();
	}

	@Then("^Title should be matched with expected value\\.$")
	public void title_should_be_matched_with_expected_value() throws Throwable {
		String expected=projectFile.getStringFromMap(scenario, "title");
		String actual=getTitle();
		if(actual.trim().equalsIgnoreCase(expected.trim())){
			ExtentTestManager.write(scenario, LogStatus.PASS, "Title has been mateched with expected title" + expected);
		} else {
			ExtentTestManager.write(scenario, LogStatus.FAIL, "Title hasn't been mateched with expected title" + expected + " , and actual title is" + actual);
		}
	}

	@After
	public void after(Scenario scenario) {
		screenShotDriver();
		if (scenario.isFailed()) {
			ExtentTestManager.write(scenario, LogStatus.FAIL, "Test case failed");
		} else {
			ExtentTestManager.write(scenario, LogStatus.PASS, "Test passed");
		}
		addScreenShotWordX();
		ExtentManager.getReporter().endTest(ExtentTestManager.getTest());
		ExtentManager.getReporter().flush();
		setCloseDriver();
		
	}
}
