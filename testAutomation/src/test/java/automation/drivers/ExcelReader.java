package automation.drivers;

import com.google.common.io.Files;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.Scenario;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import java.io.FileOutputStream;

public class ExcelReader {
	private int iRowNumber = -1;
	protected String[][] dataValue;
	public Map<String, String> mDataRow = new HashMap<String, String>();
	private String sFileName, sSheetName;
	private FileInputStream inputStream;
	private Workbook workbook;
	private Sheet firstSheet;

	public ExcelReader(String sFileName, String sSheetName) {
		this.sFileName = sFileName;
		this.sSheetName = sSheetName;
		try {
			inputStream = new FileInputStream(new File(sFileName));
			if (Files.getFileExtension(sFileName).trim().equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else if (Files.getFileExtension(sFileName).trim().equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			}

			int sheetNumber = workbook.getNameIndex(sSheetName);
			if (sheetNumber == -1) {
				firstSheet = workbook.getSheetAt(0);
			} else {
				firstSheet = workbook.getSheetAt(sheetNumber);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Provided file path: " + sFileName + " is Invalied");
			System.out.println("File Not Found Exception details" + e.toString());
		} catch (IOException e) {
			System.out.println("Provided file is : " + sFileName + " is not in a read mode");
			System.out.println("IO Exception details" + e.toString());
		}
	}

	public boolean getIntoMap(Scenario scenario) {
		getIntoDataStringArray();
		
		for (int i = 0; i < dataValue.length; i++) {
			if (dataValue[i][0].trim().equalsIgnoreCase(scenario.getName())) {
				iRowNumber = i;
				break;
			}
		}
		if (iRowNumber == -1) {
			System.out.println("Scenario name" + scenario.getName()
					+ " is not found in the test data file :" + sFileName + ", Sheet Name is " + sSheetName);
			return false;
		}

		for (int j = 0; j < dataValue[0].length; j++) {
			mDataRow.put(dataValue[0][j].trim(), dataValue[iRowNumber][j].trim());
		}

		return false;
	}

	public String getStringFromMap(Scenario scenario, String columnName) {
		if (mDataRow.isEmpty()) {
			getIntoMap(scenario);
		}
		if (mDataRow.get(columnName.trim()).isEmpty() || mDataRow.get(columnName.trim()) == null) {
			return "";
		} else {
			return mDataRow.get(columnName.trim());
		}
	}

	@SuppressWarnings("deprecation")
	public String[][] getIntoDataStringArray() {
		
		//int i = 0;
		//int j = 0;
		String[][] data = new String[firstSheet.getPhysicalNumberOfRows()][firstSheet.getRow(0).getPhysicalNumberOfCells()];
		for (int x = 0; x < firstSheet.getPhysicalNumberOfRows(); x++) {
			Row row = firstSheet.getRow(x);
			for (int y = 0; y < row.getPhysicalNumberOfCells(); y++) {
				Cell cell = row.getCell(y);
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue());
					data[x][y] = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue());
					if (cell.getBooleanCellValue()) {
						data[x][y] = "True";
					} else {
						data[x][y] = "False";
					}

					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue());
					data[x][y] = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_FORMULA:
					data[x][y] = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					data[x][y] = "";
					break;
				default:
					data[x][y] = "";
					break;
				}
			}	
		}
		dataValue =data;
		return dataValue;
	}

	@SuppressWarnings("deprecation")
	public void getIntoDataScenario() {

		int i = 0, j = 0;
		Iterator<Row> iterator = firstSheet.iterator();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			j = 0;
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue());
					dataValue[i][j] = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue());
					if (cell.getBooleanCellValue()) {
						dataValue[i][j] = "True";
					} else {
						dataValue[i][j] = "False";
					}

					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue());
					dataValue[i][j] = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_FORMULA:
					dataValue[i][j] = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					dataValue[i][j] = "";
					break;
				default:
					dataValue[i][j] = "";
					break;
				}
				System.out.print(" - ");
				j++;
			}
			System.out.println();
			i++;
		}
	}
}
