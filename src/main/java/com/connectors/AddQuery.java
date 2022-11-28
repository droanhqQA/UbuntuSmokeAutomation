package com.connectors;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AddQuery {
	
	
	XSSFWorkbook workbook;
	XSSFSheet con_sheet;
	
	public AddQuery(FileInputStream fs,int sheetNo) throws IOException
	{
		workbook =new XSSFWorkbook(fs);
		con_sheet = workbook.getSheetAt(sheetNo);
	}
	
	public void mongoQuery(WebDriver driver)
	{
		String Qpath= "//*[text()='AutoTestMongo']/ancestor-or-self::div[@class='table-row']/div/div[4]/div";
		System.out.println(Qpath);
		
		new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.xpath(Qpath)));
		driver.findElement(By.xpath(Qpath)).click();//AddQuery
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("query_name")).sendKeys("TestQ");//QueryName
		
		
		driver.findElement(By.id("limit")).sendKeys("1");//Limit
		driver.findElement(By.xpath("//*[@class='hq-link-btn d-flex align-center mr-3 pointer testquery']")).click();//Test Query
	}
	public void postgreQuery(WebDriver driver) throws InterruptedException
	{
		String Qpath= "//*[text()='AutoTestPostgre']/ancestor-or-self::div[@class='table-row']/div/div[4]/div";
		System.out.println(Qpath);
		new WebDriverWait(driver,Duration.ofSeconds(20))
		.until(ExpectedConditions.elementToBeClickable(By.xpath(Qpath)));
		driver.findElement(By.xpath(Qpath)).click();//AddQuery
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("query_name")).sendKeys("TestQ");//QueryName

		((JavascriptExecutor)driver)
		.executeScript("var evt = {type: 'keyup',keyCode: 32 } ;let CodeMirrorInstance = document.querySelector('.major-section #query_builder_container .CodeMirror-wrap').CodeMirror;CodeMirrorInstance.setValue('SELECT rolname,rolsuper,rolbypassrls,oid FROM pg_roles limit 1');CodeMirrorInstance.triggerOnKeyUp(evt)");
		
		
		driver.findElement(By.xpath("//*[@class='hq-link-btn d-flex align-center mr-3 pointer testquery']")).click();//Test Query


	}
	
	
	
}
