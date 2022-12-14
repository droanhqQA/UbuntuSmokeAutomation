package com.utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base_Class {

	
	
	
	public WebDriver driver;
	public ChromeOptions options;
	public WebDriverWait wait;
//	/UbuntuSmokeAutomation/src/main/resources/Browser/chromedriver.exe
	public void BrowserSetUp()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vinayak Hene\\git\\UbuntuSmokeAutomation\\src\\main\\resources\\Browser\\chromedriver.exe");
		driver=new ChromeDriver();
	   
		options = new ChromeOptions();
//		options.addArguments("headless");
		options.addArguments("window-size=1500,800");
		options.addArguments("incognito");
		options.addArguments("disable-infobars");
		options.setAcceptInsecureCerts(true);
		driver = new ChromeDriver(options);
		
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		driver.get("https://ubuntu.onprem.dronahq.com/apps");
	}
	
	
}
