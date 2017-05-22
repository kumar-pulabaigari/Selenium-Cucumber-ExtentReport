package scripts;

import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(features = "src/com/features/paylahScenarios2.feature", plugin = "json:target/cucumber2.json")
public class RunCukesByFeatureAndCompositionTest extends WebDriverTestCase {
    private TestNGCucumberRunner testNGCucumberRunner;
    // public static ThreadLocal<String> browser = new ThreadLocal<String>();
    // public static ThreadLocal<String> automationName = new
    // ThreadLocal<String>();
    // public static ThreadLocal<String> browserVersion = new
    // ThreadLocal<String>();
    // public static ThreadLocal<String> environment = new
    // ThreadLocal<String>();
    // public static ThreadLocal<String> deviceName = new ThreadLocal<String>();
    // public static ThreadLocal<String> deviceId = new ThreadLocal<String>();
    // public static ThreadLocal<String> packageName = new
    // ThreadLocal<String>();
    // public static ThreadLocal<String> packageActivity = new
    // ThreadLocal<String>();
    // public static ThreadLocal<String> bundleID = new ThreadLocal<String>();
    //

    @BeforeClass(alwaysRun = true)
    // @Parameters({"automationName","browser","browserVersion","environment","deviceName","deviceId","packageName","packageActivity","bundleID"})
    public void setUpClass() throws Exception {
        // this.browser.set(browser);
        // this.browserVersion.set(browserVersion);
        // this.automationName.set(automationName);
        // this.environment.set(environment);
        // this.deviceName.set(deviceName);
        // this.deviceId.set(deviceId);
        // this.packageName.set(packageName);
        // this.packageActivity.set(packageActivity);
        // this.bundleID.set(bundleID);
        //

        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
    }
}