package stepDefinitions;


import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


@CucumberOptions(strict = false,
        features = {
                "src/test/java/applicationFeature/ContentCategories.feature",
                "src/test/java/applicationFeature/Global.feature",
                "src/test/java/applicationFeature/HomePage.feature",
                "src/test/java/applicationFeature/productDetails.feature",
                "src/test/java/applicationFeature/ContentList.feature",
                "src/test/java/applicationFeature/ContentDetailsFeatures.feature",
                "src/test/java/applicationFeature/ContentDetailsHighLevel.feature",
        },
        plugin = {"pretty",
                "html:target/site/cucumber-reoprt/htmlOutput/",
                "json:target/jsonOutput/cucumber.json",
//                "testng:target/xmlOutput1/results.xml"
        },
        tags = {"~@Skip"
//                , "@DBS"
                , "@Check"
			, "@POSB"
//			, "@Test"
        }
)
public class RunCukesHealthCheckTest extends WebDriverTestCase {
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
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