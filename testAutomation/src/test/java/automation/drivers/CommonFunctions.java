package automation.drivers;

import com.perfectomobile.selenium.api.IMobileAppiumDriver;
import com.perfectomobile.selenium.api.IMobileDevice;
import com.perfectomobile.selenium.api.IMobileDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CommonFunctions {

	public WebDriver driver = null;
	public RemoteWebDriver remoteDriver = null;
	protected String BrowserName, BrowserVersion, Environment, OperatingSystem, PlatformName;

	// for APPIUM
	public IMobileDevice iMobileDevice = null;
	public IMobileDriver iMobileDriver = null;
	public IMobileAppiumDriver iMobileAppiumDriver = null;

	// Property read variables


	public boolean setDriver(String BrowserName, String BrowserVersion, String Environment, String OperatingSystem,
			String PlatformName) {
		this.BrowserName = BrowserName;
		this.BrowserVersion = BrowserVersion;
		this.Environment = Environment;
		this.OperatingSystem = OperatingSystem;
		this.PlatformName = PlatformName;

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

	public boolean setDriverLocal(String BrowserName) {
		if (BrowserName.trim().equalsIgnoreCase("ie") || BrowserName.trim().equalsIgnoreCase("internet explorer")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/lib/drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			try{
				Thread.sleep(3000);
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("IE Browser has been initiated in local");
		} else if (BrowserName.trim().equalsIgnoreCase("firefox") || BrowserName.trim().equalsIgnoreCase("ff")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "/lib/drivers/geckodriver.exe");
			driver = new FirefoxDriver();
			System.out.println("Firefox Browser has been initiated in local");
		} else {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/lib/drivers/chromedriver.exe");
			driver = new ChromeDriver();
			System.out.println("Chrome Browser has been initiated in local");
		}
		return true;
	}

	
	public boolean setDriverGrid(String BrowserName, String BrowserVersion, String OperatingSystem) {
		String node = "http://localhost:4444/wd/hub";
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
			System.out.println("Remote web driver has been initiated.");
		} catch (Exception e) {
			System.out.println("Remote web driver has not been initiated.");
			System.out.println("Exception details" + e.toString());
		}
		return true;
	}

	public boolean setDriverPerfecto(String BrowserName, String OperatingSystem, String MobileDeviceID) {
		String node = "myHost.perfectomobile.com";
		String userName = "myUser";
		String password = "Password";
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

			System.out.println("Perfecto -Remote web driver has been initiated.");
		} catch (Exception e) {
			System.out.println("Perfecto -Remote web driver has not been initiated.");
			System.out.println("Exception details" + e.toString());
		}
		return true;
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
