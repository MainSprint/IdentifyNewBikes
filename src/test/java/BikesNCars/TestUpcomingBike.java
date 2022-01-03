package BikesNCars;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import BaseClasses.PageBaseClass;
import pageModels.UpcomingBikesPage;

public class TestUpcomingBike extends PageBaseClass
{
	UpcomingBikesPage upcomingBikes;
	
	@BeforeSuite(groups = "Smoke Test")
	public void openBrowser() {

		try
		{
		invokeBrowser("chrome");
		}
		catch(Exception e)
		{
			reportFail(e.getMessage());
		}
		
	}
	
	@Test(priority = 1, groups = "upcomingbikes, Smoke Test")
	public void mouseHover() throws InterruptedException
	{
		logger = report.createTest("Open Upcoming Bikes Page");

		upcomingBikes= new UpcomingBikesPage(driver,logger);
		//Opening the Browser
		openURL("https://www.zigwheels.com/");
		maximizeBrowser();
		
		upcomingBikes.mouseHover();
		logger.log(Status.PASS, "Test Executed Successfully");

		
	}
	
	@Test(priority = 2, groups = "upcomingbikes, Smoke Test")
	public void selectManufacturer() throws InterruptedException
	{
		logger = report.createTest("Select Manufacturer");
		
		upcomingBikes.selectManufacturer();
		logger.log(Status.PASS, "Test Executed Successfully");

		
	}
	
	@Test(priority = 3, groups = "upcomingbikes, Smoke Test")
	public void viewMore() throws InterruptedException
	{
		logger = report.createTest("View More");
		
		upcomingBikes.viewMore();
		logger.log(Status.PASS, "Test Executed Successfully");

		
	}
	
	
	@Test(priority = 4, groups = "Regression Test", dependsOnMethods="viewMore")
	public void bikesUnder4Lakhs() throws InterruptedException
	{
		logger = report.createTest("Upcoming Bikes Under 4 lakh list");
		logger.log(Status.INFO, "Upcoming Bikes Under 4 lakh list");

		upcomingBikes.upcomingbikes();
		logger.log(Status.PASS, "Test Executed Successfully");
		
		//endReport();
	}
	
	/*@AfterSuite(groups = "Smoke Test")
	public void closeBrowser()
	{
		closeBrowser();
	}*/
}
