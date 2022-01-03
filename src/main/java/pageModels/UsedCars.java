package pageModels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import BaseClasses.PageBaseClass;
import BaseClasses.ReadProperties;

public class UsedCars extends PageBaseClass {

	Properties prop;
	Actions action;
	WebDriverWait wait;
	String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\UsedCars.properties";

	public UsedCars(WebDriver driver, ExtentTest logger) {
		prop = ReadProperties.readFile(filePath);
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 60);
		// TODO Auto-generated constructor stub
	}

	public String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

	// Find Used Cars
	public void findUsedCars() {

		try {
			Actions actions = new Actions(driver);
			WebElement usedCarsOption = driver.findElement(By.xpath(getPropertyValue("usedCars_xpath")));

			// Mouse hover on UsedCars
			actions.moveToElement(usedCarsOption).perform();
			takeScreenShot("Select_UsedCars");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	// Select Location Chennai
	public void selectLocation() {

		try {
			setWaitByClick(getPropertyValue("location_xpath"));
			driver.findElement(By.xpath(getPropertyValue("location_xpath"))).click();
			sleep(2000);
			takeScreenShot("Select_Chennai");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	// Printing models
	public static ArrayList<String> printModels(String city) throws Exception {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1250)", "");
		
		String models_list = driver
				.findElement(By.xpath("//body/div[11]/div[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li[2]/div[2]/div[4]"))
				.getText();
		ArrayList<String> models = new ArrayList<String>();
		Collections.addAll(models, models_list.split("\n"));

		// Printing the Popular used Cars in Chennai
		System.out.println("Popular Models of Used Cars in " + city + " are:-");
		for (int i = 0; i < models.size(); i++) {
			System.out.println((i + 1) + " " + models.get(i));

			logger.log(Status.INFO, "List of Used Cars: " + (i + 1) + "\n" + models.get(i));

		}
		takeScreenShot("UsedCars_List");
				
		return models;

	}
}
