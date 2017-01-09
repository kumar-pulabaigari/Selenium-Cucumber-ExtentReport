package com.hom.automationDigital.extentReport;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.Scenario;

public class ExtentTestManager {

	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
    static ExtentReports extent = ExtentManager.getReporter();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static boolean write(Scenario scenario, LogStatus log, String message){
    	extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(log, message);
    	scenario.write(message);
    	return true;
    }
    
    public static boolean addScreenShots(String sFileName){
    	ExtentTest test= extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    	File sFileName1 = new File(sFileName);
    	test.log(LogStatus.INFO, "Screencast below: " + test.addScreencast(sFileName1.getAbsolutePath()));
    	return true;
    }
    
    public static boolean write(Scenario scenario, LogStatus log, String stepName, String stepDetails){
    	extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(log, stepName, stepDetails);
    	scenario.write(stepName + ": " + stepDetails);
    	return true;
    }
    
    public static boolean write(Scenario scenario, LogStatus log, String stepName, String stepDetails, Throwable e){
    	extentTestMap.get((int) (long) (Thread.currentThread().getId())).log(log, stepName, stepDetails);
    	scenario.write(stepName + ": " + stepDetails);
    	return true;
    }
    
    public static synchronized void endTest() {
    	extentTestMap.get((int) (long) (Thread.currentThread().getId())).setEndedTime(new Date());
    	extent.endTest(extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.startTest(testName, desc);
        test.setStartedTime(new Date());
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

        return test;
    }
    
    public static synchronized boolean assignCategory (Collection<String> category){
    	category.forEach(new Consumer<String>() {
			public void accept(String category) {
				extentTestMap.get((int) (long) (Thread.currentThread().getId())).assignCategory(category);
			}
		});
    	return true;
    }
    
    public static synchronized boolean assignAuthor(Scenario scenario, String author[]){
    	for (int i=0; i<author.length; i++){
    		extentTestMap.get((int) (long) (Thread.currentThread().getId())).assignAuthor(author[i]);
    	}
    	return true;
    }
    
}
