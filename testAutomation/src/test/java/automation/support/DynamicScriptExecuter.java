package automation.support;

import org.testng.annotations.Factory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Class to generate testNg class file dynamically based on
 * "TestClassDetails.xls" class files selection
 */
public class DynamicScriptExecuter {

    static Properties prop;
    static String executionType = "";
    String environment = null;

    /**
     * Method to read property file
     *
     * @param testCaseFile
     * @return Properties
     */
    public static Properties readPropertyFile(File testCaseFile) {
        Properties params = new Properties();
        try {
            FileReader fr = new FileReader(testCaseFile);
            params.load(fr);

        } catch (IOException e) {
            System.err.println("Error reading from the test file. File name: "
                    + testCaseFile.getName());
        }
        return params;

    }

    /**
     * Factory method to send dynamically generated test classes to testNg
     *
     * @param environment
     * @return
     */
    @Factory
    public Object[] setUp() {


        List<Object> list = new ArrayList<Object>();

        try {
            list = GetExcutionClass.getClassList();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Object[] data = list.toArray();
        return data;
    }

}