package automation.drivers;

import automation.dataDriver.ExcelReader;
import automation.report.CReporter;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import com.qmetry.qaf.automation.ui.util.QAFWebElementWait;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;
import cucumber.api.Scenario;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

public class CommonActions extends WebDriverTestCase {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = Logger.getLogger(CommonActions.class);
    public List<String> windowHandlerlist = new ArrayList<String>();
    public static Scenario scenario = null;
    public static Collection<String> sceanrioTags = null;
//    public String scenarioName;
    public static ExcelReader xlsrdr;
//    public static RunCukesByFeatureAndCompositionTest obj = new RunCukesByFeatureAndCompositionTest();
    /**
     * The msg click success.
     */
    private final String msgClickSuccess = "Successfully Clicked On ";
    /**
     * The msg click failure.
     */
    private final String msgClickFailure = "Unable to Click on";
    /**
     * The msg type success.
     */
    private final String msgTypeSuccess = "Successfully Typed On ";
    /**
     * The msg type failure.
     */
    private final String msgTypeFailure = "Unable To Type On ";
    /**
     * The msg is element found success.
     */
    private final String msgIsElementFoundSuccess = "Successfully Found ";
    /**
     * The msg is element found failure.
     */
    private final String msgIsElementFoundFailure = "Unable To Found Element ";

    public static CReporter getReporter() {
        if (!ConfigurationManager.getBundle().containsKey("dbs.reporting")) {
            if (getBundle().getString("automationName").equalsIgnoreCase("mobile")) {
                CReporter creporter = CReporter.getCReporter(getBundle().getString("automationName"),
                        getBundle().getString("environment"), getBundle().getString("deviceName"), true);
                ConfigurationManager.getBundle().setProperty("dbs.reporting", creporter);
            } else {
                CReporter creporter = CReporter.getCReporter(getBundle().getString("automationName"), getBundle().getString("browser"), getBundle().getString("browserVersion"), true);
                ConfigurationManager.getBundle().setProperty("dbs.reporting", creporter);
            }
        }
        return (CReporter) ConfigurationManager.getBundle().getObject("dbs.reporting");

    }

    public boolean testDataFetch() {
        String testdataSheet = null, sheetName = null;
        Properties prop = new Properties();
        InputStream input = null;

        Properties prop1 = new Properties();
        InputStream input1 = null;

        try {
            input = new FileInputStream(".//resources//config.properties");
            prop.load(input);
            input1 = new FileInputStream(".//resources//applicationURL.properties");
            prop1.load(input1);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // load a properties file
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (prop1.getProperty("env.name").trim().toString().equalsIgnoreCase("UAT")) {
            testdataSheet = prop.getProperty("uat.EXECUTION_SCRIPT_FILE_EXCEL").trim().toString();
            sheetName = prop.getProperty("uat.sheetName");
            xlsrdr = new ExcelReader(testdataSheet, sheetName);
        } else if (prop1.getProperty("env.name").trim().toString().equalsIgnoreCase("AWSPreview")) {
            testdataSheet = prop.getProperty("AWSPreview.EXECUTION_SCRIPT_FILE_EXCEL").trim().toString();
            sheetName = prop.getProperty("uat.sheetName");
            xlsrdr = new ExcelReader(testdataSheet, sheetName);
        } else if (prop1.getProperty("env.name").trim().toString().equalsIgnoreCase("AWSProd")) {
            testdataSheet = prop.getProperty("AWSProd.EXECUTION_SCRIPT_FILE_EXCEL").trim().toString();
            sheetName = prop.getProperty("uat.sheetName");
            xlsrdr = new ExcelReader(testdataSheet, sheetName);
        }
        return true;
    }

    public boolean isEnabledButton(By locator, String locatorName, boolean expected) throws Throwable {
        boolean status = false;
        try {

            WebDriverWait wait = new WebDriverWait(getDriver(), 60);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            getDriver().findElement(locator).isEnabled();
            status = true;


            // status = true;
        } catch (Exception e) {
            status = false;
            //e.printStackTrace();
            //LOG.info(e.getMessage());
            getReporter().failureReport("Enabled", this.msgTypeFailure + locatorName);
            //throw e;
        }
        if (status == expected) {
            getReporter().SuccessReport("IsEnabled:" + locatorName, "Provided element's enablement status is as expected " + expected);
        } else {
            getReporter().failureReport("IsEnabled:" + locatorName, "Provided element's enablement status is NOT as expected " + expected);
        }

        return status;

    }

    public boolean launch(String LaunchName) throws Throwable {
        boolean status = false;
        // driverreporterSelect();
        try {
            System.out.println("Launch URL/App  ");
            getDriver().get(LaunchName);
            getReporter().SuccessReport("Launch URL/App ", this.msgClickSuccess + LaunchName);
            status = true;

        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            getReporter().failureReport("Launch URL/App", this.msgClickFailure + LaunchName, getDriver());
            throw e;
        }
        return status;

    }

    public boolean pageTitleValidate(String expectedTitle) throws Throwable {
        boolean status = false;
        String aPageTitle = "Dummy";
        try {

            aPageTitle = getDriver().getTitle();
            if (aPageTitle.trim().equals(expectedTitle.trim())) {
                getReporter().SuccessReport("Page Title", " is matched with expected value:" + expectedTitle);
            } else {
                getReporter().failureReport("Page Title",
                        " is not matched with expected value:" + expectedTitle + ", actual title is " + aPageTitle);
            }

            System.out.println("Page Title  " + aPageTitle);
            status = true;

        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
        }
        return status;

    }

    public boolean pageURLValidate(String expectedURL) throws Throwable {
        boolean status = false;
        String aPageURL = "Dummy";
        try {
            aPageURL = getDriver().getCurrentUrl();
            if (aPageURL.trim().equals(expectedURL.trim()) || aPageURL.trim().contains(expectedURL.trim())) {
                getReporter().SuccessReport("Page URL", " is matched with expected value:" + expectedURL);
            } else {
                getReporter().failureReport("Page URL",
                        " is not matched with expected value:" + expectedURL + ", actual URL is " + aPageURL);
            }

            System.out.println("Page url   " + aPageURL);
            status = true;

        } catch (Exception e) {

            status = false;
            LOG.info(e.getMessage());
            throw e;

        }
        return status;

    }

    public boolean getTextValidate(By LocatorName, String expectedText) throws Throwable {
        boolean status = false;
        String aText = "Dummy";
        Thread.sleep(4000);
        try {
            new QAFWebElementWait(getDriver().findElement(LocatorName), 10000);

            aText = getDriver().findElement(LocatorName).getText();
            if (aText.trim().equals(expectedText.trim())) {
                getReporter().SuccessReport("Text contains", " is matched with expected value:" + expectedText);
            } else {
                getReporter().failureReport("Text contains",
                        " is not matched with expected value:" + expectedText + ", actual text is " + aText);
            }

            System.out.println("Get Element Text is  " + aText);
            status = true;

        } catch (Exception e) {

            status = false;
            LOG.info(e.getMessage());

            throw e;

        }
        return status;

    }

    public boolean imagePathValidate(By LocatorName, String ePath) throws Throwable {
        boolean status = false;
        String apath = "Dummy";
        try {

            apath = getDriver().findElement(LocatorName).getAttribute("src");
            if (apath.trim().contains(ePath.trim())) {
                getReporter().SuccessReport("Image path ", " is matched with expected value:" + ePath);
            } else {
                getReporter().failureReport("Image path ",
                        " is not matched with expected value:" + ePath + ", actual path is " + apath);
            }

            System.out.println("Get Element Text is  " + apath);
            status = true;

        } catch (Exception e) {

            status = false;
            LOG.info(e.getMessage());

            throw e;

        }
        return status;

    }

    public boolean click(By locator, String locatorName) throws Throwable {
        if (getDriver().getCapabilities().getBrowserName().trim().contains("Internet") || getDriver().getCapabilities().getBrowserName().trim().equalsIgnoreCase("Internet Explorer")) {
            return jclick(locator, locatorName);
        } else {
            boolean status = false;
            try {
                QAFWebElement ele = new QAFExtendedWebElement(locator);
                //ele.waitForVisible();
                ele.click();
                System.out.println("Click on " + locator);
                getReporter().SuccessReport("Click", this.msgClickSuccess + locatorName);

                status = true;

            } catch (Exception e) {
                getReporter().failureReport(locatorName + " click ", this.msgIsElementFoundFailure + locatorName,
                        getDriver());
            }
            return status;
        }

    }

    public boolean isEnabled(By locator, String locatorName, boolean expected) throws Throwable {
        boolean status = false;
        try {

            WebDriverWait wait = new WebDriverWait(getDriver(), 60);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            getDriver().findElement(locator).isEnabled();
            status = true;


            // status = true;
        } catch (Exception e) {
            status = false;
            //e.printStackTrace();
            //LOG.info(e.getMessage());
            getReporter().failureReport("Enabled", this.msgTypeFailure + locatorName);
            //throw e;
        }
        if (status == expected) {
            getReporter().SuccessReport("IsEnabled:" + locatorName, "Provided element's enablement status is as expected " + expected);
        } else {
            getReporter().failureReport("IsEnabled:" + locatorName, "Provided element's enablement status is NOT as expected " + expected);
        }

        return status;
    }

    public boolean isElementPresent(By by, String locatorName, boolean expected) throws Throwable {
        boolean status = false;
        QAFWebElement ele = new QAFExtendedWebElement(by);

        try {

            ele.waitForPresent();

            System.out.println("Element Present " + by);
            getReporter().SuccessReport("isElementPresent", this.msgIsElementFoundSuccess + locatorName);

            status = true;
        } catch (Exception e) {
            status = false;
            LOG.info(e.getMessage());
            if (expected == status) {
                getReporter().SuccessReport("isElementPresent", locatorName + "is ElementPresent");
            } else {

                getReporter().failureReport(locatorName + " is ElementPresent",
                        this.msgIsElementFoundFailure + locatorName, getDriver());
            }
        }

        return status;
    }

    public boolean type(By locator, String testdata, String locatorName) throws Throwable {
        boolean status = false;
        // driverreporterSelect();
        try {
            QAFWebElement ele = new QAFExtendedWebElement(locator);
            ele.waitForVisible();
            ele.click();
            ele.sendKeys(testdata);
            System.out.println("Click on " + locator);
            getReporter().SuccessReport("Click", this.msgClickSuccess + locatorName);

            status = true;

        } catch (Exception e) {
            getReporter().failureReport("type", this.msgTypeFailure + locatorName, getDriver());

        }

        if (getBundle().getString("environment").equalsIgnoreCase("ios")) {
            click(By.xpath("//*[@label=\"Done\"]"), "Done Button");
        } else if (getBundle().getString("environment").equalsIgnoreCase("android")) {
            getAppiumDriver().hideKeyboard();
        }

        return status;
    }

    public AppiumDriver getAppiumDriver() {
        return (AppiumDriver) getDriver().getUnderLayingDriver();
    }

    public RemoteWebDriver getwebDriver() {
        return (RemoteWebDriver) getDriver().getUnderLayingDriver();
    }

    public boolean isSelect(By locator, String testdata, String locatorName) throws Throwable {
        boolean status = false;
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), 60);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            Thread.sleep(2000);

            Select select = new Select(getDriver().findElement(locator));
            List<WebElement> vales = select.getOptions();

            for (int i = 0; i < vales.size(); i++) {

                if (vales.get(i).getText().equals(testdata)) {
                    select.selectByIndex(i);
                    break;
                }
            }

            System.out.println("Select" + testdata);
            getReporter().SuccessReport("Select", "Select option is displayed::" + locatorName);
            status = true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
            LOG.info(e.getMessage());
            getReporter().failureReport("Select", "Select option is not displayed::" + locatorName);
            throw e;
        }

        return status;
    }

    @SuppressWarnings("deprecation")
    public boolean calendarDate(By locator, String locatorName, int index) throws Throwable {
        boolean status = false;
        int values = 1900;
        int year, month, date;
        String syear, smonth, sdate;
        Date currentDate = new Date();
        long ltime = currentDate.getTime() + index * 24 * 60 * 60 * 1000;
        currentDate = new Date(ltime);

        // String value = currentDate.getDate()+ "\\" + currentDate.getMonth() +
        // "\\" + (values + currentDate.getYear()) ;
        year = currentDate.getYear() + 1900;
        month = currentDate.getMonth();
        date = currentDate.getDate();

        smonth = Integer.toString(month);
        syear = Integer.toString(year);
        sdate = Integer.toString(date);

        String givenDate = "02/01/2017";
        String date1[] = givenDate.split("/");
        /*
         * for (int x=0; x< date1.length; x++){ System.out.println(date1[x]); }
		 */
        try {

            WebDriverWait wait = new WebDriverWait(getDriver(), 60);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            getDriver().findElement(locator).click();
            // date picker window
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='ui-datepicker-div']/div")));

            // Month dropdown
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='ui-datepicker-month']")));
            Select select = new Select(getDriver().findElement(By.xpath("//*[@class='ui-datepicker-month']")));
            select.selectByValue(smonth);

            // Year dropdown
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='ui-datepicker-year']")));
            select = new Select(getDriver().findElement(By.xpath("//*[@class='ui-datepicker-year']")));
            select.selectByVisibleText(syear);

            // date selection
            wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//td[@data-handler='selectDay']/a[text()='" + sdate + "'][1]")));
            getDriver().findElement(By.xpath("//td[@data-handler='selectDay']/a[text()='" + sdate + "'][1]")).click();
            // getDriver().findElement(locator).sendKeys();

            System.out.println("Click on " + locator);
            getReporter().SuccessReport("Select Date", "Date has been selected at " + locatorName);

            status = true;

        } catch (Exception e) {

            status = false;
            getReporter().failureReport("Click  ", "Date has NOT been selected at " + locatorName);

            throw e;

        }
        return status;

    }

    public boolean imageAltTextValidate(By LocatorName, String eAltText) throws Throwable {
        boolean status = false;
        String aAltText = "Dummy";
        try {

            aAltText = getDriver().findElement(LocatorName).getAttribute("alt");
            if (aAltText.trim().equals(eAltText.trim())) {
                getReporter().SuccessReport("Image alt text ", " is matched with expected value:" + eAltText);
            } else {
                getReporter().failureReport("Image alt text ",
                        " is not matched with expected value:" + eAltText + ", actual alt text is " + aAltText);
            }

            System.out.println("Get Element Text is  " + aAltText);
            status = true;

        } catch (Exception e) {

            status = false;
            LOG.info(e.getMessage());
            throw e;

        }
        return status;

    }

    public boolean linkHyperlinkValidate(By LocatorName, String eAltText) throws Throwable {
        boolean status = false;
        String aAltText = "Dummy";
        try {

            aAltText = getDriver().findElement(LocatorName).getAttribute("href");
            if (aAltText.trim().equalsIgnoreCase(eAltText.trim()) || aAltText.trim().contains(eAltText.trim())) {
                getReporter().SuccessReport("Link href text ", " is matched with expected value:" + eAltText);
            } else {
                getReporter().failureReport("Link href text ",
                        " is not matched with expected value:" + eAltText + ", actual link ref is " + aAltText);
            }

            System.out.println("Get Element Text is  " + aAltText);
            status = true;

        } catch (Exception e) {

            status = false;
            LOG.info(e.getMessage());

        }
        return status;

    }

    public boolean jclick(By locator, String locatorName) throws Throwable {
        boolean status = false;
        try {
            QAFWebElement ele = new QAFExtendedWebElement(locator);
            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].click();", ele);
            System.out.println("Click on " + locator);
            getReporter().SuccessReport("Click", this.msgClickSuccess + locatorName);

            status = true;

        } catch (Exception e) {
            getReporter().failureReport(locatorName + " click ", this.msgIsElementFoundFailure + locatorName,
                    getDriver());
        }
        return status;
    }

}