package test;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestNGListener extends TestListenerAdapter {


    @Override
    public void onTestStart(ITestResult result) {
        //CommonActions.getReporter().initTestCase(this.getClass().getName().substring(0,this.getClass().getName().lastIndexOf(".")), result.getName(), null, true);
        System.out.println("started Test:::::::" + result.getName());

    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        //aftercucumber();
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        //aftercucumber();

    }


    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestFailure(tr);
        //aftercucumber();

    }
    /*public void aftercucumber() {
        try {
			CommonActions.getReporter().calculateTestCaseExecutionTime();
			CommonActions.getReporter().closeDetailedReport();
			CommonActions.getReporter().updateTestCaseStatus();
			CommonActions.getReporter().calculateSuiteExecutionTime();	
			CommonActions.getReporter().createHtmlSummaryReport(ReporterConstants.APP_BASE_URL,true);
			CommonActions.getReporter().closeSummaryReport();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}*/

}
