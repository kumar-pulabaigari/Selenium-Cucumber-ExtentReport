package scripts;

import automation.drivers.CommonActions;
import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import pages.web.googlHome;

@QAFTestStepProvider
public class WebSteps extends CommonActions {

    @Given("^Enter text in text field$")
    public void googleSeach() throws Throwable {
        try {
            new googlHome().verifyText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^Click on Search button$")
    public void googleSeach1() throws Throwable {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}