package automation.report;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestResult {
    /* copied from TestEngine*/
    public static Map<BrowserContext, Integer> stepNum = new LinkedHashMap<BrowserContext, Integer>();
    public static Map<BrowserContext, Integer> PassNum = new LinkedHashMap<BrowserContext, Integer>();
    public static Map<BrowserContext, Integer> FailNum = new LinkedHashMap<BrowserContext, Integer>();
    public static Map<BrowserContext, Integer> passCounter = new LinkedHashMap<BrowserContext, Integer>();
    public static Map<BrowserContext, Integer> failCounter = new LinkedHashMap<BrowserContext, Integer>();
    public static Map<BrowserContext, String> testName = new LinkedHashMap<BrowserContext, String>();
    public static Map<BrowserContext, String> testCaseExecutionTime = new LinkedHashMap<BrowserContext, String>();
    /*testDescription contains BrowserContext as key , Map<testpackage:testname,testDescription> as value*/
    public static Map<BrowserContext, Map<String, String>> testDescription = new LinkedHashMap<BrowserContext, Map<String, String>>();
    public static Map<BrowserContext, Map<String, String>> testResults = new LinkedHashMap<BrowserContext, Map<String, String>>();

	/*copied from HtmlReportSupport*/

    public static Map<BrowserContext, Long> iStartTime = new LinkedHashMap<BrowserContext, Long>();
    public static Map<BrowserContext, Long> iEndTime = new LinkedHashMap<BrowserContext, Long>();
    public static Map<BrowserContext, Long> iExecutionTime = new LinkedHashMap<BrowserContext, Long>();
    public static Map<BrowserContext, Long> iSuiteStartTime = new LinkedHashMap<BrowserContext, Long>();
    public static Map<BrowserContext, Long> iSuiteEndTime = new LinkedHashMap<BrowserContext, Long>();
    public static Map<BrowserContext, Double> iSuiteExecutionTime = new LinkedHashMap<BrowserContext, Double>();
    public static Map<BrowserContext, Long> startStepTime = new LinkedHashMap<BrowserContext, Long>();
    public static Map<BrowserContext, Long> endStepTime = new LinkedHashMap<BrowserContext, Long>();
    public static Map<BrowserContext, Double> stepExecutionTime = new LinkedHashMap<BrowserContext, Double>();
    public static Map<BrowserContext, String> strTestName = new LinkedHashMap<BrowserContext, String>();
    public static Map<BrowserContext, String> startedAt = new LinkedHashMap<BrowserContext, String>();
    public static Map<BrowserContext, String> tc_name = new LinkedHashMap<BrowserContext, String>();
    public static Map<BrowserContext, String> packageName = new LinkedHashMap<BrowserContext, String>();
    /*mapBrowserContextTestCaseRef contains BrowserContext as key , Map<testpackage:testname,status> as value*/
    public static Map<BrowserContext, Map<String, String>> mapBrowserContextTestCaseRef = new LinkedHashMap<BrowserContext, Map<String, String>>();
    public static Map<BrowserContext, Map<String, String>> executionTime = new LinkedHashMap<BrowserContext, Map<String, String>>();
    public static String workingDir = System.getProperty("user.dir").replace(File.separator, "/");
    public static Map<BrowserContext, Integer> BFunctionNo = new LinkedHashMap<BrowserContext, Integer>();
    public ArrayList<Double> list = new ArrayList<Double>();
    public String currentSuite = "";
    public int pCount = 0;
    ;
    public int fCount = 0;

	/**/
}
