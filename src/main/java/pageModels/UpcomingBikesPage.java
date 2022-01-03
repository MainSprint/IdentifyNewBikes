package pageModels;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import BaseClasses.PageBaseClass;
import BaseClasses.ReadProperties;
import BaseClasses.WriteExcelSheet;

public class UpcomingBikesPage extends PageBaseClass {

	Properties prop;
	Actions action;
	WebDriverWait wait;

	static Double Price;
	static String Name;
	static String LaunchDate;
	String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\UpcomingBikesPage.properties";

	public UpcomingBikesPage(WebDriver driver, ExtentTest logger) {
		prop = ReadProperties.readFile(filePath);
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 60);
		// TODO Auto-generated constructor stub
	}

	public String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

	// To select "Upcoming Bikes" option through mouse hover
	public void mouseHover() throws InterruptedException {
		try {
			WebElement newBikes = driver.findElement(By.xpath(getPropertyValue("newBikes_xpath")));
			Actions builder = new Actions(driver);
			builder.moveToElement(newBikes).build().perform();

			setWaitByClick(getPropertyValue("upcomingBikes"));

			WebElement upcomingBikes = driver.findElement(By.xpath(getPropertyValue("upcomingBikes")));
			upcomingBikes.click();
			sleep(2000);

			logger.log(Status.INFO, "Opened Page Successfully");
			takeScreenShot("New_Upcoming_Bikes");

			Thread.sleep(3000);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	// To select "Honda" from the manufacturer dropdown list
	public void selectManufacturer() throws InterruptedException {

		try {
			WebElement sManufacturer = driver.findElement(By.id("makeId"));
			Select dropdown = new Select(sManufacturer);
			dropdown.selectByVisibleText("Honda");
			sleep(2000);
			logger.log(Status.INFO, "Select Honda Manufacturer Successfully");

			takeScreenShot("Select_Honda");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void viewMore() throws InterruptedException {

		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,1250)", "");

			sleep(2000);
			setWaitByPresence(getPropertyValue("viewMore_xpath"));
			driver.findElement(By.xpath(getPropertyValue("viewMore_xpath"))).click();

			logger.log(Status.INFO, "Clicked on View More Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void upcomingbikes() throws InterruptedException, NumberFormatException {

		try {
			List<WebElement> bikes = driver.findElements(By.xpath("//*[@id=\"modelList\"]/li"));
			List<WebElement> bikeprice = driver.findElements(By.xpath("//*[@id=\"modelList\"]/li/div/div[3]/div[1]"));
			List<WebElement> bikename = driver.findElements(By.xpath("//*[@id=\"modelList\"]/li/div/div[3]/a"));
			List<WebElement> bikelaunchdate = driver
					.findElements(By.xpath("//*[@id=\"modelList\"]/li/div/div[3]/div[2]"));
			int count = bikes.size();
			String[] data = new String[count];
			Double[] data1 = new Double[count];
			String[] data2 = new String[count];
			try {
				for (int i = 0; i < count - 1; i++) {

					WebElement bprice = bikeprice.get(i);
					String pricetext = bprice.getText();
					String ptext = pricetext.substring(4, 7);
					Price = Double.parseDouble(ptext);
					WebElement bname = bikename.get(i);
					Name = bname.getText();
					WebElement bdate = bikelaunchdate.get(i);
					String datetxt = bdate.getText();
					LaunchDate = datetxt.substring(14);
					// Printing Bike Details Less than 4 Lacs
					if (Price < 4.00) {
						System.out.println("Upcoming bike of Honda");
						System.out.println("Name:" + Name);
						data[i] = Name;
						System.out.println("Price: Rs " + Price + " Lakhs");
						data1[i] = Price;
						System.out.println("LaunchDate:" + LaunchDate);
						data2[i] = LaunchDate;
						System.out.println("================================================");

						logger.log(Status.INFO,
								"BikeName: " + Name + " BikePrice: RS." + Price + "Lakh Launch Data: " + LaunchDate);

						WebElement bikeDetails = driver.findElement(By.xpath("//*[@id=\"modelList\"]/li[14]"));

						String Details = bikeDetails.getText();
						Details.split(" ");

						WriteExcelSheet.writeUpcomingBikeData(Details);
					} else {
						System.out.println("Price is Greater than Four Lakh");
						System.out.println("================================================");
					}
					sleep(2000);
				}
			} catch (Exception e) {
				e.getMessage();
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}
}
