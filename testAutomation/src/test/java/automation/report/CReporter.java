package automation.report;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * Class Name: CReporter. Reason for naming class CReporter is to avoid
 * ambiguity with TestNG Reporter class
 *
 * @author in01518
 */
public class CReporter {
    private static final Logger LOG = Logger.getLogger(CReporter.class);
    private static Map<BrowserContext, CReporter> mapBrowserContextReporter = new HashMap<BrowserContext, CReporter>();
    private BrowserContext browserContext = null;
    private String reportPath = null;
    private String[] package_testname;

    /**
     * @param browserName
     * @param version
     * @param platform
     * @param append
     * @throws IOException
     * @Consturctor for CReporter
     */
    private CReporter(String browserName, String version, String platform,
                      boolean append) {
        this.browserContext = BrowserContext.getBrowserContext(browserName,
                version, platform);
        this.reportPath = this.filePath();
        LOG.info("instance member browserContext was set to : ");
        LOG.info(this.browserContext);

    }

    public static synchronized CReporter getCReporter(String browserName,
                                                      String version, String platform, boolean append) {
        CReporter reporter = null;
        BrowserContext browserContext = BrowserContext.getBrowserContext(
                browserName, version, platform);
        reporter = CReporter.mapBrowserContextReporter.get(browserContext);
        if (reporter == null) {
            reporter = new CReporter(browserName, version, platform, append);
            LOG.info("Instance Of CReporter Created");
            CReporter.mapBrowserContextReporter.put(browserContext, reporter);
            LOG.info("reporter was placed into CReporter.mapBrowserContextReporter");
        }
        return reporter;
    }

    /* copied from CReporter */
    private static String dateStamp() {
        DateFormat dateFormat = new SimpleDateFormat();
        Date date = new Date();
        return dateFormat.format(date).substring(0, 7);
    }

    //
    private static String dateTime() {
        Date todaysDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss a");
        String formattedDate = formatter.format(todaysDate);
        return formattedDate;

    }

    private static String getTime() {
        Date todaysDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss a");
        String formattedDate = formatter.format(todaysDate);
        return formattedDate;

    }

    // return time and date
    private static String timeStamp() {
        Date today = new Date();
        return new java.sql.Timestamp(today.getTime()).toString();
    }

    // return environmental details
    private static String osEnvironment() {

        return "Current suit exicuted on : " + System.getProperty("os.name")
                + "/version : " + System.getProperty("os.version")
                + "/Architecture : " + System.getProperty("os.arch");
    }

    private static String getHostName() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        String hostname = addr.getHostName();

        return hostname;
    }

    private static String makeUniqueImagePath(String fileName) {
        String newFileName = fileName;
        try {
            // Verifying if the file already exists, if so append the numbers
            // 1,2 so on to the fine name.

            File myPngImage = new File(fileName);
            int counter = 1;
            while (myPngImage.exists()) {
                newFileName = fileName + "_" + counter;
                myPngImage = new File(newFileName);
                counter++;
            }
            return newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return newFileName;
        }
    }

    /**
     * @return BrowserContext
     */
    public BrowserContext getBrowserContext() {
        return this.browserContext;
    }

    private String getFileName(String filePath) {
        String fileNameOnly = null;
        File file = new File(filePath);
        try {

            if (file.isFile()) {
                fileNameOnly = file.getName().toString();
            }

        } catch (Exception e) {
            LOG.error("Exception Encountered : " + e.getMessage());
            throw e;
        }
        return fileNameOnly;
    }

    /**
     * @return ResultDir for each browserContext
     */
    private String filePath() {
        String strDirectory = null;
        String browserName = this.browserContext.getBrowserName();
        String browserVersion = this.browserContext.getBrowserVersion();
        String browserPlatform = this.browserContext.getBrowserPlatform();

        LOG.debug("browser name = " + browserName);
        switch (browserName.toLowerCase()) {
            case "firefox":
                strDirectory = "FF";
                break;

            case "chrome":
                strDirectory = "CHROME";
                break;
            case "ie":
                strDirectory = "IE";
                break;
            default:
                strDirectory = browserName;
            /*
			 * try { throw new IOException("BrowserName not properly defined");
			 * } catch (IOException e) { // TODO Auto-generated catch block
			 * LOG.error("IOException Encountered : " + e.getMessage());
			 * e.printStackTrace(); }
			 */

        }

        strDirectory = strDirectory + "-" + browserVersion + "-"
                + browserPlatform;

        File resultDir = new File(ReporterConstants.LOCATION_RESULT
                + File.separator + strDirectory);
        LOG.info("resultDir = " + resultDir);
        if (resultDir.exists() == false) {
            try {
                resultDir.mkdirs();
            } catch (Exception e) {
                LOG.info("Exception Encountered : " + e.getMessage());
            }
        }

        File screenShotDir = new File(ReporterConstants.LOCATION_RESULT
                + File.separator + strDirectory + File.separator
                + ReporterConstants.FOLDER_SCREENRSHOTS);

        if (screenShotDir.exists() == false) {
            try {
                screenShotDir.mkdirs();
                this.copyLogos(ReporterConstants.LOCATION_CLIENT_LOGO,
                        ReporterConstants.LOCATION_COMPANY_LOGO,
                        ReporterConstants.LOCATION_FAILED_LOGO,
                        ReporterConstants.LOCATION_MINUS_LOGO,
                        ReporterConstants.LOCATION_PASSED_LOGO,
                        ReporterConstants.LOCATION_TEST_PASSED_LOGO,
                        ReporterConstants.LOCATION_PLUS_LOGO,
                        ReporterConstants.LOCATION_WARNING_LOGO,
                        ReporterConstants.LOCATION_JQUERY_CSS_FOLDER,
                        ReporterConstants.LOCATION_JQUERY_IMAGES_FOLDER,
                        ReporterConstants.LOCATION_JQUERY_JS_FOLDER);
            } catch (Exception e) {
                LOG.info("Exception Encountered : " + e.getMessage());
            }
        }

        try {
            strDirectory = resultDir.getCanonicalPath();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOG.error("IOException Encountered : " + e.getMessage());
            e.printStackTrace();
        }
        return strDirectory;
    }

    private void copyLogos(String... logos) {

        File destFolder = new File(this.filePath() + File.separatorChar
                + ReporterConstants.FOLDER_SCREENRSHOTS);
        for (String logo : logos) {
            LOG.info("Current Logo Name : " + logo);
            File logoFile = new File(logo);
			/* if folder then copy folder */
            if (logoFile.isDirectory()) {
                try {
                    FileUtils.copyDirectoryToDirectory(logoFile, destFolder);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    LOG.info(logoFile + "could not be copied to " + destFolder);
                    LOG.info("IOException Encountered : " + e.getMessage());
                    e.printStackTrace();
                }
            }
			/* if file then copy file */
            if (logoFile.isDirectory() == false) {
				/* copy File if exist */
                if (logoFile.exists()) {
                    try {
                        FileUtils.copyFileToDirectory(logoFile, destFolder);
                        LOG.info(logoFile + "copied to " + destFolder);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        LOG.info(logoFile + "could not be copied to "
                                + destFolder);
                        LOG.info("IOException Encountered : " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * deletes html detailed file if exist
     */

    private void htmlCreateReport() {

        File file = new File(this.filePath() + "/" + TestResult.strTestName
                + "_Results"
				/* + TestResult.timeStamp */ + ".html");// "Results.html"
        if (file.exists()) {
            file.delete();
        }

    }

    public void failureReport(String strStepName, String strStepDes,
                              String response) throws Throwable {
        BufferedWriter writer = null;
        switch (ReporterConstants.REPORT_FORMAT.toLowerCase()) {

            case "html":

                String reportDescription = strStepDes;
                // // New Screen shot code to avoid overriding \\\\
                strStepDes = strStepDes.replaceAll(":", "_");
                strStepDes = strStepDes.replaceAll(",", "_");
                strStepDes = strStepDes.replaceAll("&", "_");
                strStepDes = strStepDes.replaceAll(" ", "_");
                strStepDes = strStepDes.replaceAll("=", "_");
                String fileName = this.filePath() + File.separatorChar
                        + ReporterConstants.FOLDER_SCREENRSHOTS
                        + File.separatorChar + strStepDes + ".txt";

                fileName = makeUniqueImagePath(fileName);
                File file = new File(fileName);
                file.createNewFile();
                writer = new BufferedWriter(new FileWriter(fileName));
                writer.write(response);
                writer.close();
                this.onFailure(strStepName, reportDescription, fileName);
                break;
            default:

                // // New Screen shot code to avoid overriding \\\\
                fileName = this.filePath() + File.separatorChar
                        + ReporterConstants.FOLDER_SCREENRSHOTS
                        + File.separatorChar + strStepDes.replace(" ", "_")
                        + ".txt";
                fileName = makeUniqueImagePath(fileName);
                // File f = new File(fileName+".txt");
                writer = new BufferedWriter(new FileWriter(fileName));
                writer.write(response);
                writer.close();
                this.onFailure(strStepName, strStepDes, fileName);
                break;
        }

    }

    /**
     * @param strStepName
     * @param strStepDes
     * @param fileName
     */
    private void onSuccessscreenshot(String strStepName, String strStepDes,
                                     String fileName) {

        String href = "./"
                + fileName
                .substring(
                        fileName.indexOf(ReporterConstants.FOLDER_SCREENRSHOTS),
                        fileName.length());
        String imgSrc = "'." + File.separatorChar
                + ReporterConstants.FOLDER_SCREENRSHOTS + File.separatorChar
                + this.getFileName(ReporterConstants.LOCATION_TEST_PASSED_LOGO)
                + "'";
        Writer writer = null;
        try {
            File file = new File(this.filePath() + File.separatorChar
                    + TestResult.strTestName.get(this.browserContext)
                    + "_Results" +
					/* + TestResult.timeStamp + */".html");// "SummaryReport.html"

            writer = new FileWriter(file, true);
            Integer stepNumValue = TestResult.stepNum.get(this.browserContext);
            if (stepNumValue != null) {

                TestResult.stepNum.put(this.browserContext, stepNumValue + 1);
            }

            writer.write("<tr class='content2' >");
            writer.write("<td>" + TestResult.stepNum.get(this.browserContext)
                    + "</td> ");
            writer.write("<td class='justified'>" + strStepName + "</td>");
            writer.write("<td class='justified'>" + strStepDes + "</td> ");

            Integer passNumValue = TestResult.PassNum.get(this.browserContext);
            if (passNumValue != null) {
                TestResult.PassNum.put(this.browserContext, passNumValue + 1);
            }

            writer.write("<td class='Pass' align='center'><a  href='"
                    + href
                    + "'"
                    + " alt= Screenshot  width= 15 height=15 style='text-decoration:none;'><img  src="
                    + imgSrc + "height='18'/></a></td>");

            String strPassTime = CReporter.getTime();
            writer.write("<td><small>" + strPassTime + "</small></td> ");
            writer.write("</tr> ");
            writer.close();
            String strPackageName = TestResult.packageName
                    .get(this.browserContext);
            String strTcName = TestResult.tc_name.get(this.browserContext);
            if (!TestResult.mapBrowserContextTestCaseRef
                    .get(this.browserContext)
                    .get(strPackageName + ":" + strTcName).equals("PASS")) {
                TestResult.mapBrowserContextTestCaseRef
                        .get(this.browserContext).put(
                        strPackageName + ":" + strTcName, "FAIL");
            }
        } catch (Exception e) {
            LOG.info("Exception Encountered : " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void successwithscreenshot(String strStepName, String strStepDes,
                                      WebDriver... webDrivers) throws Throwable {

        switch (ReporterConstants.REPORT_FORMAT.toLowerCase()) {

            case "html":

                String reportDescription = strStepDes;

                strStepDes = strStepDes.replaceAll(":", "_");
                strStepDes = strStepDes.replaceAll(",", "_");
                strStepDes = strStepDes.replaceAll("&", "_");
                strStepDes = strStepDes.replaceAll(" ", "_");
                String fileName = this.filePath() + File.separatorChar
                        + ReporterConstants.FOLDER_SCREENRSHOTS
                        + File.separatorChar + strStepDes + ".jpeg";

                fileName = makeUniqueImagePath(fileName);
                for (WebDriver webDriver : webDrivers) {

                    this.screenShot(webDriver, fileName);
                    break;
                }

                this.onSuccessscreenshot(strStepName, reportDescription, fileName);

                break;

            default:

                // // New Screen shot code to avoid overriding \\\\

                fileName = this.filePath() + File.separatorChar
                        + ReporterConstants.FOLDER_SCREENRSHOTS
                        + File.separatorChar + strStepDes.replace(" ", "_");
                fileName = makeUniqueImagePath(fileName);

                this.onSuccessscreenshot(strStepName, strStepDes, fileName
                        + ".jpeg");

                break;
        }

    }

    public void createHtmlSummaryReport(String Url, boolean append)
            throws Exception {

        File file = new File(this.filePath() + "/" + "SummaryResults"
				/* + TestResult.timeStamp */ + ".html");// "SummaryReport.html"
        Writer writer = null;
        String imgSrcClientLogo = "." + File.separatorChar
                + ReporterConstants.FOLDER_SCREENRSHOTS + File.separatorChar
                + this.getFileName(ReporterConstants.LOCATION_CLIENT_LOGO);
        String imgSrcCompanyLogo = "." + File.separatorChar
                + ReporterConstants.FOLDER_SCREENRSHOTS + File.separatorChar
                + this.getFileName(ReporterConstants.LOCATION_COMPANY_LOGO);
        if (file.exists()) {
            file.delete();
        }
        writer = new FileWriter(file, append);
        try {
            writer.write("<!DOCTYPE html>");
            writer.write("<html> ");
            writer.write("<head> ");
            writer.write("<meta charset='UTF-8'> ");
            writer.write("<title>Automation Execution Results Summary</title>");

            // Jquery java script
            writer.write("<link rel='stylesheet' type='text/css' href='Screenshots/css/datatable/jquery.dataTables.css'>");
            writer.write("<link rel='stylesheet' type='text/css' href='Screenshots/css/jquery-ui.css'>");
            writer.write("<link rel='stylesheet' type='text/css' href='Screenshots/css/datatable/dataTables.jqueryui.css'>");
            writer.write("<link rel='stylesheet' type='text/css' href='Screenshots/css/custom.css'>");

            writer.write("<script type='text/javascript' language='javascript' src='Screenshots/js/jquery-1.11.1.min.js'></script>");
            writer.write("<script type='text/javascript' language='javascript' src='Screenshots/js/datatable/jquery.dataTables.min.js'></script>");
            writer.write("<script type='text/javascript' language='javascript' src='Screenshots/js/datatable/dataTables.jqueryui.js'></script>");
            writer.write("<script type='text/javascript' language='javascript' src='Screenshots/js/jquery-ui.min.js'></script>");
            writer.write("<script type='text/javascript' language='javascript' src='Screenshots/js/custom.js'></script>");

            writer.write("<style type='text/css'>");
            writer.write("body {");
            writer.write("background-color: #FFFFFF; ");
            writer.write("font-family: Verdana, Geneva, sans-serif; ");
            writer.write("text-align: center; ");
            writer.write("} ");

            writer.write("small { ");
            writer.write("font-size: 0.7em; ");
            writer.write("} ");

            writer.write("table { ");
            writer.write("box-shadow: 9px 9px 10px 4px #BDBDBD;");
            writer.write("border: 0px solid #4D7C7B;");
            writer.write("border-collapse: collapse; ");
            writer.write("border-spacing: 0px; ");
            writer.write("width: 1000px; ");
            writer.write("margin-left: auto; ");
            writer.write("margin-right: auto; ");
            writer.write("} ");

            writer.write("tr.heading { ");
            writer.write("background-color: #041944;");
            writer.write("color: #FFFFFF; ");
            writer.write("font-size: 0.7em; ");
            writer.write("font-weight: bold; ");
            writer.write("background:-o-linear-gradient(bottom, #999999 5%, #000000 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #999999), color-stop(1, #000000) );");
            writer.write("background:-moz-linear-gradient( center top, #999999 5%, #000000 100% );");
            writer.write("filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#999999, endColorstr=#000000);	background: -o-linear-gradient(top,#999999,000000);");
            writer.write("} ");

            writer.write("tr.subheading { ");
            writer.write("background-color: #6A90B6;");
            writer.write("color: #000000; ");
            writer.write("font-weight: bold; ");
            writer.write("font-size: 0.7em; ");
            writer.write("text-align: justify; ");
            writer.write("} ");

            writer.write("tr.section { ");
            writer.write("background-color: #A4A4A4; ");
            writer.write("color: #333300; ");
            writer.write("cursor: pointer; ");
            writer.write("font-weight: bold;");
            writer.write("font-size: 0.8em; ");
            writer.write("text-align: justify;");
            writer.write("background:-o-linear-gradient(bottom, #56aaff 5%, #e5e5e5 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #56aaff), color-stop(1, #e5e5e5) );");
            writer.write("background:-moz-linear-gradient( center top, #56aaff 5%, #e5e5e5 100% );");
            writer.write("filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#56aaff, endColorstr=#e5e5e5);	background: -o-linear-gradient(top,#56aaff,e5e5e5);");

            writer.write("} ");

            writer.write("tr.subsection { ");
            writer.write("cursor: pointer; ");
            writer.write("} ");

            writer.write("tr.content { ");
            writer.write("background-color: #FFFFFF; ");
            writer.write("color: #000000; ");
            writer.write("font-size: 0.7em; ");
            writer.write("display: table-row; ");
            writer.write("} ");

            writer.write("tr.content2 { ");
            writer.write("background-color:#;E1E1E1");
            writer.write("border: 1px solid #4D7C7B;");
            writer.write("color: #000000; ");
            writer.write("font-size: 0.7em; ");
            writer.write("display: table-row; ");
            writer.write("} ");

            writer.write("td, th { ");
            writer.write("padding: 5px; ");
            writer.write("border: 1px solid #4D7C7B; ");
            writer.write("text-align: inherit\0/; ");
            writer.write("} ");

            writer.write("th.Logos { ");
            writer.write("padding: 5px; ");
            writer.write("border: 0px solid #4D7C7B; ");
            writer.write("text-align: inherit /;");
            writer.write("} ");

            writer.write("td.justified { ");
            writer.write("text-align: justify; ");
            writer.write("} ");

            writer.write("td.pass {");
            writer.write("font-weight: bold; ");
            writer.write("color: green; ");
            writer.write("} ");

            writer.write("td.fail { ");
            writer.write("font-weight: bold; ");
            writer.write("color: red; ");
            writer.write("} ");

            writer.write("td.done, td.screenshot { ");
            writer.write("font-weight: bold; ");
            writer.write("color: black; ");
            writer.write("} ");

            writer.write("td.debug { ");
            writer.write("font-weight: bold; ");
            writer.write("color: blue; ");
            writer.write("} ");

            writer.write("td.warning { ");
            writer.write("font-weight: bold; ");
            writer.write("color: orange; ");
            writer.write("} ");
            writer.write("</style> ");

            writer.write("<script> ");
            writer.write("function toggleMenu(objID) { ");
            writer.write(" if (!document.getElementById) return;");
            writer.write(" var ob = document.getElementById(objID).style; ");
            writer.write("if(ob.display === 'none') { ");
            writer.write(" try { ");
            writer.write(" ob.display='table-row-group';");
            writer.write("} catch(ex) { ");
            writer.write("	 ob.display='block'; ");
            writer.write("} ");
            writer.write("} ");
            writer.write("else { ");
            writer.write(" ob.display='none'; ");
            writer.write("} ");
            writer.write("} ");
            writer.write("function toggleSubMenu(objId) { ");
            writer.write("for(i=1; i<10000; i++) { ");
            writer.write("var ob = document.getElementById(objId.concat(i)); ");
            writer.write("if(ob === null) { ");
            writer.write("break; ");
            writer.write("} ");
            writer.write("if(ob.style.display === 'none') { ");
            writer.write("try { ");
            writer.write(" ob.style.display='table-row'; ");
            writer.write("} catch(ex) { ");
            writer.write("ob.style.display='block'; ");
            writer.write("} ");
            writer.write(" } ");
            writer.write("else { ");
            writer.write("ob.style.display='none'; ");
            writer.write("} ");
            writer.write(" } ");
            writer.write("} ");
            writer.write("</script> ");
            writer.write("</head> ");

            writer.write("<body> ");
            writer.write("</br>");

            writer.write("<table id='Logos' class='testData'>");
            writer.write("<colgroup>");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("</colgroup> ");
            writer.write("<thead> ");

            writer.write("<tr class='content'>");
            writer.write("<th class ='Logos' colspan='2' >");
            writer.write("<img align ='left' src= " + imgSrcClientLogo
                    + "></img>");
            writer.write("</th>");
            writer.write("<th class = 'Logos' colspan='2' > ");
            writer.write("<img align ='right' src=  " + imgSrcCompanyLogo
                    + "></img>");
            writer.write("</th> ");
            writer.write("</tr> ");

            writer.write("</thead> ");
            writer.write("</table> ");

            writer.write("<table id='header' class='testData'> ");
            writer.write("<colgroup> ");
            writer.write("<col style='width: 25%' /> ");
            writer.write("<col style='width: 25%' /> ");
            writer.write("<col style='width: 25%' /> ");
            writer.write(" <col style='width: 25%' /> ");
            writer.write("</colgroup> ");

            writer.write("<thead> ");

            writer.write("<tr class='heading'> ");
            writer.write("<th colspan='4' style='font-family:Copperplate Gothic Bold; font-size:1.4em;'> ");
            writer.write("Automation Execution Result Summary ");
            writer.write("</th> ");
            writer.write("</tr> ");
            writer.write("<tr class='subheading'> ");
            writer.write("<th>&nbsp;Date&nbsp;&&nbsp;Time&nbsp;:&nbsp;" + ""
                    + "</th> ");
            // writer.write("<th>&nbsp;:&nbsp;08-Apr-2013&nbsp;06:24:21&nbsp;PM</th> ");
            writer.write("<th> &nbsp;" + CReporter.dateTime() + "&nbsp;</th> ");
            writer.write("<th>&nbsp;OnError&nbsp;:&nbsp;</th> ");
            writer.write("<th>" + ReporterConstants.ON_ERROR_ACTION + "</th> ");
            writer.write("</tr> ");

            writer.write("<tr class='subheading'> ");
            writer.write("<th>&nbsp;Suite Executed&nbsp;:&nbsp;</th> ");
            writer.write("<th>" + ReporterConstants.SUITE_NAME + "</th> ");
            writer.write("<th>&nbsp;Browser&nbsp;:</th> ");
            writer.write("<th>" + this.browserContext.getBrowserName()
                    + "</th> ");
            writer.write("</tr> ");

            writer.write("<tr class='subheading'> ");
            writer.write("<th>&nbsp;Host Name&nbsp;:</th> ");
            writer.write("<th>" + InetAddress.getLocalHost().getHostName()
                    + "</th> ");
            writer.write("<th>&nbsp;No.&nbsp;Of&nbsp;Threads&nbsp;:&nbsp;</th> ");
            writer.write("<th>" + "NA" + "</th> ");
            writer.write("</tr> ");

            writer.write("<tr class='subheading'> ");
            writer.write("<th>&nbsp;Device&nbsp;Name&nbsp;:</th> ");
            writer.write("<th>" + ReporterConstants.DEVICE_NAME + "</th> ");
            writer.write("<th>Build Version&nbsp;:</th> ");
            writer.write("<th>" + this.browserContext.getBrowserVersion()
                    + "</th> ");
            writer.write("</tr> ");

            writer.write("<tr class='subheading'> ");
            writer.write("<th colspan='4'> ");
            writer.write("&nbsp;Environment -  " + Url + "");
            writer.write("</th> ");
            writer.write("</tr> ");
            writer.write("</thead> ");
            writer.write("</table> ");
            writer.write("<div class='mainTableDiv4'>");
            writer.write("<table id='main' class='testData'> ");
            writer.write("<colgroup> ");
            writer.write("<col style='width: 5%' /> ");
            writer.write("<col style='width: 35%' /> ");
            writer.write("<col style='width: 42%' /> ");
            writer.write("<col style='width: 10%' /> ");
            writer.write("<col style='width: 8%' /> ");
            writer.write("</colgroup> ");
            writer.write("<thead> ");
            writer.write("<tr class='heading'> ");
            writer.write("<th>S.NO</th> ");
            writer.write("<th>Test Case</th> ");
            writer.write("<th>Description</th> ");
            writer.write("<th>Time</th> ");
            writer.write("<th>Status</th> ");
            writer.write("</tr> ");
            writer.write("</thead> ");

			/* get corresponding map to browserContext */
            Map<String, String> testCaseRef = TestResult.mapBrowserContextTestCaseRef
                    .get(this.browserContext);

            Iterator<Entry<String, String>> iterator1 = testCaseRef.entrySet()
                    .iterator();
            int serialNo = 1;
            writer.write("<tbody> ");
            while (iterator1.hasNext()) {

                Entry<String, String> mapEntry1 = (Entry<String, String>) iterator1
                        .next();
				/* key contains packagename:testmethod */
                LOG.info("Key of mapEntry1 : " + mapEntry1.getKey());
                this.package_testname = mapEntry1.getKey().toString()
                        .split(":");
                LOG.info("package is present in package_testname[0] : "
                        + this.package_testname[0]);
                LOG.info("test method is present in package_testname[1] : "
                        + this.package_testname[1]);
                String testCaseExecutionStatus = (String) mapEntry1.getValue();
                LOG.info("value against package_testname is : "
                        + testCaseExecutionStatus);
                // writer.write("<tbody> ");
                writer.write("<tr class='content2' > ");
                writer.write("<td class='justified'>" + serialNo + "</td>");
                if (testCaseExecutionStatus
                        .equals(ReporterConstants.TEST_CASE_STATUS_PASS)) {
                    writer.write("<td class='justified'><a href='"
                            + package_testname[1]
                            + "_Results"
									/* + TestResult.timeStamp */ + ".html#'"
                            + "' target='about_blank'>"
                            + this.package_testname[1].substring(0,
                            this.package_testname[1].indexOf("-"))
                            + "</a></td>");
                } else {
                    writer.write("<td class='justified'><a href='"
                            + this.package_testname[1]
                            + "_Results"
									/* + TestResult.timeStamp */ + ".html'"
                            + " target='about_blank'>"
                            + this.package_testname[1].substring(0,
                            this.package_testname[1].indexOf("-"))
                            + "</a></td>");
                }
                String localTestDescription = "";
                if (TestResult.testDescription != null) {
                    Map<String, String> mapTestDescription = TestResult.testDescription
                            .get(this.browserContext);
                    if (mapTestDescription != null) {
                        localTestDescription = mapTestDescription
                                .get(this.package_testname[1]);
                    }
                }
                writer.write("<td class='justified'>" + localTestDescription
                        + "</td>");

                writer.write("<td>"
                        + TestResult.executionTime.get(this.browserContext)
                        .get(this.package_testname[1])
                        + " Seconds</td>");
                if (TestResult.testResults.get(this.browserContext)
                        .get(this.package_testname[1])
                        .equals(ReporterConstants.TEST_CASE_STATUS_PASS))
                    writer.write("<td class='pass'>"
                            + ReporterConstants.TEST_CASE_STATUS_PASS
                            + "</td> ");
                else
                    writer.write("<td class='fail'>"
                            + ReporterConstants.TEST_CASE_STATUS_FAIL
                            + "</td> ");
                writer.write("</tr>");
                // writer.write("</tbody> ");
                serialNo = serialNo + 1;
            }

            writer.write("</tbody> ");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            LOG.info("Excepiton Encountered : " + e.getMessage());
            writer.flush();
            writer.close();
        }

    }

    /**
     * @param strStepName
     * @param strStepDes
     * @throws IOException
     */
    private void onSuccess(String strStepName, String strStepDes)
            throws IOException {

        File file = new File(this.filePath() + File.separatorChar
                + TestResult.strTestName.get(this.browserContext) + "_Results"
				/* + TestResult.timeStamp */ + ".html");// "SummaryReport.html"
        Writer writer = null;
        Integer stepNumValue = TestResult.stepNum.get(this.browserContext);
        String imgSrc = "'." + File.separatorChar
                + ReporterConstants.FOLDER_SCREENRSHOTS + File.separatorChar
                + this.getFileName(ReporterConstants.LOCATION_PASSED_LOGO)
                + "'";
        if (stepNumValue != null) {

            TestResult.stepNum.put(this.browserContext, stepNumValue + 1);
        }

        try {
            // testdescrption.put(TestTitleDetails.x.toString(),
            // TestResult.testDescription.get(TestTitleDetails.x));
            String strPackageName = TestResult.packageName
                    .get(this.browserContext);
            String strTcName = TestResult.tc_name.get(this.browserContext);
            if (!TestResult.mapBrowserContextTestCaseRef
                    .get(this.browserContext)
                    .get(strPackageName + ":" + strTcName)
                    .equals(ReporterConstants.TEST_CASE_STATUS_FAIL)) {
                TestResult.mapBrowserContextTestCaseRef
                        .get(this.browserContext).put(
                        strPackageName + ":" + strTcName,
                        ReporterConstants.TEST_CASE_STATUS_PASS);
                // map.put(TestTitleDetails.x.toString(),
                // TestResult.testDescription.get(TestTitleDetails.x.toString()));
            }
            writer = new FileWriter(file, true);
            writer.write("<tr class='content2' >");
            writer.write("<td>" + TestResult.stepNum.get(this.browserContext)
                    + "</td> ");
            writer.write("<td class='justified'>" + strStepName + "</td>");
            writer.write("<td class='justified'>" + strStepDes + "</td> ");

            writer.write("<td class='Pass' align='center'><img  src=" + imgSrc
                    + " width='18' height='18'/></td> ");

            Integer passNumValue = TestResult.PassNum.get(this.browserContext);
            if (passNumValue != null) {
                TestResult.PassNum.put(this.browserContext, passNumValue + 1);
            }

            String strPassTime = CReporter.getTime();
            writer.write("<td><small>" + strPassTime + "</small></td> ");
            writer.write("</tr> ");
            writer.close();

        } catch (Exception e) {
            LOG.info("Exception Encountered : " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * @param strStepName
     * @param strStepDes
     * @param fileName
     */
    private void onFailure(String strStepName, String strStepDes,
                           String fileName) {

        String href = "./"
                + fileName
                .substring(
                        fileName.indexOf(ReporterConstants.FOLDER_SCREENRSHOTS),
                        fileName.length());
        String imgSrc = "'." + File.separatorChar
                + ReporterConstants.FOLDER_SCREENRSHOTS + File.separatorChar
                + this.getFileName(ReporterConstants.LOCATION_FAILED_LOGO)
                + "'";
        Writer writer = null;
        try {
            File file = new File(this.filePath() + File.separatorChar
                    + TestResult.strTestName.get(this.browserContext)
                    + "_Results" +
					/* + TestResult.timeStamp + */".html");// "SummaryReport.html"

            writer = new FileWriter(file, true);
            Integer stepNumValue = TestResult.stepNum.get(this.browserContext);
            if (stepNumValue != null) {

                TestResult.stepNum.put(this.browserContext, stepNumValue + 1);
            }

            writer.write("<tr class='content2' >");
            writer.write("<td>" + TestResult.stepNum.get(this.browserContext)
                    + "</td> ");
            writer.write("<td class='justified'>" + strStepName + "</td>");
            writer.write("<td class='justified'>" + strStepDes + "</td> ");

            Integer failNumValue = TestResult.FailNum.get(this.browserContext);
            if (stepNumValue != null) {

                TestResult.FailNum.put(this.browserContext, failNumValue + 1);
            }

			/*
			 * writer.write("<td class='Fail' align='center'><a  href='"+
			 * "./Screenshots"+"/" + strStepDes.replace(" ", "_") + "_" +
			 * TestResult.timeStamp + ".jpeg'"+
			 * " alt= Screenshot  width= 15 height=15 style='text-decoration:none;'><img  src='./Screenshots/failed.ico' width='18' height='18'/></a></td>"
			 * );
			 */
            // New Screen shot code to avoid overriding \\\\
            writer.write("<td class='Fail' align='center'><a  href='"
                    + href
                    + "'"
                    + " alt= Screenshot  width= 15 height=15 style='text-decoration:none;'><img  src="
                    + imgSrc + "height='18'/></a></td>");

            String strFailTime = CReporter.getTime();
            writer.write("<td><small>" + strFailTime + "</small></td> ");
            writer.write("</tr> ");
            writer.close();
            String strPackageName = TestResult.packageName
                    .get(this.browserContext);
            String strTcName = TestResult.tc_name.get(this.browserContext);
            if (!TestResult.mapBrowserContextTestCaseRef
                    .get(this.browserContext)
                    .get(strPackageName + ":" + strTcName).equals("PASS")) {
                TestResult.mapBrowserContextTestCaseRef
                        .get(this.browserContext).put(
                        strPackageName + ":" + strTcName, "FAIL");
            }
        } catch (Exception e) {
            LOG.info("Exception Encountered : " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void onWarning(String strStepName, String strStepDes) {

        Writer writer = null;
        try {
            File file = new File(this.filePath() + File.separatorChar
                    + TestResult.strTestName.get(this.browserContext)
                    + "_Results"
					/* + TestResult.timeStamp */ + ".html");// "SummaryReport.html"
            String imgSrc = "'." + File.separatorChar
                    + ReporterConstants.FOLDER_SCREENRSHOTS
                    + File.separatorChar
                    + this.getFileName(ReporterConstants.LOCATION_WARNING_LOGO)
                    + "'";
            writer = new FileWriter(file, true);
            Integer stepNumValue = TestResult.stepNum.get(this.browserContext);
            if (stepNumValue != null) {

                TestResult.stepNum.put(this.browserContext, stepNumValue + 1);
            }

            writer.write("<tr class='content2' >");
            writer.write("<td>" + TestResult.stepNum.get(this.browserContext)
                    + "</td> ");
            writer.write("<td class='justified'>" + strStepName + "</td>");
            writer.write("<td class='justified'>" + strStepDes + "</td> ");
            // TestResult.FailNum = TestResult.FailNum + 1;

            writer.write("<td class='Fail'  align='center'><a  href='"
                    + "."
                    + File.separatorChar
                    + ReporterConstants.FOLDER_SCREENRSHOTS
                    + File.separatorChar
                    + strStepDes.replace(" ", "_") /*
					 * + "_" +
					 * TestResult.timeStamp
					 */
                    + ".jpeg'"
                    + " alt= Screenshot  width= 15 height=15 style='text-decoration:none;'><img  src="
                    + imgSrc + " width='18' height='18'/></a></td>");

            String strFailTime = CReporter.getTime();
            writer.write("<td><small>" + strFailTime + "</small></td> ");
            writer.write("</tr> ");
            writer.close();

        } catch (Exception e) {
            LOG.info("Exception Encountered : " + e.getMessage());
            e.printStackTrace();
        }

    }

    // return time

    public void testHeader(String testName, boolean append) {
        Writer writer = null;

        try {
            String imgSrcClientLogo = "." + File.separatorChar
                    + ReporterConstants.FOLDER_SCREENRSHOTS
                    + File.separatorChar
                    + this.getFileName(ReporterConstants.LOCATION_CLIENT_LOGO);
            String imgSrcCompanyLogo = "." + File.separatorChar
                    + ReporterConstants.FOLDER_SCREENRSHOTS
                    + File.separatorChar
                    + this.getFileName(ReporterConstants.LOCATION_COMPANY_LOGO);
            TestResult.strTestName.put(this.browserContext, testName);
            File file = new File(this.filePath() + File.separatorChar
                    + TestResult.strTestName.get(this.browserContext)
                    + "_Results"
					/* + TestResult.timeStamp */ + ".html");// "Results.html"
            writer = new FileWriter(file, append);

            writer.write("<!DOCTYPE html> ");
            writer.write("<html>");
            writer.write("<head> ");
            writer.write("<meta charset='UTF-8'> ");
            writer.write("<title>"
                    + TestResult.strTestName.get(this.browserContext)
                    + " Execution Results</title> ");

            // Jquery java script
            writer.write("<link rel='stylesheet' type='text/css' href='Screenshots/css/datatable/jquery.dataTables.css'>");
            writer.write("<link rel='stylesheet' type='text/css' href='Screenshots/css/jquery-ui.css'>");
            writer.write("<link rel='stylesheet' type='text/css' href='Screenshots/css/datatable/dataTables.jqueryui.css'>");
            writer.write("<link rel='stylesheet' type='text/css' href='Screenshots/css/custom.css'>");

            writer.write("<script type='text/javascript' language='javascript' src='Screenshots/js/jquery-1.11.1.min.js'></script>");
            writer.write("<script type='text/javascript' language='javascript' src='Screenshots/js/datatable/jquery.dataTables.min.js'></script>");
            writer.write("<script type='text/javascript' language='javascript' src='Screenshots/js/datatable/dataTables.jqueryui.js'></script>");
            writer.write("<script type='text/javascript' language='javascript' src='Screenshots/js/jquery-ui.min.js'></script>");
            writer.write("<script type='text/javascript' language='javascript' src='Screenshots/js/custom.js'></script>");

            writer.write("<style type='text/css'> ");
            writer.write("body { ");
            writer.write("background-color: #FFFFFF; ");
            writer.write("font-family: Verdana, Geneva, sans-serif; ");
            writer.write("text-align: center; ");
            writer.write("} ");

            writer.write("small { ");
            writer.write("font-size: 0.7em; ");
            writer.write("} ");

            writer.write("table { ");
            writer.write("box-shadow: 9px 9px 10px 4px #BDBDBD;");
            writer.write("border: 0px solid #4D7C7B; ");
            writer.write("border-collapse: collapse; ");
            writer.write("border-spacing: 0px; ");
            writer.write("width: 1000px; ");
            writer.write("margin-left: auto; ");
            writer.write("margin-right: auto; ");
            writer.write("} ");

            writer.write("tr.heading { ");
            writer.write("background-color: #041944; ");
            writer.write("color: #FFFFFF; ");
            writer.write("font-size: 0.7em; ");
            writer.write("font-weight: bold; ");
            writer.write("background:-o-linear-gradient(bottom, #999999 5%, #000000 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #999999), color-stop(1, #000000) );");
            writer.write("background:-moz-linear-gradient( center top, #999999 5%, #000000 100% );");
            writer.write("filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#999999, endColorstr=#000000);	background: -o-linear-gradient(top,#999999,000000);");
            writer.write("} ");

            writer.write("tr.subheading { ");
            writer.write("background-color: #FFFFFF; ");
            writer.write("color: #000000; ");
            writer.write("font-weight: bold; ");
            writer.write("font-size: 0.7em; ");
            writer.write("text-align: justify; ");
            writer.write("} ");

            writer.write("tr.section { ");
            writer.write("background-color: #A4A4A4; ");
            writer.write("color: #333300; ");
            writer.write("cursor: pointer; ");
            writer.write("font-weight: bold; ");
            writer.write("font-size: 0.7em; ");
            writer.write("text-align: justify; ");
            writer.write("background:-o-linear-gradient(bottom, #56aaff 5%, #e5e5e5 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #56aaff), color-stop(1, #e5e5e5) );");
            writer.write("background:-moz-linear-gradient( center top, #56aaff 5%, #e5e5e5 100% );");
            writer.write("filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#56aaff, endColorstr=#e5e5e5);	background: -o-linear-gradient(top,#56aaff,e5e5e5);");
            writer.write("} ");

            writer.write("tr.subsection { ");
            writer.write("cursor: pointer; ");
            writer.write("} ");

            writer.write("tr.content { ");
            writer.write("background-color: #FFFFFF; ");
            writer.write("color: #000000; ");
            writer.write("font-size: 0.7em; ");
            writer.write("display: table-row; ");
            writer.write("} ");

            writer.write("tr.content2 { ");
            writer.write("background-color: #E1E1E1; ");
            writer.write("border: 1px solid #4D7C7B;");
            writer.write("color: #000000; ");
            writer.write("font-size: 0.75em; ");
            writer.write("display: table-row; ");
            writer.write("} ");

            writer.write("td, th { ");
            writer.write("padding: 5px; ");
            writer.write("border: 1px solid #4D7C7B; ");
            writer.write("text-align: inherit\0/; ");
            writer.write("} ");

            writer.write("th.Logos { ");
            writer.write("padding: 5px; ");
            writer.write("border: 0px solid #4D7C7B; ");
            writer.write("text-align: inherit /;");
            writer.write("} ");

            writer.write("td.justified { ");
            writer.write("text-align: justify; ");
            writer.write("} ");

            writer.write("td.pass { ");
            writer.write("font-weight: bold; ");
            writer.write("color: green; ");
            writer.write("} ");

            writer.write("td.fail { ");
            writer.write("font-weight: bold; ");
            writer.write("color: red; ");
            writer.write("} ");

            writer.write("td.done, td.screenshot { ");
            writer.write("font-weight: bold; ");
            writer.write("color: black; ");
            writer.write("} ");

            writer.write("td.debug { ");
            writer.write("font-weight: bold;");
            writer.write("color: blue; ");
            writer.write("} ");

            writer.write("td.warning { ");
            writer.write("font-weight: bold; ");
            writer.write("color: orange; ");
            writer.write("} ");
            writer.write("</style> ");

            writer.write("<script> ");
            writer.write("function toggleMenu(objID) { ");
            writer.write("if (!document.getElementById) return; ");
            writer.write("var ob = document.getElementById(objID).style; ");
            writer.write("if(ob.display === 'none') { ");
            writer.write("try { ");
            writer.write("ob.display='table-row-group'; ");
            writer.write("} catch(ex) { ");
            writer.write("ob.display='block'; ");
            writer.write("} ");
            writer.write("} ");
            writer.write("else { ");
            writer.write("ob.display='none'; ");
            writer.write("} ");
            writer.write("} ");
            writer.write("function toggleSubMenu(objId) { ");
            writer.write("for(i=1; i<10000; i++) { ");
            writer.write("var ob = document.getElementById(objId.concat(i)); ");
            writer.write("if(ob === null) { ");
            writer.write("break; ");
            writer.write("} ");
            writer.write("if(ob.style.display === 'none') { ");
            writer.write("try { ");
            writer.write("ob.style.display='table-row'; ");
            writer.write("} catch(ex) { ");
            writer.write("ob.style.display='block'; ");
            writer.write("} ");
            writer.write("} ");
            writer.write("else { ");
            writer.write("ob.style.display='none'; ");
            writer.write("} ");
            writer.write("} ");
            writer.write("} ");
            writer.write("</script> ");
            writer.write("</head> ");
            writer.write("<body> ");
            writer.write("</br>");

            writer.write("<table id='Logos' class='testData'>");
            writer.write("<colgroup>");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("</colgroup> ");
            writer.write("<thead> ");

            writer.write("<tr class='content'>");
            writer.write("<th class ='Logos' colspan='2' >");
            writer.write("<img align ='left' src= " + imgSrcClientLogo
                    + "></img>");
            writer.write("</th>");
            writer.write("<th class = 'Logos' colspan='2' > ");
            writer.write("<img align ='right' src= " + imgSrcCompanyLogo
                    + "></img>");
            writer.write("</th> ");
            writer.write("</tr> ");
            writer.write("</thead> ");
            writer.write("</table> ");

            writer.write("<table id='header' class='testData'> ");
            writer.write("<colgroup> ");
            writer.write("<col style='width: 25%' /> ");
            writer.write("<col style='width: 25%' /> ");
            writer.write("<col style='width: 25%' /> ");
            writer.write("<col style='width: 25%' /> ");
            writer.write("</colgroup> ");
            writer.write(" <thead> ");

            writer.write("<tr class='heading'> ");
            writer.write("<th colspan='4' style='font-family:Copperplate Gothic Bold; font-size:1.4em;'> ");
            writer.write("**" + TestResult.strTestName.get(this.browserContext)
                    + " Execution Results **");
            writer.write("</th> ");
            writer.write("</tr> ");
            writer.write("<tr class='subheading'> ");
            writer.write("<th>&nbsp;Date&nbsp;&&nbsp;Time&nbsp;:&nbsp;</th> ");

            writer.write("<th>" + CReporter.dateTime() + "</th> ");
            writer.write("<th>&nbsp;Iteration&nbsp;Mode&nbsp;:&nbsp;</th> ");
            writer.write("<th>" + ReporterConstants.ITERAION_MODE + "</th> ");
            writer.write("</tr> ");

            writer.write("<tr class='subheading'> ");
            writer.write("<th>Device&nbsp;Name&nbsp;:</th> ");
            writer.write("<th>" + ReporterConstants.DEVICE_NAME + "</th> ");
            writer.write("<th>Version&nbsp;:</th> ");
            writer.write("<th>" + this.browserContext.getBrowserVersion()
                    + "</th> ");
            writer.write("</tr> ");

            writer.write("<tr class='subheading'> ");
            writer.write("<th>&nbsp;Browser&nbsp;:&nbsp;</th> ");
            writer.write("<th>" + this.browserContext.getBrowserName()
                    + "</th> ");
            writer.write(" <th>&nbsp;Executed&nbsp;on&nbsp;:&nbsp;</th> ");
            writer.write("<th>" + InetAddress.getLocalHost().getHostName()
                    + "</th> ");
            writer.write("</tr> ");
            writer.write("</thead> ");
            writer.write("</table> ");
            writer.write("<div class='mainTableDiv4'>");
            writer.write("<table id='main' class='testData'> ");
            writer.write("<colgroup> ");
            writer.write("<col style='width: 5%' /> ");
            writer.write("<col style='width: 26%' /> ");
            writer.write("<col style='width: 51%' /> ");
            writer.write("<col style='width: 8%' /> ");
            writer.write("<col style='width: 10%' /> ");
            writer.write("</colgroup> ");
            writer.write("<thead> ");
            writer.write("<tr class='heading'> ");
            writer.write("<th>S.NO</th> ");
            writer.write("<th>Steps</th> ");
            writer.write("<th>Details</th> ");
            writer.write("<th>Status</th> ");
            writer.write("<th>Time</th> ");
            writer.write("</tr>");
            writer.write("</thead>");
			/*writer.write("</table>");
			writer.write("</div> ");*/
            writer.close();

            String strPackageName = TestResult.packageName
                    .get(this.browserContext);
            String strTcName = TestResult.tc_name.get(this.browserContext);

			/* get test case status map */
            Map<String, String> mapTestCaseStatus = TestResult.mapBrowserContextTestCaseRef
                    .get(this.browserContext);
            if (mapTestCaseStatus == null) {
                mapTestCaseStatus = new HashMap<String, String>();
            }

            mapTestCaseStatus.put(strPackageName + ":" + strTcName, "status");
            TestResult.mapBrowserContextTestCaseRef.put(this.browserContext,
                    mapTestCaseStatus);
        } catch (Exception e) {

        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (Exception e) {

            }
        }
    }

    private void reportStep(String StepDesc) throws IOException {
        StepDesc = StepDesc.replaceAll(" ", "_");

        File file = new File(this.filePath() + File.separatorChar
                + TestResult.strTestName.get(this.browserContext) + "_Results"
				/* + TestResult.timeStamp */ + ".html");// "SummaryReport.html"
        Writer writer = null;

        try {
            writer = new FileWriter(file, true);
            Integer bFunctionNo = TestResult.BFunctionNo
                    .get(this.browserContext);
            if (bFunctionNo != null && bFunctionNo > 0) {
                writer.write("</tbody>");
            }
            writer.write("<tbody>");
            writer.write("<tr class='section'> ");
            writer.write("<td colspan='5' onclick=toggleMenu('" + StepDesc
                    + TestResult.stepNum.get(this.browserContext) + "')>+ "
                    + StepDesc + "</td>");
            writer.write("</tr> ");
            writer.write("</tbody>");
            writer.write("<tbody id='" + StepDesc
                    + TestResult.stepNum.get(this.browserContext)
                    + "' style='display:table-row-group'>");
            writer.close();

            TestResult.BFunctionNo.put(this.browserContext, bFunctionNo + 1);
        } catch (Exception e) {

        }
    }

    public void closeDetailedReport() throws IOException {

        File file = new File(this.filePath() + File.separatorChar
                + TestResult.strTestName.get(this.browserContext) + "_Results"
				/* + TestResult.timeStamp */ + ".html");// "SummaryReport.html"
        Writer writer = null;

        try {
            writer = new FileWriter(file, true);
            writer.write("</table></div>");
            writer.write("<table id='footer' class='testData'>");
            writer.write("<colgroup>");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("</colgroup>");
            writer.write("<tfoot>");
            writer.write("<tr class='heading'> ");
            writer.write("<th colspan='4'>Execution Time In Seconds (Includes Report Creation Time) : "
                    + TestResult.executionTime.get(this.browserContext).get(
                    TestResult.tc_name.get(this.browserContext))
                    + "&nbsp;</th> ");
            writer.write("</tr> ");
            writer.write("<tr class='content'>");
            writer.write("<td class='pass'>&nbsp;Steps Passed&nbsp;:</td>");
            writer.write("<td class='pass'> "
                    + TestResult.PassNum.get(this.browserContext) + "</td>");
            writer.write("<td class='fail'>&nbsp;Steps Failed&nbsp;: </td>");
            writer.write("<td class='fail'>"
                    + TestResult.FailNum.get(this.browserContext) + "</td>");
            writer.write("</tr>");
            writer.close();
        } catch (Exception e) {

        }
    }

    public void closeSummaryReport() throws IOException {

        File file = new File(this.filePath() + File.separatorChar
                + "SummaryResults"
				/* + TestResult.timeStamp */ + ".html");// "SummaryReport.html"
        Writer writer = null;
        try {
            // get pass/fail test cases count
            Integer passTestCasesCount = TestResult.passCounter
                    .get(this.browserContext) == null ? 0
                    : TestResult.passCounter.get(this.browserContext);
            Integer failTestCasesCount = TestResult.failCounter
                    .get(this.browserContext) == null ? 0
                    : TestResult.failCounter.get(this.browserContext);

            writer = new FileWriter(file, true);

            String googleChartURL = "https://www.google.com/jsapi";
            writer.write("<script type='text/javascript' language='javascript' src='" + googleChartURL + "'></script>");
            writer.write("<script type='text/javascript'>");
            writer.write("google.load('visualization', '1', {'packages':['corechart']});");
            writer.write("google.setOnLoadCallback(drawChart);");
            writer.write("function drawChart() {");
            writer.write("var data = new google.visualization.DataTable();");
            writer.write("data.addColumn('string', 'Result');");
            writer.write("data.addColumn('number', 'Count');");
            writer.write("data.addRows([");
            writer.write("['Pass'," + passTestCasesCount + "],");
            writer.write("['Fail'," + failTestCasesCount + "],");
            writer.write("]);");

            if (ReporterConstants.pieChart.equalsIgnoreCase("true")) {
                writer.write("var options = {'title':'Automation Execution Status : Pie Chart','width':400,'height':300, is3D: true, strokeWidth : 100, backgroundColor: { stroke: \"#5A5A5A\", strokeWidth: 3, fill: \"#F4F4F4\" },};");
                writer.write("var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));");
                writer.write("chart.draw(data, options);");
                writer.write("}");
                writer.write("</script>");
                writer.write("<div id='piechart_3d' style=\" position: relative; width: 400px; height: 300px; left: 450px;padding-top:10px;padding-bottom:10px\"></div>");
            } else if (ReporterConstants.barGraph.equalsIgnoreCase("true")) {
                writer.write("var options = {'title':'Automation Execution Status : Bar Graph','width':400,'height':300, is3D: true, strokeWidth : 100, backgroundColor: { stroke: \"#5A5A5A\", strokeWidth: 3, fill: \"#F4F4F4\" },};");
                writer.write("var chart = new google.visualization.ColumnChart(document.getElementById('bargraph_3d'));");
                writer.write("chart.draw(data, options);");
                writer.write("}");
                writer.write("</script>");
                writer.write("<div id=\"bargraph_3d\" style=\" position: relative; width: 400px; height: 300px; left: 450px;padding-top:10px;padding-bottom:10px\"></div>");
            } else if (ReporterConstants.lineChart.equalsIgnoreCase("true")) {
                writer.write("var options = {'title':'Automation Execution Status : Line Chart','width':400,'height':300, is3D: true, strokeWidth : 100, backgroundColor: { stroke: \"#5A5A5A\", strokeWidth: 3, fill: \"#F4F4F4\" },};");
                writer.write("var chart = new google.visualization.LineChart(document.getElementById('linegraph_3d'));");
                writer.write("chart.draw(data, options);");
                writer.write("}");
                writer.write("</script>");
                writer.write("<div id='linegraph_3d' style=\" position: relative; width: 400px; height: 300px; left: 450px;padding-top:10px;padding-bottom:10px\"></div>");
            }

            writer.write("<table id='footer' class='testData'>");
            writer.write("<colgroup>");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("<col style='width: 25%' />");
            writer.write("</colgroup> ");
            writer.write("<tfoot>");
            writer.write("<tr class='heading'>");
            writer.write("<th colspan='4'>Total Duration  In Seconds (Including Report Creation) : "
                    + (int) ((double) TestResult.iSuiteExecutionTime
                    .get(this.browserContext)) + "</th>");
            writer.write("</tr>");
            writer.write("<tr class='content'>");
            writer.write("<td class='pass'>&nbsp;Tests Passed&nbsp;:</td>");

            // entry for pass test cases count
            writer.write("<td class='pass'> " + passTestCasesCount + "</td> ");
            writer.write("<td class='fail'>&nbsp;Tests Failed&nbsp;:</td>");

            // entry for fail test cases count
            writer.write("<td class='fail'> " + failTestCasesCount + "</td> ");
            writer.write("</tr>");
            writer.write("</tfoot>");
            writer.write("</table> ");
            writer.close();
        } catch (Exception e) {

        }
    }

    public void calculateTestCaseStartTime() {
        TestResult.iStartTime.put(this.browserContext,
                System.currentTimeMillis());
    }

    /***
     * This method is supposed to be used in the @AfterMethod to calculate the
     * total test case execution time to show in Reports by taking the start
     * time from the calculateTestCaseStartTime method.
     */
    public void calculateTestCaseExecutionTime() {
        TestResult.iEndTime
                .put(this.browserContext, System.currentTimeMillis());
        Long iExecutionTimeValue = TestResult.iEndTime.get(this.browserContext)
                - TestResult.iStartTime.get(this.browserContext);
        TestResult.iExecutionTime.put(this.browserContext, iExecutionTimeValue);
        long execTimeInSecs = TimeUnit.MILLISECONDS
                .toSeconds(TestResult.iExecutionTime.get(this.browserContext));
        String testCaseName = TestResult.tc_name.get(this.browserContext);
        Map<String, String> mapTCExecTime = TestResult.executionTime
                .get(this.browserContext);
        if (mapTCExecTime == null) {
            mapTCExecTime = new HashMap<String, String>();
        }
        mapTCExecTime.put(testCaseName, String.valueOf(execTimeInSecs));
        TestResult.executionTime.put(this.browserContext, mapTCExecTime);

    }

    /***
     * This method is supposed to be used in the @BeforeSuite in-order trigger
     * the Suite Start Time which inturn used to calculate the Total Suite
     * execution time to show in Reports.
     */
    public void calculateSuiteStartTime() {

        TestResult.iSuiteStartTime.put(this.browserContext,
                System.currentTimeMillis()); // Newly added
    }

    /***
     * This method is supposed to be used in the @AfterMethod to calculate the
     * total suite execution time to show in Reports by taking the suite start
     * time from the calculateSuiteStartTime method.
     */
    public void calculateSuiteExecutionTime() {
        try {
            TestResult.iSuiteEndTime.put(this.browserContext,
                    System.currentTimeMillis()); // Newly added
            double dblSuiteexecTime = (TestResult.iSuiteEndTime
                    .get(this.browserContext) - TestResult.iSuiteStartTime
                    .get(this.browserContext)) / 1000.000;
            Double DoubleSuiteExecTime = new Double(dblSuiteexecTime);
            TestResult.iSuiteExecutionTime.put(this.browserContext,
                    DoubleSuiteExecTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reportCreater() throws Throwable {

        switch (ReporterConstants.REPORT_FORMAT.toLowerCase()) {

            case "html":

                this.htmlCreateReport();

                break;
            default:

                this.htmlCreateReport();
                break;
        }
    }

    public void SuccessReport(String strStepName, String strStepDes)
            throws Throwable {

        switch (ReporterConstants.REPORT_FORMAT.toLowerCase()) {

            case "html":
			/* take screen shot if Screenshot is required for passed test cases */
                if (ReporterConstants.BOOLEAN_ONSUCCESS_SCREENSHOT == true) {

				/* WebDriver webDriver = WebDriverFactory.getWebDriver(null,this.testContext.getCurrentXmlTest().getParameter("browser"));
				 ActionEngine.screenShot(webDriver ,testUtil.filePath()+"/"+"Screenshots"+"/" +strStepDes.replace(" ", "_") + "_" + TestEngine.timeStamp +".jpeg");
				 */
                }
                this.onSuccess(strStepName, strStepDes);
                break;

            default:
			/* take screen shot if Screenshot is required for passed test cases */
                if (ReporterConstants.BOOLEAN_ONSUCCESS_SCREENSHOT == true) {
				/*
				 * WebDriver webDriver = WebDriverFactory.getWebDriver(null,
				 * this
				 * .testContext.getCurrentXmlTest().getParameter("browser"));
				 * ActionEngine.screenShot(webDriver ,
				 * testUtil.filePath()+"/"+"Screenshots"+"/" +
				 * strStepDes.replace(" ", "_") + "_" + TestEngine.timeStamp +
				 * ".jpeg");
				 */
                }
                this.onSuccess(strStepName, strStepDes);
                break;
        }
    }

    public void failureReport(String strStepName, String strStepDes,
                              WebDriver... webDrivers) throws Throwable {

        switch (ReporterConstants.REPORT_FORMAT.toLowerCase()) {

            case "html":

                String reportDescription = strStepDes;
                // // New Screen shot code to avoid overriding \\\\
                strStepDes = strStepDes.replaceAll(":", "_");
                strStepDes = strStepDes.replaceAll(",", "_");
                strStepDes = strStepDes.replaceAll("&", "_");
                strStepDes = strStepDes.replaceAll(" ", "_");
                String fileName = this.filePath() + File.separatorChar
                        + ReporterConstants.FOLDER_SCREENRSHOTS
                        + File.separatorChar + strStepDes + ".jpeg";

                fileName = makeUniqueImagePath(fileName);

			/* Logic to take screenshot */
			/*
			 * WebDriver webDriver = WebDriverFactory.getWebDriver(null,
			 * this.testContext.getCurrentXmlTest().getParameter("browser"));
			 * ActionEngine.screenShot(webDriver, fileName+".jpeg");
			 */
                for (WebDriver webDriver : webDrivers) {
                    // commented by swarup as saucelab gives problem
                    this.screenShot(webDriver, fileName);
                    break;
                }

                this.onFailure(strStepName, reportDescription, fileName);
                // New Screenshot code Starts//
                // if(!screenShotSource.equalsIgnoreCase("Selenium"))
                // screenShotSource = "Selenium";
                // New Screenshot code ends//
                break;

            default:

                // // New Screen shot code to avoid overriding \\\\

                fileName = this.filePath() + File.separatorChar
                        + ReporterConstants.FOLDER_SCREENRSHOTS
                        + File.separatorChar + strStepDes.replace(" ", "_");
                fileName = makeUniqueImagePath(fileName);

			/* Logic to take screenshot */
			/*
			 * WebDriver webDriver = WebDriverFactory.getWebDriver(null,
			 * this.testContext.getCurrentXmlTest().getParameter("browser"));
			 * ActionEngine.screenShot(webDriver, fileName+".jpeg");
			 */

                this.onFailure(strStepName, strStepDes, fileName + ".jpeg");
                // New Screenshot code Starts//
                // if(!screenShotSource.equalsIgnoreCase("Selenium"))
                // screenShotSource = "Selenium";
                // New Screenshot code ends//
                break;
        }

    }

    // New Screen shot code to avoid overriding \\\\

    public void warningReport(String strStepName, String strStepDes)
            throws Throwable {

        switch (ReporterConstants.REPORT_FORMAT.toLowerCase()) {

            case "html":
			/* logic to take screen shot */
			/*
			 * WebDriver webDriver = WebDriverFactory.getWebDriver(null,
			 * this.testContext.getCurrentXmlTest().getParameter("browser"));
			 * ActionEngine
			 * .screenShot(webDriver,testUtil.filePath()+"/"+"Screenshots"+"/" +
			 * strStepDes.replace(" ", "_") + "_" + TestEngine.timeStamp +
			 * ".jpeg");
			 */

                this.onWarning(strStepName, strStepDes);
                break;

            default:
			/* logic to take screen shot */
			/*
			 * WebDriver webDriver = WebDriverFactory.getWebDriver(null,
			 * this.testContext.getCurrentXmlTest().getParameter("browser"));
			 * ActionEngine
			 * .screenShot(webDriver,testUtil.filePath()+"/"+"Screenshots"+"/" +
			 * strStepDes.replace(" ", "_") + "_" + TestEngine.timeStamp +
			 * ".jpeg");
			 */

                this.onWarning(strStepName, strStepDes);
                break;
        }

    }

    private void screenShot(WebDriver driver, String fileName) {
        WebDriver driverScreenShot = new Augmenter().augment(driver);
        File scrFile = ((TakesScreenshot) driverScreenShot)
                .getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initTestCase(String packageName, String testCaseName,
                             String testCaseDescription, boolean appendTestCaseResult) {
        TestResult.tc_name.put(this.browserContext, testCaseName + "-");
        TestResult.packageName.put(this.browserContext, packageName);
        this.testHeader(TestResult.tc_name.get(this.browserContext),
                appendTestCaseResult);
        TestResult.stepNum.put(browserContext, 0);
        TestResult.PassNum.put(browserContext, 0);
        TestResult.FailNum.put(browserContext, 0);
        TestResult.testName.put(browserContext, testCaseName);
        this.calculateTestCaseStartTime();
        if (testCaseDescription != null) {
            this.initTestCaseDescription(testCaseDescription);
        }
    }

    public void initTestCaseDescription(String testCaseDescription) {
        if (testCaseDescription != null) {
            Map<String, String> mapTestDescription = TestResult.testDescription
                    .get(this.getBrowserContext());
            if (mapTestDescription == null) {
                mapTestDescription = new HashMap<String, String>();

            }
            mapTestDescription.put(TestResult.tc_name.get(this.browserContext),
                    testCaseDescription);
            TestResult.testDescription.put(this.browserContext,
                    mapTestDescription);
        }
    }

    public void updateTestCaseStatus() {
        if (TestResult.FailNum.get(this.browserContext) != 0) {
            Integer failCount = TestResult.failCounter.get(this.browserContext) == null ? 1
                    : TestResult.failCounter.get(this.browserContext) + 1;
            TestResult.failCounter.put(this.browserContext, failCount);
            Map<String, String> mapResult = TestResult.testResults
                    .get(this.browserContext);
            if (mapResult == null) {
                mapResult = new HashMap<String, String>();
            }
            mapResult.put(TestResult.tc_name.get(this.browserContext),
                    ReporterConstants.TEST_CASE_STATUS_FAIL);
            TestResult.testResults.put(this.browserContext, mapResult);
        } else {
            Integer passCount = TestResult.passCounter.get(this.browserContext) == null ? 1
                    : TestResult.passCounter.get(this.browserContext) + 1;
            TestResult.passCounter.put(this.browserContext, passCount);
            Map<String, String> mapResult = TestResult.testResults
                    .get(this.browserContext);
            if (mapResult == null) {
                mapResult = new HashMap<String, String>();
            }
            mapResult.put(TestResult.tc_name.get(this.browserContext),
                    ReporterConstants.TEST_CASE_STATUS_PASS);
            TestResult.testResults.put(this.browserContext, mapResult);
        }
    }

}
