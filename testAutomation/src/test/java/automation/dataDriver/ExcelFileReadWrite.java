package automation.dataDriver;

import jxl.Cell;
import jxl.LabelCell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author in01518
 */
public class ExcelFileReadWrite implements Iterator<String[]> {

    private static final Logger LOG = Logger
            .getLogger(ExcelFileReadWrite.class);
    private Workbook workbook = null;
    private Sheet sheet = null;
    private String[] columnNames = null;
    private int currentRow = 0;
    private int MAX_ROWS = 0;

    /**
     * This is constructor to read excel sheet
     *
     * @param workbook
     * @param sheet
     */
    public ExcelFileReadWrite(Workbook workbook, Sheet sheet) {
        this.workbook = workbook;
        this.sheet = sheet;
        this.currentRow = 0;
        this.MAX_ROWS = this.sheet.getRows();
        LOG.debug("this.currentRow = " + this.currentRow);
        LOG.debug("this.MAX_ROWS = " + this.MAX_ROWS);
    }

    /**
     * 3 Argument Constructors to get data from startRow onwards
     *
     * @param workbook
     * @param sheet
     * @param startRow
     */
    public ExcelFileReadWrite(Workbook workbook, Sheet sheet, int startRow) {
        this.workbook = workbook;
        this.sheet = sheet;
        this.currentRow = startRow;
        this.MAX_ROWS = this.sheet.getRows();
        LOG.debug("this.currentRow = " + this.currentRow);
        LOG.debug("this.MAX_ROWS = " + this.MAX_ROWS);
    }

    /**
     * Constructor to get data within startRow and endRow
     *
     * @param workbook
     * @param sheet
     * @param startRow
     * @param endRow
     */
    public ExcelFileReadWrite(Workbook workbook, Sheet sheet, int startRow, int endRow) {
        this.workbook = workbook;
        this.sheet = sheet;
        this.currentRow = startRow;
        this.MAX_ROWS = endRow + 1;
        LOG.debug("this.currentRow = " + this.currentRow);
        LOG.debug("this.MAX_ROWS = " + this.MAX_ROWS);
    }

    /**
     * 3 Argument Constructors to get data from specified columnNames
     *
     * @param workbook
     * @param sheet
     * @param columnNames
     */
    public ExcelFileReadWrite(Workbook workbook, Sheet sheet,
                              String[] columnNames) {
        this(workbook, sheet);
        this.columnNames = columnNames;
    }

    /**
     * Constructors to get data from specified columnNames and startRow onwards
     *
     * @param workbook
     * @param sheet
     * @param columnNames
     * @param startRow
     */
    public ExcelFileReadWrite(Workbook workbook, Sheet sheet,
                              String[] columnNames, int startRow) {
        this(workbook, sheet, startRow);
        this.columnNames = columnNames;
    }

    /**
     * Constructors to get data from specified columnNames and within startRow and endRow
     *
     * @param workbook
     * @param sheet
     * @param columnNames
     * @param startRow
     * @param endRow
     */
    public ExcelFileReadWrite(Workbook workbook, Sheet sheet,
                              String[] columnNames, int startRow, int endRow) {
        this(workbook, sheet, startRow, endRow);
        this.columnNames = columnNames;
    }

    @Override
    public boolean hasNext() {
        boolean hasNext = false;

        if (this.currentRow <= this.MAX_ROWS - 1) {
            hasNext = true;
        }
        LOG.debug("hasNext = " + hasNext);
        return hasNext;
    }

    @Override
    public String[] next() {
        String[] data = null;

        int dataIndex = 0;
        if (this.currentRow == 0) {
            this.currentRow++;
        }

		/* if columnNames not given return all columns */
        if (this.columnNames == null) {
            Cell[] cells = null;
            LOG.debug("this.currentRow = " + this.currentRow);
            cells = this.sheet.getRow(this.currentRow);
            data = new String[cells.length];
            for (Cell cell : cells) {

                data[dataIndex] = cell.getContents();
                LOG.debug("data[ " + dataIndex + " ] = " + data[dataIndex]);
                dataIndex++;

            }
            /* increment currentRow */
            this.currentRow = cells != null ? cells[0].getRow() + 1
                    : this.currentRow;
            LOG.debug("this.currentRow = " + this.currentRow);
        }

		/* if columnNames are given then return values for columnNames only */
        else if (this.columnNames != null) {
            /* find the cell whose names are given and add columns to list */
            List<Integer> listColumns = new ArrayList<Integer>();
            for (String columnName : this.columnNames) {
                LabelCell labelCell = this.sheet.findLabelCell(columnName);
                if (labelCell != null) {
                    LOG.debug("labelCell.getColumn() = " + labelCell.getColumn());
                    listColumns.add(labelCell.getColumn());
                }
            }

			/* get desired cells corresponding to given columnNames */
            List<Cell> listCells = new ArrayList<Cell>();

            for (Integer column : listColumns) {
                LOG.debug("column = " + column);
                LOG.debug("this.currentRow = " + this.currentRow);
                listCells.add(this.sheet.getCell(column, this.currentRow));
            }

            data = new String[listCells.size()];
            for (Cell cell : listCells) {
                data[dataIndex] = cell.getContents();
                LOG.debug("data[ " + dataIndex + " ] = " + data[dataIndex]);
                dataIndex++;
            }
			/* increment currentRow */
            this.currentRow = listCells.size() != 0 ? listCells.get(0).getRow() + 1
                    : this.currentRow;
            LOG.debug("this.currentRow = " + this.currentRow);
        }

        return data;
    }

    @Override
    public void remove() {

        throw new UnsupportedOperationException();

    }

}
