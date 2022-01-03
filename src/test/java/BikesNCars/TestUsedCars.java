package BikesNCars;

import java.util.ArrayList;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import BaseClasses.PageBaseClass;
import BaseClasses.WriteExcelSheet;
import BaseClasses.readDataSheet;
import pageModels.UsedCars;

public class TestUsedCars extends PageBaseClass {
	UsedCars usedCars;

	// Method to get cell data from excel
	public static String getData(int colNum, int rowNum) throws Exception {
		readDataSheet dataprovider = new readDataSheet();
		dataprovider.openWorkbook("Email_password_Dataset.xlsx");
		return dataprovider.getCellData("Sheet1", colNum, rowNum);
	}

	/*@BeforeSuite(groups = "Smoke Test")
	public void openBrowser() {
		try
		{
		invokeBrowser("chrome");
		}
		catch(Exception e)
		{
			reportFail(e.getMessage());
		}
	}*/

	@Test(priority = 1, groups=("Regression Testing"))
	public void usedCars() throws Exception {
		// Create object of PageBaseClass

		usedCars = new UsedCars(driver, logger);

		logger = report.createTest("Used Cars Page - Test 1");

		
		openURL("https://www.zigwheels.com/");
		maximizeBrowser();

		usedCars.findUsedCars();
		usedCars.selectLocation();

		String location = getData(0, 8);

		ArrayList<String> models = usedCars.printModels(location);
		logger.log(Status.PASS, "List of Used Car.");

		WriteExcelSheet.writeData(models, "Used Cars", "List of Used cars in Chennai", "UsedCarsChennaiTest.xlsx");

		logger.log(Status.PASS, "Output Stored in Excel File Successfully.");

	}

	/*@AfterSuite(groups = "Smoke Test")
	public void closeBrowser() {
		quitBrowser();
	}*/
}
