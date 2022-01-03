package BikesNCars;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import BaseClasses.PageBaseClass;
import BaseClasses.readDataSheet;
import pageModels.LoginPage;

public class TestLoginPage extends PageBaseClass {

	LoginPage login;

	// Method to get cell data from excel
	public static String getData(int colNum, int rowNum) throws Exception {
		readDataSheet dataprovider = new readDataSheet();
		dataprovider.openWorkbook("Email_Password_Dataset.xlsx");
		return dataprovider.getCellData("Sheet1", colNum, rowNum);
	}

	/*@BeforeSuite(groups = "Smoke Test")
	public void openBrowser() {

		try {
			invokeBrowser("chrome");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}*/

	@Test(priority = 1, groups = ("Smoke Test"))
	public void openLoginPage() throws InterruptedException {
		logger = report.createTest("Open Login Page");
		openURL("https://www.zigwheels.com/");
		maximizeBrowser();

		login = new LoginPage(driver, logger);
		login.openLoginWindow();

		logger.log(Status.INFO, "Opened Login Window.");

		logger.log(Status.PASS, "Test Executed Successfully");

	}

	@Test(priority = 2, groups = ("Regression Test"))
	public void invalidEmailId() throws Exception {
		String email = getData(0, 4);

		logger = report.createTest("Login Page - Test 1");

		login.enterCredentials(email);
		logger.log(Status.INFO, "Entered Invalid Email ID ");

		logger.log(Status.PASS, "Test Executed Successfully");

	}

	@Test(priority = 3, groups = ("Regression Test"))
	public void blankEmailId() throws Exception {
		String email = getData(0, 5);

		logger = report.createTest("Login Page - Test 2");

		login.enterCredentials2(email);
		logger.log(Status.INFO, "Entered blank Email ID");

		logger.log(Status.PASS, "Test Executed Successfully");

	}

	@AfterSuite(groups = "Smoke Test")
	public void closeBrowser() {
		quitBrowser();
	}
}
