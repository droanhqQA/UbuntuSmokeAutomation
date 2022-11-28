package com.dao;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserLogin {
	WebDriver driver;
	
 public UserLogin(WebDriver driver) {
		super();
		this.driver = driver;
	}

public void login(String u_name,String u_pass)
 {
	System.out.println("inside Login");
	 driver.findElement(By.id("email")).sendKeys(u_name);
		driver.findElement(By.id("continue-btn-email")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		 wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("password"))));
		 driver.findElement(By.id("password")).sendKeys(u_pass);
		driver.findElement(By.id("continue-btn-password")).click();
 }
}
