package com.hom.automationDigital.commonFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.hom.automationDigital.extentReport.ExtentTestManager;
import com.perfectomobile.selenium.api.IMobileAppiumDriver;
import com.perfectomobile.selenium.api.IMobileDevice;
import com.perfectomobile.selenium.api.IMobileDriver;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.Scenario;

public class CommonFunctions {

	public WebDriver driver = null;
	public RemoteWebDriver remoteDriver = null;
	protected CommonBehaviour common = new CommonBehaviour();
	protected String BrowserName, BrowserVersion, Environment, OperatingSystem, PlatformName;

	// for APPIUM
	public IMobileDevice iMobileDevice = null;
	public IMobileDriver iMobileDriver = null;
	public IMobileAppiumDriver iMobileAppiumDriver = null;

	// Property read variables
	public Properties prop = new Properties();
	public InputStream input = null;

	protected int screenShotsCount = 0;
	protected Scenario scenario;
	public String fileScreenShoot;

	//Testdata file Name
	protected ExcelReader masterFile, projectFile;
	
	
	protected boolean screenShotPath() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	Date today = Calendar.getInstance().getTime();
    	String reportDate = df.format(today);
		fileScreenShoot = System.getProperty("user.dir") + "\\Output\\" + Environment + "\\" + BrowserName + "\\"
				+ scenario.getName() + "\\" + reportDate + "\\";
		return true;
	}

	public boolean setDriver(String BrowserName, String BrowserVersion, String Environment, String OperatingSystem,
			String PlatformName, Scenario scenario) {
		this.BrowserName = BrowserName;
		this.BrowserVersion = BrowserVersion;
		this.Environment = Environment;
		this.OperatingSystem = OperatingSystem;
		this.PlatformName = PlatformName;
		screenShotPath();
		if (Environment.trim().equalsIgnoreCase("Local")) {

			if (setDriverLocal(BrowserName)) {
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			}

		} else if (Environment.trim().equalsIgnoreCase("Grid")) {
			if (setDriverGrid(BrowserName, BrowserVersion, OperatingSystem)) {
				remoteDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				remoteDriver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
				remoteDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			}
		} else if (Environment.trim().equalsIgnoreCase("Perfecto")) {
			if (setDriverPerfecto(BrowserName, BrowserVersion, OperatingSystem)) {
				remoteDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				remoteDriver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
				remoteDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			}
		}

		return true;
	}

	private void loadPopertyFile() {
		String sFileName = System.getProperty("user.dir") + "/resources/environmentProperties.property";
		try {
			input = new FileInputStream(sFileName);
			// load a properties file
			prop.load(input);
		} catch (FileNotFoundException e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					"Provided property file path: " + sFileName + " is Invalied");
			ExtentTestManager.getTest().log(LogStatus.INFO, "File Not Found Exception details" + e.toString());
		} catch (IOException e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					"Provided peroperty file is : " + sFileName + " is not in a read mode");
			ExtentTestManager.getTest().log(LogStatus.INFO, "IO Exception details" + e.toString());
		}
	}

	public boolean setDriverLocal(String BrowserName) {
		loadPopertyFile();
		if (BrowserName.trim().equalsIgnoreCase("ie") || BrowserName.trim().equalsIgnoreCase("internet explorer")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + prop.getProperty("IEdriver"));
			driver = new InternetExplorerDriver();
			try{
				Thread.sleep(3000);
			}catch(Exception e){
				e.printStackTrace();
			}
			ExtentTestManager.write(scenario, LogStatus.INFO, "IE Browser has been initiated in local");
		} else if (BrowserName.trim().equalsIgnoreCase("Edge") || BrowserName.trim().equalsIgnoreCase("IE Edge")) {
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + prop.getProperty("Edgedriver"));
			driver = new EdgeDriver();
			try{
				Thread.sleep(3000);
			}catch(Exception e){
				e.printStackTrace();
			}
			ExtentTestManager.write(scenario, LogStatus.INFO, "Edge Browser has been initiated in local");
		} else if (BrowserName.trim().equalsIgnoreCase("firefox") || BrowserName.trim().equalsIgnoreCase("ff")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + prop.getProperty("geckoDriver"));
			driver = new FirefoxDriver();
			ExtentTestManager.write(scenario, LogStatus.INFO, "Firefox Browser has been initiated in local");
		} else {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + prop.getProperty("chromeDriver"));
			driver = new ChromeDriver();
			ExtentTestManager.write(scenario, LogStatus.INFO, "Chrome Browser has been initiated in local");
		}
		return true;
	}

	
	public boolean loadExcelFile(){
		loadPopertyFile();
		String[][] testdataString;
		String testDataFileName = prop.getProperty("testDataFile");
		String testDataSheet = prop.getProperty("testDataSheet");
		String environment = prop.getProperty("environment");
		String sTestDataFileName, sTestDataSheetName; 
		masterFile = new ExcelReader(System.getProperty("user.dir") +  testDataFileName , testDataSheet);
		testdataString= masterFile.getIntoDataStringArray();
		for(int i=0; i<testdataString.length; i++){
				if(testdataString[i][0].trim().equalsIgnoreCase(scenario.getName().trim())){
					if(testdataString[i][1].trim().equalsIgnoreCase(environment.trim())){
						if(testdataString[i][2].trim().equalsIgnoreCase("True")){
							sTestDataFileName = testdataString[i][3];
							sTestDataSheetName = testdataString[i][4];
							projectFile = new ExcelReader(System.getProperty("user.dir") +  sTestDataFileName , sTestDataSheetName);
							projectFile.getIntoMap(scenario);
							ExtentTestManager.write(scenario, LogStatus.PASS, "Project specific test data file :" + sTestDataFileName, " , and sheet name is " + sTestDataSheetName);
							break;
						}
					}
				}
		}
		masterFile =null;
		return true;
	}
	
	public boolean setDriverGrid(String BrowserName, String BrowserVersion, String OperatingSystem) {
		loadPopertyFile();
		String node = prop.getProperty("SeleniumGridURL");
		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (BrowserName.trim().equalsIgnoreCase("ie") || BrowserName.trim().equalsIgnoreCase("internet explorer")) {
			capabilities.setBrowserName("internet explorer");
		} else if (BrowserName.trim().equalsIgnoreCase("firefox") || BrowserName.trim().equalsIgnoreCase("ff")) {
			capabilities.setBrowserName("firefox");
		} else {
			capabilities.setBrowserName("chrome");
		}

		if (BrowserVersion.trim().isEmpty()) {

		} else {
			capabilities.setVersion(BrowserVersion);
		}

		if (OperatingSystem.trim().isEmpty()) {

		} else {
			if (OperatingSystem.trim().equalsIgnoreCase("Windows")) {
				capabilities.setPlatform(Platform.WINDOWS);
			} else if (OperatingSystem.trim().equalsIgnoreCase("XP")) {
				capabilities.setPlatform(Platform.XP);
			} else if (OperatingSystem.trim().equalsIgnoreCase("MAC")) {
				capabilities.setPlatform(Platform.MAC);
			} else if (OperatingSystem.trim().equalsIgnoreCase("Linux")) {
				capabilities.setPlatform(Platform.LINUX);
			} else if (OperatingSystem.trim().equalsIgnoreCase("Unix")) {
				capabilities.setPlatform(Platform.UNIX);
			}
		}

		try {
			remoteDriver = new RemoteWebDriver(new URL(node), capabilities);
			ExtentTestManager.write(scenario, LogStatus.INFO, "Remote web driver has been initiated.");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Remote web driver has not been initiated.");
			ExtentTestManager.getTest().log(LogStatus.INFO, "Exception details" + e.toString());
		}
		return true;
	}

	public boolean setDriverPerfecto(String BrowserName, String OperatingSystem, String MobileDeviceID) {
		loadPopertyFile();
		String node = prop.getProperty("PerfectoURL");
		String userName = prop.getProperty("PerfectoUserName");
		String password = prop.getProperty("PerfectoPassword");
		DesiredCapabilities capabilities = null;

		if (!MobileDeviceID.trim().isEmpty()) {
			if (BrowserName.trim().equalsIgnoreCase("Safari")) {
				capabilities = new DesiredCapabilities("Safari", "", Platform.ANY);
			} else if (BrowserName.trim().equalsIgnoreCase("Chrome")) {
				capabilities = new DesiredCapabilities("Chrome", "", Platform.ANY);
			} else {
				capabilities = new DesiredCapabilities("MobileOS", "", Platform.ANY);
			}

			capabilities.setCapability("deviceName", MobileDeviceID);
		} else if (!OperatingSystem.trim().isEmpty()) {
			if (OperatingSystem.trim().equalsIgnoreCase("Android")) {
				if (BrowserName.trim().equalsIgnoreCase("Chrome")) {
					capabilities = new DesiredCapabilities("Chrome", "", Platform.ANY);
				} else {
					capabilities = new DesiredCapabilities("MobileOS", "", Platform.ANY);
				}
				capabilities.setCapability("platformName", "Android");
			} else if (OperatingSystem.trim().equalsIgnoreCase("iOS")) {
				if (BrowserName.trim().equalsIgnoreCase("Safari")) {
					capabilities = new DesiredCapabilities("Safari", "", Platform.ANY);
				} else {
					capabilities = new DesiredCapabilities("MobileOS", "", Platform.ANY);
				}
				capabilities.setCapability("platformName", "iOS");
			} else {
				if (BrowserName.trim().equalsIgnoreCase("Safari")) {
					capabilities = new DesiredCapabilities("Safari", "", Platform.ANY);
				} else if (BrowserName.trim().equalsIgnoreCase("Chrome")) {
					capabilities = new DesiredCapabilities("Chrome", "", Platform.ANY);
				} else {
					capabilities = new DesiredCapabilities("MobileOS", "", Platform.ANY);
				}
			}
		} else {
			if (BrowserName.trim().equalsIgnoreCase("Safari")) {
				capabilities = new DesiredCapabilities("Safari", "", Platform.ANY);
			} else if (BrowserName.trim().equalsIgnoreCase("Chrome")) {
				capabilities = new DesiredCapabilities("Chrome", "", Platform.ANY);
			} else {
				capabilities = new DesiredCapabilities("MobileOS", "", Platform.ANY);
			}
		}

		capabilities.setCapability("user", userName);
		capabilities.setCapability("password", password);

		try {
			remoteDriver = new RemoteWebDriver(new URL("https://" + node + "/nexperience/perfectomobile/wd/hub"),
					capabilities);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			ExtentTestManager.write(scenario, LogStatus.INFO, "Perfecto -Remote web driver has been initiated.");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Perfecto -Remote web driver has not been initiated.");
			ExtentTestManager.getTest().log(LogStatus.INFO, "Exception details" + e.toString());
		}
		return true;
	}
	
	public boolean screenShotDriver(){
		String filePath =fileScreenShoot + screenShotsCount++ + ".png" ;
		if (Environment != null) {
			if (Environment.trim().equalsIgnoreCase("Local")) {

				if (driver != null) {
					common.getscreenshot(driver, filePath);
				}

			} else if (Environment.trim().equalsIgnoreCase("Grid")) {
				if (remoteDriver != null) {
					common.getscreenshot(remoteDriver, filePath);
				}
			} else if (Environment.trim().equalsIgnoreCase("Perfecto")) {
				if (remoteDriver != null) {
					common.getscreenshot(remoteDriver, filePath);
				}
			}
		}
		return true;
	}

	public boolean addScreenShotWordX() {
		@SuppressWarnings("resource")
		XWPFDocument doc = new XWPFDocument();
		XWPFParagraph p = doc.createParagraph();
		XWPFRun xwpfRun = p.createRun();

		FileOutputStream out = null;
		for(int i=0; i<screenShotsCount; i++){
			String fileName=fileScreenShoot + i + ".png";
			File file = new File(fileName);
			int format = XWPFDocument.PICTURE_TYPE_PNG;
			String imgFile = file.getPath();
			xwpfRun.setText("");
			xwpfRun.addBreak();
			XWPFParagraph paragraphFive = doc.createParagraph();
		    paragraphFive.setAlignment(ParagraphAlignment.CENTER);
		    XWPFRun paragraphFiveRunOne = paragraphFive.createRun();
			try {
			 	 out = new FileOutputStream(fileScreenShoot + "\\screenShots1.docx");
				 paragraphFiveRunOne.addPicture(new FileInputStream(imgFile), format, null,Units.toEMU(300), Units.toEMU(300));
				 paragraphFiveRunOne.addCarriageReturn();
				 doc.write(out);
				 out.close();
				 ExtentTestManager.getTest().log(LogStatus.INFO, "Please refer the screen shots in " + fileScreenShoot + "\\screenShots1.docx");
			} catch (InvalidFormatException e) {
				ExtentTestManager.getTest().log(LogStatus.INFO, "FileNotFoundException details" + e.toString());
			} catch (FileNotFoundException e) {
				ExtentTestManager.getTest().log(LogStatus.INFO, "FileNotFoundException details" + e.toString());
			} catch (IOException e) {
				ExtentTestManager.getTest().log(LogStatus.INFO, "IOException details" + e.toString());
			}
		}
		return true;
	}

	public void navigateURL(String url) {
		if (driver != null) {
			if (common.navigateURL(driver, url)) {
				ExtentTestManager.write(scenario, LogStatus.PASS, "User is navigated to given URL:" + url);
			} else {
				ExtentTestManager.write(scenario, LogStatus.INFO, "User is not navigated to given URL:" + url);
			}
		} else if (remoteDriver != null) {
			if (common.navigateURL(remoteDriver, url)) {
				ExtentTestManager.write(scenario, LogStatus.PASS, "User is navigated to given URL:" + url);
			} else {
				ExtentTestManager.write(scenario, LogStatus.INFO, "User is not navigated to given URL:" + url);
			}
		} else {
			ExtentTestManager.write(scenario, LogStatus.FAIL, "Driver is not initiated....");
		}
	}

	public String getTitle() {
		String title = "";
		if (driver != null) {
			title = common.getTitle(driver);
			if (!title.isEmpty()) {
				ExtentTestManager.write(scenario, LogStatus.PASS, "Browse title is:" + title);
			} else {
				ExtentTestManager.write(scenario, LogStatus.INFO, "Browser title is " + title);
			}
		} else if (remoteDriver != null) {
			title = common.getTitle(remoteDriver);
			if (!title.isEmpty()) {
				ExtentTestManager.write(scenario, LogStatus.PASS, "Browser title is :" + title);
			} else {
				ExtentTestManager.write(scenario, LogStatus.INFO, "Browser title is:" + title);
			}
		} else {
			ExtentTestManager.write(scenario, LogStatus.FAIL, "Driver is not initiated....");
		}
		return title;
	}

	public boolean setCloseDriver() {

		if (Environment != null) {
			if (Environment.trim().equalsIgnoreCase("Local")) {

				if (driver != null) {
					driver.close();
					driver.quit();
				}

			} else if (Environment.trim().equalsIgnoreCase("Grid")) {
				if (remoteDriver != null) {
					remoteDriver.close();
					remoteDriver.quit();
				}
			} else if (Environment.trim().equalsIgnoreCase("Perfecto")) {
				if (remoteDriver != null) {
					remoteDriver.close();
					remoteDriver.quit();
				}
			}
		}
		return true;
	}
}
