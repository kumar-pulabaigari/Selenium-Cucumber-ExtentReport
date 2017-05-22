package automation.dataDriver;

import automation.report.ConfigFileReadWrite;

import java.io.File;

public interface IFrameworkConstant {

    String FILE_FRAMEWORK =  System.getProperty("user.dir") + File.separator +"resources/config.properties";
    String LOCATION_DATATABLE_EXCEL = ConfigFileReadWrite.read(FILE_FRAMEWORK, "LOCATION_DATATABLE_EXCEL");
    String EXECUTION_SCRIPT_FILE_EXCEL = ConfigFileReadWrite.read(FILE_FRAMEWORK, "EXECUTION_SCRIPT_FILE_EXCEL");
}
