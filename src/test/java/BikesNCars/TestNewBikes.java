package BikesNCars;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import BaseClasses.PageBaseClass;
import BaseClasses.readDataSheet;
import pageModels.NewBikesPage;

public class TestNewBikes extends PageBaseClass {

	
	NewBikesPage newBikes;

	// Method to get cell data from excel
	public static String getData(int colNum, int rowNum) throws Exception {
		readDataSheet dataprovider = new readDataSheet();
		dataprovider.openWorkbook("Email_Password_Dataset.xlsx");
		return dataprovider.getCellData("Sheet1", colNum, rowNum);
	}

	
	@Test(groups = "Smoke Test")
	public void openBrowser() {

		logger = report.createTest("Initializing Browser");

		// Initializing the Browser
		logger.log(Status.INFO, "Initializing the Browser");
		invokeBrowser("chrome");

		logger.log(Status.PASS, "Test Executed Successfully");

	}

	@Test(priority = 1, groups = "Regression Test")
	public void search() throws Exception {
		
		newBikes= new NewBikesPage(driver,logger);
		// Open the website URL
		openURL("https://www.zigwheels.com/");
		maximizeBrowser();

		String searchText = getData(0, 1);

		newBikes.searchBox2(searchText);

		//endReport();
	}
	
	/*@AfterSuite(groups = "Smoke Test")
	public void closeBrowser()
	{

		closeBrowser();
	}*/
}
