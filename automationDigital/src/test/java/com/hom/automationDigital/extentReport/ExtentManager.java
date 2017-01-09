package com.hom.automationDigital.extentReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	static ExtentReports extent;
    final static String filePath = "ExtentOutput.html";
    
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports("./Output/ExtentReport/"+ fileName() + "/" + filePath, true);
           // extent.loadConfig(System.getProperty("user.dir") +".\\resources\\extent-config.xml");        
        }
        return extent;
    }
   public static String  fileName() {
    	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	Date today = Calendar.getInstance().getTime();
    	String reportDate = df.format(today);
    	return "Run_" + reportDate;
	}
}
