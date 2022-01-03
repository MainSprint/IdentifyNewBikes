package BaseClasses;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readDataSheet {

	// Creating new workbook variables
	public FileInputStream fis = null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row = null;
	public XSSFCell cell = null;

	// Method to open a workbook
	public void openWorkbook(String xlFilePath) throws Exception {

		// creating new input stream to read excel sheet
		fis = new FileInputStream(System.getProperty("user.dir") + "\\InputExcelSheet\\" + xlFilePath);
		workbook = new XSSFWorkbook(fis);
	}

	// Method to read data from a cell
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);

			// Using DataFormatter to format the cell data to string
			DataFormatter formatter = new DataFormatter();
			String strValue = formatter.formatCellValue(cell);
			if (strValue.equals("NONE")) {
				return " ";
			} else {
				return strValue;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist in excel workbook";
		}

	}
}
