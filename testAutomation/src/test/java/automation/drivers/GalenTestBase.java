package automation.drivers;

/**
 * Created by vinaykumarp on 3/6/2017.
 */
import automation.dataDriver.*;
import com.galenframework.testng.GalenTestNgTestBase;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;

public abstract class GalenTestBase extends GalenTestNgTestBase {

    private static String ENV_URL = "https://dbsweb-uat-sg-www.uat.dbs.com";

    @Override
    public WebDriver createDriver(Object[] args) {
        Properties prop1 = new Properties();
        InputStream input1 = null;
        try {
            input1 = new FileInputStream(".//resources//applicationURL.properties");
            prop1.load(input1);
            if (prop1.getProperty("env.DBSbURL").trim().toString().trim().isEmpty()) {
                ENV_URL="";
            } else {
                ENV_URL= prop1.getProperty("env.DBSbURL").trim().toString();
            }
        } catch (FileNotFoundException e) {
            ENV_URL="";
        } // load a properties file
        catch (IOException e) {
            ENV_URL="";
        }
        RemoteWebDriver remoteDriver =null;
        String node = "http://localhost:4444/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (args[0].toString().trim().equalsIgnoreCase("ie") || args[0].toString().trim().equalsIgnoreCase("internet explorer")) {
            capabilities.setBrowserName("internet explorer");
        } else if (args[0].toString().trim().equalsIgnoreCase("firefox") || args[0].toString().trim().equalsIgnoreCase("ff")) {
            capabilities.setBrowserName("firefox");
        } else {
            capabilities.setBrowserName("chrome");
        }

        try {
            remoteDriver = new RemoteWebDriver(new URL(node), capabilities);
            System.out.println("Remote web driver has been initiated.");
            remoteDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            remoteDriver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
            remoteDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            if (args.length > 1) {
                if (args[1] != null && args[1] instanceof TestDevice) {
                    //TestDevice device = (TestDevice)args[1];
                    if (((TestDevice) args[1]).getScreenSize() != null) {
                        remoteDriver.manage().window().setSize(((TestDevice) args[1]).getScreenSize());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Remote web driver has not been initiated.");
            System.out.println("Exception details" + e.toString());
        }
        return new Augmenter().augment(remoteDriver);
    }

    public void load(String uri) {
        getDriver().get(ENV_URL + uri);
    }

    //@Parameters("browser")
    @DataProvider(name = "devices")
    public Object [][] devices () {
        return new Object[][] {
                {"Chrome", new TestDevice("mobile", new Dimension(450, 800), asList("mobile"))},
                {"Chrome", new TestDevice("tablet", new Dimension(750, 800), asList("tablet"))},
                {"Chrome", new TestDevice("desktop", new Dimension(1024, 800), asList("desktop"))},
                {"Firefox", new TestDevice("mobile", new Dimension(450, 800), asList("mobile"))},
                {"Firefox", new TestDevice("tablet", new Dimension(750, 800), asList("tablet"))},
                {"Firefox", new TestDevice("desktop", new Dimension(1024, 800), asList("desktop"))},
//                {"IE", new TestDevice("mobile", new Dimension(450, 800), asList("mobile"))},
//                {"IE", new TestDevice("tablet", new Dimension(750, 800), asList("tablet"))},
//                {"IE", new TestDevice("desktop", new Dimension(1024, 800), asList("desktop"))}
        };
    }

    public static class TestDevice {
        private final String name;
        private final Dimension screenSize;
        private final List<String> tags;

        public TestDevice(String name, Dimension screenSize, List<String> tags) {
            this.name = name;
            this.screenSize = screenSize;
            this.tags = tags;
        }

        public String getName() {
            return name;
        }

        public Dimension getScreenSize() {
            return screenSize;
        }

        public List<String> getTags() {
            return tags;
        }

        @Override
        public String toString() {
            return String.format("%s %dx%d", name, screenSize.width, screenSize.height);
        }
    }
}