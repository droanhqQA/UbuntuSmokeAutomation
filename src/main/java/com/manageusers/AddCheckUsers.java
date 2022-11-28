package com.manageusers;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dao.UserLogin;

public class AddCheckUsers {
	WebDriver driver;
	public AddCheckUsers(WebDriver driver) {
		super();
		this.driver = driver;
	}
	public String generateEmail()
	{
		String new_email ="test" + System.nanoTime() + "@studio.com";
		return new_email;
	}
	public String addCheckUser(String email)
	{
		String u_name = "brijesh@studio.com";
		String u_pass = "qwerty";
	
		UserLogin user = new UserLogin(driver);
		user.login(u_name,u_pass);
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='sidebar-options']/div[@data-header='Users']")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//*[@id='invite-user-button']")));
		Actions act = new Actions(driver);
		//Double click on element
		WebElement ele = driver.findElement(By.xpath("//*[@id='invite-user-button']")); 
		act.doubleClick(ele).perform();
	//	driver.findElement(By.xpath("//*[@id='invite-user-button']")).click();
		new WebDriverWait(driver, Duration.ofSeconds(180))
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='invite-u-name-ip form-control w-30']")));
		driver.findElement(By.xpath("//input[@class='invite-u-name-ip form-control w-30']")).sendKeys("Test");
		//String new_email="test" + System.nanoTime() + "@studio.com";
		String pass="Test@123";
		System.out.println(email);
//		emailSaver.setEmail(EmailAddress);
		driver.findElement(By.xpath("//input[@class='invite-u-email-ip form-control w-30']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@class='invite-u-password-ip form-control w-40 true ']")).sendKeys(pass);
		driver.findElement(By.id("selectUserRoleUserLst")).click();
		driver.findElement(By.xpath("//*[@id=\"selectUserRoleUserLst\"]/div[2]/div[1]")).click();
//		System.out.println(emailSaver.getEmail());
		driver.findElement(By.xpath("//div[@class='invite-user-actions']/div")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@class='search-user-input']")).sendKeys(email);
		new WebDriverWait(driver, Duration.ofSeconds(60))
		.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[text()='Apply Filters']"))));
		driver.findElement(By.xpath("//div[text()='Apply Filters']")).click();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String checkEmail="";
		List<WebElement> emailAfter = driver.findElements(By.className("u-m-email"));
	for (WebElement webElement : emailAfter) {
		checkEmail = webElement.getText();
		System.out.println("Inside ForEach: "+webElement.getText());
	}
	return checkEmail;
	}
}
