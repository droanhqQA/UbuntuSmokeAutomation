package com.permission;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dao.UserLogin;

public class ConnectorPermission {
	final String add_query="//*[text()='AutoTestMongo']/ancestor::div[contains(@class,'head')]/descendant::div[contains(@class,'add-query')]";
	final String option_menu="//*[text()='AutoTestMongo']/ancestor::div[contains(@class,'head')]/descendant::div[contains(@class,'option')]";
	final String edit_permission=option_menu+"/*[contains(@class,'edit-permission')]";
	final String saveBtn="//*[contains(@class,'save-permission')]";
	public void loginOtherUser(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofSeconds(30))
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='new-profile-icon']")));
		driver.findElement(By.xpath("//*[@class='new-profile-icon']")).click();
		driver.findElement(By.xpath("//*[@class='settings-option logout-button']")).click();
		driver.findElement(By.xpath("//*[contains(@class,'logout')]/descendant::div[@class='positive button']")).click();
		UserLogin user = new UserLogin(driver);
		user.login("sachin@studion.com", "qwerty");
		
	}
	void gotoConnector(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofSeconds(60))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-header='Connectors']/div/div/div[2]")));
		driver.findElement(By.xpath("//*[@data-header='Connectors']/div/div/div[2]")).click();
	}
	public ArrayList<String> changePermission(String permission,WebDriver driver)
	{
		gotoConnector(driver);
		driver.findElement(By.xpath(option_menu)).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath(edit_permission)).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		WebElement element=driver.findElement(By.xpath("//div[@class='user-table']/descendant::input[@id='"+permission+"sachin@studion.com']"));
		
		JavascriptExecutor js_exe = (JavascriptExecutor)driver;
		js_exe.executeScript("arguments[0].click();", element);
		driver.findElement(By.xpath(saveBtn)).click();
		loginOtherUser(driver);
		gotoConnector(driver);
		Boolean menu_opt=false;
		switch(permission)
		{
		case("useonly"):
			menu_opt=checkPreviewOnly(driver);
			
			break;
		case("none"):
			menu_opt=checkNone(driver);
			
			break;
			
		}
		
		ArrayList<String> perm = new ArrayList<String>();
		perm.add(0, menu_opt.toString());
		
		
		return perm;
	}
	

	private Boolean checkNone(WebDriver driver) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.xpath("//*[text()='AutoTestMongo']"));
			return false;
		}
		catch(NoSuchElementException e) {
			return true;
		}
		
	}

	private Boolean checkPreviewOnly(WebDriver driver) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.xpath(add_query));
			return false;
		}
		catch(NoSuchElementException e) {
			return true;
		}
		
	}
	
}
