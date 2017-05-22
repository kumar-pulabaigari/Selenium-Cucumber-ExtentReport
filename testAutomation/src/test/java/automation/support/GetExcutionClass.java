package automation.support;

import automation.dataDriver.IFrameworkConstant;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Class to read excel file which contains execution script class details
 */
public class GetExcutionClass {

    static String excelFilePath = "";

    static {

        excelFilePath = IFrameworkConstant.EXECUTION_SCRIPT_FILE_EXCEL;

    }


    /**
     * method to get execution class list with flag 'Y'/'YES'
     *
     * @return List<Object>
     */
    public static List<Object> getClassList() {
        List<Object> list = new ArrayList<Object>();

        try {
            HashMap<String, String> excelValueMap = readExcelData();
            for (String className : excelValueMap.keySet()) {
                String executeable = excelValueMap.get(className);
                if (executeable.equalsIgnoreCase("Y")
                        || executeable.equalsIgnoreCase("YES")) {
                    Object classObj = Class.forName(className).newInstance();
                    list.add(classObj);
                }
            }
        } catch (InstantiationException ie) {

        } catch (IllegalAccessException ile) {

        } catch (ClassNotFoundException ce) {

        }

        return list;

    }

    /**
     * Method to read excel data
     *
     * @return HashMap<String,String>
     */
    public static HashMap<String, String> readExcelData() {
        HashMap<String, String> excelValueMap = new HashMap<String, String>();
        try {
            System.out.println("excelFilePath:" + excelFilePath);
            ExcelReadUtilPoi excelRead = new ExcelReadUtilPoi(excelFilePath);
            HSSFSheet sheet1 = excelRead.workbook.getSheetAt(0);

            List<String> excelVal = excelRead.read(sheet1);

            for (String rowVal : excelVal) {
                String[] col1DatatArray = rowVal.split("~");
                excelValueMap.put(col1DatatArray[0], col1DatatArray[1]);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return excelValueMap;
    }


}
