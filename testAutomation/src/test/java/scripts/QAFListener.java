package scripts;

import automation.drivers.secureCredentials;
import com.qmetry.qaf.automation.core.QAFListenerAdapter;
import com.qmetry.qaf.automation.ui.webdriver.CommandTracker;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Capabilities;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

public class QAFListener extends QAFListenerAdapter {

    @Override
    public void beforeInitialize(Capabilities desiredCapabilities) {
        secureCredentials.secureData();
        super.beforeInitialize(desiredCapabilities);
    }

    @Override
    public void onInitialize(QAFExtendedWebDriver driver) {

        super.onInitialize(driver);
        if ("mobile".equalsIgnoreCase(getBundle().getString("automationName"))) {
            AppiumDriver appiumDriver = (AppiumDriver) driver.getUnderLayingDriver();
            try {
                appiumDriver.closeApp();
                appiumDriver.closeApp();
            } catch (Exception e) {

                e.printStackTrace();
            }
            appiumDriver.launchApp();
        }
        if ("mobileWeb".equalsIgnoreCase(getBundle().getString("automationName"))) {
            //getDriver().get("http://www.google.com/");
        }
    }

    @Override
    public void afterCommand(QAFExtendedWebElement element, CommandTracker commandTracker) {
        // TODO Auto-generated method stub
        super.afterCommand(element, commandTracker);
    }


}
