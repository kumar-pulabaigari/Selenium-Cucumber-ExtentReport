package stepDefinitions;


import automation.drivers.CommonActions;
import automation.report.ReporterConstants;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

public class ImageComparisionSampleTest extends CommonActions {

    private String instance="1";
    @DataProvider(name="urls")
    public Object[][] urlData() {
        Object[][] arrayObject = getExcelData(System.getProperty("user.dir")+"\\TestData\\Landing page_prod uat & sit URLs.xls","URL");
        return arrayObject;
    }

    public String[][] getExcelData(String fileName, String sheetName) {
        String[][] arrayExcelData = null;
        try {
            FileInputStream fs = new FileInputStream(fileName);
            Workbook wb = Workbook.getWorkbook(fs);
            Sheet sh = wb.getSheet(sheetName);

            int totalNoOfCols = sh.getColumns();
            int totalNoOfRows = sh.getRows();

            arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];

            for (int i= 1 ; i < totalNoOfRows; i++) {

                for (int j=0; j < totalNoOfCols; j++) {
                    arrayExcelData[i-1][j] = sh.getCell(j, i).getContents();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return arrayExcelData;
    }

    @Test(dataProvider="urls")
    public void feature(String SNo,	String caseID, String caseName,String Page,String Country, String BusinessUnit, String Segment,	String PRODURLs, String	UATURLs, String	SITURLs) throws Throwable {
        String testCaseName=Page + "_" +Country +  "_" +BusinessUnit + "_" +Segment ;
        String fileName1= "\\tmp\\instance1\\" + caseName +  "\\" + getBundle().getString("browser") +"\\HTTPscreenshot1.png";
        String fileName2= "\\tmp\\instance1\\" + caseName +  "\\" + getBundle().getString("browser") +"\\HTTPscreenshot2.png";
        String fileName3= "\\tmp\\instance1\\" + caseName +  "\\" + getBundle().getString("browser") +"\\HTTPscreenshot3.png";
        String fileName12= "\\tmp\\instance1\\" + caseName +  "\\" + getBundle().getString("browser") +"\\HTTPscreenshot12.png";
        String fileName23= "\\tmp\\instance1\\" + caseName +  "\\" + getBundle().getString("browser") +"\\HTTPscreenshot23.png";
        String fileName31= "\\tmp\\instance1\\" + caseName +  "\\" + getBundle().getString("browser") +"\\HTTPscreenshot31.png";
        File scrFile;
        try {
            getReporter().initTestCase(this.getClass().getName().substring(0, this.getClass().getName().lastIndexOf(".")), caseName, "Screenshot compare_" +testCaseName, false);
        } catch(Exception  ex){
            System.out.println("Report Initiation exception is " + ex.getMessage());
        }

       /* getDriver().get(PRODURLs);
        Thread.sleep(3000);
        getDriver().manage().window().maximize();
        Thread.sleep(3000);
        scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+ fileName1));
        if(new File(System.getProperty("user.dir")+ fileName1).exists()){
            getReporter().SuccessReport("Screen shot of Prod URL:" + PRODURLs, " has been captured, and Image path is " + new File(System.getProperty("user.dir")+ fileName1).getAbsolutePath());
        } else {
            getReporter().failureReport("Screen shot of Prod URL:" + PRODURLs, " has not been captured");
        }

        getDriver().get(UATURLs);
        Thread.sleep(3000);
        getDriver().manage().window().maximize();
        Thread.sleep(3000);
        scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+ fileName2));
        if(new File(System.getProperty("user.dir")+ fileName2).exists()){
            getReporter().SuccessReport("Screen shot of UAT URL:" + UATURLs, " has been captured, and Image path is " + new File(System.getProperty("user.dir")+ fileName2).getAbsolutePath());
        } else {
            getReporter().failureReport("Screen shot of UAT URL:" + UATURLs, " has not been captured");
        }*/
        getDriver().manage().deleteAllCookies();
        Thread.sleep(1000);
        getDriver().get("http:\\" + SITURLs);
        Thread.sleep(3000);
        getDriver().manage().window().maximize();
        Thread.sleep(3000);
        scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+ fileName3));
        if(new File(System.getProperty("user.dir")+ fileName3).exists()){
            getReporter().SuccessReport("Screen shot of SIT URL:" + SITURLs, " has been captured, and Image path is " + new File(System.getProperty("user.dir")+ fileName3).getAbsolutePath());
        } else {
            getReporter().failureReport("Screen shot of SIT URL:" + SITURLs, " has not been captured");
        }

         if(getDriver().getCurrentUrl().contains("https://")){
             getReporter().failureReport("SIT URL:" + SITURLs, " has been navigated to https, and current URL is " + getDriver().getCurrentUrl());
         }
        /*if (similarity( ImageIO.read(new File(System.getProperty("user.dir")+ fileName1)), ImageIO.read(new File(System.getProperty("user.dir")+fileName2)), fileName12) < 05){
            getReporter().SuccessReport("Production and UAT screen shot" , " is matched , and path of the difference image is " + new File(System.getProperty("user.dir")+ fileName12).getAbsolutePath());
            System.out.println("Both images matched");
        } else {
            getReporter().failureReport("Production and UAT screen shot" , " is NOT matched , and path of the difference image is " + new File(System.getProperty("user.dir")+ fileName12).getAbsolutePath());
            System.out.println("Both images aren't matched");
        }

        if (similarity( ImageIO.read(new File(System.getProperty("user.dir")+ fileName2)), ImageIO.read(new File(System.getProperty("user.dir")+fileName3)), fileName23) < 05){
            getReporter().SuccessReport("UAT and SIT screen shot" , " is matched , and path of the difference image is " + new File(System.getProperty("user.dir")+ fileName23).getAbsolutePath());
            System.out.println("Both images matched");
        } else {
            getReporter().failureReport("UAT and SIT screen shot" , " is NOT matched , and path of the difference image is " + new File(System.getProperty("user.dir")+ fileName23).getAbsolutePath());
            System.out.println("Both images aren't matched");
        }

        if (similarity( ImageIO.read(new File(System.getProperty("user.dir")+ fileName3)), ImageIO.read(new File(System.getProperty("user.dir")+fileName1)), fileName31) < 05){
            getReporter().SuccessReport("SIT and Production screen shot" , " is matched , and path of the difference image is " + new File(System.getProperty("user.dir")+ fileName31).getAbsolutePath());
            System.out.println("Both images matched");
        } else {
            getReporter().failureReport("SIT and Production screen shot" , " is NOT matched , and path of the difference image is " + new File(System.getProperty("user.dir")+ fileName31).getAbsolutePath());
            System.out.println("Both images aren't matched");
        }*/
    }


    public static double similarity( BufferedImage image1, BufferedImage image2, String filename ) throws IOException{
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
        ImageIO.write(image2, "png", new File(System.getProperty("user.dir")+ filename));

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


    @AfterMethod
    public void afterTestNgTestCase() throws Exception {
        try {
            getReporter().calculateTestCaseExecutionTime();
            getReporter().closeDetailedReport();
            getReporter().updateTestCaseStatus();
            //getReporter().calculateSuiteExecutionTime();
            getReporter().createHtmlSummaryReport(ReporterConstants.APP_BASE_URL, false);
            getReporter().closeSummaryReport();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}