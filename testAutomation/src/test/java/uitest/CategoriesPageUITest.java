package uitest;

/**
 * Created by vinaykumarp on 3/6/2017.
 */

import automation.drivers.GalenTestBase;
import org.testng.annotations.Test;

import java.io.IOException;


public class CategoriesPageUITest extends GalenTestBase {

    @Test(dataProvider = "devices")
    public void CategoriesPageUIValidator(String browser, TestDevice device ) throws IOException, InterruptedException {
        load("/i-bank/my-goals/Education.page");
        Thread.sleep(3000);
        checkLayout(System.getProperty("user.dir")+ "\\src\\test\\java\\specs\\contentCategoriesPageUI.spec", device.getTags());
    }

}
