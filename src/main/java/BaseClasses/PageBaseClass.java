package BaseClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pageModels.NewBikesPage;

public class PageBaseClass  {

	protected static ExtentReports report=ExtentReportManager.getReportInstance();
	public static ExtentTest logger;
	public static WebDriver driver;

	
	
	/******************Invoke Browser*********************/
	
		
	public void invokeBrowser(String browserName) {
		
		if(browserName.equalsIgnoreCase("chrome")) {
			//Creating ChromeDriver
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
			ChromeOptions chrome = new ChromeOptions();
			chrome.addArguments("--disable-notifications");
			
			driver = new ChromeDriver(chrome);
			
		} else if(browserName.equalsIgnoreCase("mozilla")) {
			//Creating FirefoxDriver
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\Drivers\\geckodriver (1).exe");
			
			driver = new FirefoxDriver();
			
		} else if(browserName.equalsIgnoreCase("IE")) {
			//Setting up IE driver
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\Drivers\\IEDriverServer(1).exe");
			driver = new InternetExplorerDriver();
			
		} else if(browserName.equalsIgnoreCase("opera")) {
			//Setting up Opera Driver
			System.setProperty("webdriver.opera.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\operadriver.exe");
			driver = new OperaDriver();
			
		} else if(browserName.equalsIgnoreCase("safari")) {
			//Setting up Safari driver
			driver = new SafariDriver();
			
		}
		
	}
	

	/************************** To open URL ***************************/

	public NewBikesPage openURL(String websiteURL) {
		try {
			logger.log(Status.INFO, "Opening the WebSite");
			driver.get(websiteURL);
			takeScreenShot("WebsiteOpened");
			reportPass(websiteURL + " (Website) Identified Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reportFail(e.getMessage()); // Display the message if website is not opened.

		}
		// It directed on the SelectFurnitureAndBenches class
		NewBikesPage NP = new NewBikesPage(driver, logger);
		PageFactory.initElements(driver, NP);
		return NP;

	}

	// To maximize the browser
	public void maximizeBrowser() {
		driver.manage().window().maximize();
	}

	// To enter Text in a element
	public void enterText(String xpath, String text) {
		driver.findElement(By.xpath(xpath)).sendKeys(text);
	}

	// TO click on a given xpath
	public void clickXpath(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}
	
	public void sleep(int seconds)
	{
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// To set explicit wait based on visibility of the element
	public void setWaitByVisibility(String path) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
	}

	// To set explicit wait based on presence of the element
	public void setWaitByPresence(String upath) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(upath)));

	}

	// To set explicit wait based on clickability of the element
	public void setWaitByClick(String path) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(path)));

	}

	// To click an element using JS executor
	public static void clickByVisibleElement(String xpath) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Find element by link text and store in variable "Element"
		WebElement Element = driver.findElement(By.xpath(xpath));

		// This will scroll the page till the element is found
		js.executeScript("arguments[0].click()", Element);
	}

	// To set implicit wait
	public static void waitImplicit() {
		// Implicit wait of 15 seconds
		try {
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/************************** ScreenShots ***************************/
	public static void takeScreenShot(String fileName) {

		// TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourcefile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File desFile = new File(System.getProperty("user.dir") + "/Screenshots/" + fileName + ".png");

		try {
			FileUtils.copyFile(sourcefile, desFile);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "/Screenshots/" + fileName + ".png");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/************** Report Function *****************/

	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShot(reportString);
	}

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);

	}

	// To close the current window
	public void closeBrowser() {
		driver.close();
	}

	// To quit the driver
	public void quitBrowser() {
		driver.quit();
	}
	
	@AfterSuite
	public void tearDown() {
		//To close the report once the test suite execution is complete
		report.flush();
	}
}
