package automation.dataDriver;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public class DataTable {
    private static final Logger LOG = Logger.getLogger(DataTable.class);

    /**
     * @param excelWorkBookPath (if null, then DataTablePath from config.xml will be picked)
     * @param sheetName         (mandatory)
     * @param columns           (array of columns, if null then records from all columns will be returned)
     * @param startRow          (starting row of excel sheet to be read from)
     * @param endRow            (end row of excel sheet till it will be read)
     * @return Iterator<String []>
     * @throws BiffException
     * @throws IOException
     */

    public static Iterator<String[]> getTableArray(String excelWorkBookPath, String sheetName, String[] columns, Integer startRow, Integer endRow) throws BiffException, IOException {
        ExcelFileReadWrite recordFromFirstRowTillEnd = null;
        Workbook workbook = null;
        Sheet sheet = null;
        if (excelWorkBookPath == null) {
            excelWorkBookPath = IFrameworkConstant.LOCATION_DATATABLE_EXCEL;
        }
        /*set excel work book*/
        workbook = Workbook.getWorkbook(new File(excelWorkBookPath));
        /*Set sheet */
        sheet = workbook.getSheet(sheetName);
		
		/*read all rows from a given sheet*/

        if (columns == null && startRow == null && endRow == null) {
            LOG.info("read all rows from a given excelFile, sheet : " + excelWorkBookPath + " , " + sheetName);
            recordFromFirstRowTillEnd = new ExcelFileReadWrite(workbook, sheet);
        }
		
		/*read set of rows from a given sheet starting from given starting row*/

        if (columns == null && startRow != null && endRow == null) {
            LOG.info("read set of rows from a given excelFile, sheet , start row : " + excelWorkBookPath + " , " + sheetName + " , " + startRow);
            recordFromFirstRowTillEnd = new ExcelFileReadWrite(workbook, sheet, startRow);
        }
		
		/*read set of rows from a given sheet from given startRow till given endRow		 */

        if (columns == null && startRow != null && endRow != null) {
            LOG.info("read set of rows from a given excelFile, sheet , start row  , endRow: " + excelWorkBookPath + " , " + sheetName + " , " + startRow + " , " + endRow);
            recordFromFirstRowTillEnd = new ExcelFileReadWrite(workbook, sheet, startRow, endRow);
        }
		
		/*read all rows from given columns from a given sheet  */

        if (columns != null && startRow == null && endRow == null) {
            LOG.info("read set of rows from a given excelFile, sheet , columns : " + excelWorkBookPath + " , " + sheetName + " , " + "\"" + columns.toString() + "\"");
            recordFromFirstRowTillEnd = new ExcelFileReadWrite(workbook, sheet, columns);
        }
		
		/*read all rows from given columns from a given sheet from given starting row */
        if (columns != null && startRow != null && endRow == null) {
            LOG.info("read set of rows from a given excelFile, sheet , columns , start row  : " + excelWorkBookPath + " , " + sheetName + " , " + "\"" + columns.toString() + "\"" + " , " + startRow);
            recordFromFirstRowTillEnd = new ExcelFileReadWrite(workbook, sheet, columns, startRow);
        }
		
		/*read all rows from given columns from a given sheet from given starting row till given end row*/

        if (columns != null && startRow != null && endRow != null) {
            LOG.info("read set of rows from a given excelFile, sheet , columns , start row  , endRow: " + excelWorkBookPath + " , " + sheetName + " , " + "\"" + columns.toString() + "\"" + " , " + startRow + " , " + endRow);
            recordFromFirstRowTillEnd = new ExcelFileReadWrite(workbook, sheet, columns, startRow, endRow);
        }
        return recordFromFirstRowTillEnd;
    }
}
