package pageModels;

import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import BaseClasses.PageBaseClass;
import BaseClasses.ReadProperties;

public class LoginPage extends PageBaseClass {

	Properties prop;
	Actions action;
	WebDriverWait wait;
	String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\LoginPage.properties";

	public LoginPage(WebDriver driver, ExtentTest logger) {
		prop = ReadProperties.readFile(filePath);
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 60);
		// TODO Auto-generated constructor stub
	}

	public String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

	public String parentHandle = "";

	public void openLoginWindow() throws InterruptedException {
		// To click on the login button
		try {
			clickXpath(getPropertyValue("login_xpath"));
			sleep(3000);
			takeScreenShot("Login_Google");

			// To click on the login with google/facebook button
			clickXpath(getPropertyValue("google_xpath"));
			waitImplicit();

			// To switch to child window
			Set<String> windowHandles = driver.getWindowHandles();
			for (String windowHandle : windowHandles) {
				if (!(windowHandle.equals(parentHandle))) {
					driver.switchTo().window(windowHandle);
				}
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void enterCredentials(String email) throws InterruptedException {
		// To enter login credentials in google window
		try {
			enterText(getPropertyValue("emailId_xpath"), email);
			Thread.sleep(3000);
			takeScreenShot("Login_error");

			clickXpath(getPropertyValue("next_btn"));
			takeScreenShot("Login_error1");

			String parentWindowHandle = driver.getWindowHandle();
			driver.switchTo().window(parentWindowHandle);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void enterCredentials2(String email) throws InterruptedException {
		try {
			driver.findElement(By.xpath(getPropertyValue("emailId_xpath"))).clear();
			// To enter login credentials in google window
			enterText(getPropertyValue("emailId_xpath"), email);
			Thread.sleep(3000);
			takeScreenShot("Login_error");

			clickXpath(getPropertyValue("next_btn"));
			takeScreenShot("Login_error2");

			String parentWindowHandle = driver.getWindowHandle();
			driver.switchTo().window(parentWindowHandle);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
}
