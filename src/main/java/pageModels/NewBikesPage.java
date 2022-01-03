package pageModels;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import BaseClasses.PageBaseClass;
import BaseClasses.ReadProperties;

public class NewBikesPage extends PageBaseClass {
	Properties prop;
	Actions action;
	WebDriverWait wait;
	String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\NewBikesPage.properties";

	public NewBikesPage(WebDriver driver, ExtentTest logger) {
		prop = ReadProperties.readFile(filePath);
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 60);
		// TODO Auto-generated constructor stub
	}

	public String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

	// To search "Upcoming Honda Bikes Under 4 Lakhs" in the search box
	public UpcomingBikesPage searchBox2(String searchText) throws InterruptedException {
		try {
			logger = report.createTest("Search on Website for upcoming bikes");

			logger.log(Status.INFO, "Entering 'Upcoming Honda Bikes Under 4 Lakhs' in the search box");

			enterText(getPropertyValue("search_xpath"), searchText);
			sleep(2000);

			clickXpath(getPropertyValue("searchIcon_xpath"));

			sleep(2000);
			WebElement resultText = driver.findElement(By.xpath(getPropertyValue("result_xpath")));
			resultText.getText();

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
					resultText);
			sleep(2000);

			takeScreenShot("Upcoming_Bikes_result");
			System.out.println(resultText);

			logger.log(Status.PASS, "Test Executed Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		UpcomingBikesPage UP = new UpcomingBikesPage(driver, logger);
		PageFactory.initElements(driver, UP);
		return UP;
	}

}
