package automation.support;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Excel Util class to read excel file using apache.poi
 *
 * @author Swarup Mishra, 2016
 */
public class ExcelReadUtilPoi {

    public HSSFWorkbook workbook = null;
    FileInputStream fileInputStream = null;

    /**
     * constructor to initialize excel reader
     *
     * @param path
     */
    public ExcelReadUtilPoi(String path) {
        try {
            fileInputStream = new FileInputStream(path);
            workbook = new HSSFWorkbook(fileInputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * to read data from shete
     *
     * @param sheet
     * @return
     * @throws IOException
     */
    public List<String> read(HSSFSheet sheet) throws IOException {
        List<String> excelVal = new ArrayList<String>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row rovVal = (Row) rowIterator.next();
            String rowVal = "";
            for (int i = 0; i < rovVal.getLastCellNum(); i++) {
                HSSFCell cellA1 = (HSSFCell) rovVal.getCell(i);
                rowVal = rowVal + cellA1.getStringCellValue() + "~";
            }

            excelVal.add(rowVal);

        }

        return excelVal;
    }


}
