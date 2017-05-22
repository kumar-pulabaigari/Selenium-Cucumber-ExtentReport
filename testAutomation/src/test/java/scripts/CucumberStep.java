package scripts;

import automation.drivers.CommonActions;
import automation.report.ReporterConstants;
import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

public class CucumberStep extends CommonActions {

    //public  CReporter reporter;
    // public CommonActions actions = new CommonActions();
    public static String scenarioName;
    public static RunCukesByFeatureAndCompositionTest obj = new RunCukesByFeatureAndCompositionTest();

    @Before
    public void beforeCucumber(Scenario scenario) {

        getReporter().initTestCase(this.getClass().getName().substring(0, this.getClass().getName().lastIndexOf(".")), scenario.getName(), null, true);
        scenarioName = scenario.getName().toString();
        if ("mobile".equalsIgnoreCase(getBundle().getString("automationName"))) {
            try {

                getAppiumDriver().closeApp();
                getAppiumDriver().closeApp();
            } catch (Exception e) {

                e.printStackTrace();
            }
            getAppiumDriver().launchApp();
        } else if ("web".equalsIgnoreCase(getBundle().getString("automationName"))) {

            getwebDriver().navigate().to("http://www.google.com/");

        }

    }


    @After
    public void aftercucumber(Scenario scenario) throws Exception {

        getReporter().calculateTestCaseExecutionTime();
        getReporter().closeDetailedReport();
        getReporter().updateTestCaseStatus();
        getReporter().calculateSuiteExecutionTime();
        getReporter().createHtmlSummaryReport(ReporterConstants.APP_BASE_URL, true);
        getReporter().closeSummaryReport();


    }


}