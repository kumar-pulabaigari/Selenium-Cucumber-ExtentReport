package stepDefinitions;


import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;

public class ImageComparisionSampleTest extends WebDriverTestCase {


    @Test
    public void feature() throws IOException, InterruptedException {
        getDriver().get("https://dbsweb-uat-sg-www.uat.dbs.com/i-bank/default.page");
        Thread.sleep(1000);
        File scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);

        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+ "\\tmp\\screenshot1.png"));
        Reporter.log( "Screen shot-2 has been saved, and saved at:" + System.getProperty("user.dir")+ "\\tmp\\screenshot1.png" , true);
        Thread.sleep(1000);
        getDriver().get("http://dbsweb-s01.dbs.com.sg/i-bank/default.page");
        Thread.sleep(1000);
        scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+ "\\tmp\\screenshot2.png"));
        Reporter.log( "Screen shot-2 has been saved, and saved at:" + System.getProperty("user.dir")+ "\\tmp\\screenshot2.png" , true);
        if (similarity( ImageIO.read(new File(System.getProperty("user.dir")+ "\\tmp\\screenshot1.png")), ImageIO.read(new File(System.getProperty("user.dir")+ "\\tmp\\screenshot2.png"))) > 10){
            Reporter.log( "Both images matched", true );
            System.out.println("Both images matched");
        } else {
            Reporter.log( "Both images aren't matched", false );
            System.out.println("Both images aren't matched");
        }
        ;
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {

    }

    public static double similarity( BufferedImage image1, BufferedImage image2 ) throws IOException{
        int total_no_ofPixels = 0;
        int image1_PixelColor, red, blue, green;
        int image2_PixelColor, red2, blue2, green2;
        float differenceRed, differenceGreen, differenceBlue, differenceForThisPixel;
        double nonSimilarPixels = 0l, non_Similarity = 0l;

        long startTime = System.nanoTime();
        for (int row = 0; row < image1.getWidth(); row++) {
            for (int column = 0; column < image1.getHeight(); column++) {
                image1_PixelColor   =  image1.getRGB(row, column);
                red                 = (image1_PixelColor & 0x00ff0000) >> 16;
                green               = (image1_PixelColor & 0x0000ff00) >> 8;
                blue                =  image1_PixelColor & 0x000000ff;

                image2_PixelColor   =  image2.getRGB(row, column);
                red2                = (image2_PixelColor & 0x00ff0000) >> 16;
                green2              = (image2_PixelColor & 0x0000ff00) >> 8;
                blue2               =  image2_PixelColor & 0x000000ff;

                if (red != red2 || green != green2 || blue != blue2) {
                    differenceRed   =  (red - red2 )/ 255;
                    differenceGreen = ( green - green2 ) / 255;
                    differenceBlue  = ( blue - blue2 ) / 255;
                    differenceForThisPixel = ( differenceRed + differenceGreen + differenceBlue ) / 3;
                    nonSimilarPixels += differenceForThisPixel;
                }
                total_no_ofPixels++;

                if ( image1_PixelColor != image2_PixelColor ) {
                    image2.setRGB(row, column, Color.green.getGreen());
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println(String.format( "%-2d: %s", 0, toString( endTime - startTime )));

        System.out.println(" Writing the difference of first_Image to Second_Image ");
        ImageIO.write(image2, "png", new File(System.getProperty("user.dir")+ "\\tmp\\difference.png"));

        non_Similarity = (nonSimilarPixels / total_no_ofPixels);
        System.out.println( "Total No of pixels : " + total_no_ofPixels +"\t Non Similarity is : " + non_Similarity +"%");

        return non_Similarity;
    }
    private static String toString(long nanoSecs) {
        int minutes    = (int) ( nanoSecs / 60000000000.0 );
        int seconds    = (int) ( nanoSecs / 1000000000.0 )  - ( minutes * 60 );
        int millisecs  = (int) ( (( nanoSecs / 1000000000.0 ) - ( seconds + minutes * 60 )) * 1000 );

        if      ( minutes == 0 && seconds == 0   )    return millisecs + "ms";
        else if ( minutes == 0 && millisecs == 0 )    return seconds + "s";
        else if ( seconds == 0 && millisecs == 0 )    return minutes + "min";
        else if ( minutes == 0                   )    return seconds + "s " + millisecs + "ms";
        else if ( seconds == 0                   )    return minutes + "min " + millisecs + "ms";
        else if ( millisecs == 0                 )    return minutes + "min " + seconds + "s";

        return minutes + "min " + seconds + "s " + millisecs + "ms";
    }
}