package com.permission;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GroupPermission extends AppPermission{

	
	final String manage_Users="//div[@data-header='Users']";
	final String manage_Groups="//div[@class='add-new-btn open-add-new-group-screen']";
	final String edit_options="//span[@class='open-dp']";
	final String edit_permission="//div[@data-type='editGroupPermission']";
	final String search_box="//*[@id='per-search']";
	final String save_btn="//*[contains(@class,'save-permission')]";
	final String editor_chk="//*[contains(text(),'AutoApp')]/ancestor::div[@class='seven-grid m-3 mt-0 mb-0']/div[3]/div/input";
	final String preview_chk="//*[contains(text(),'AutoApp')]/ancestor::div[@class='seven-grid m-3 mt-0 mb-0']/div[4]/div/input";
	final String none_chk="//*[contains(text(),'AutoApp')]/ancestor::div[@class='seven-grid m-3 mt-0 mb-0']/div[5]/div/input";
	final String connector_tab="//label[contains(@class,'connectors')]";
	final String conn_useonly="//div[@data-name='AutoTestMongo']/descendant::input[contains(@id,'useonly')]";
	final String conn_none="//div[@data-name='AutoTestMongo']/descendant::input[contains(@id,'none')]";	
	
	void gotoPermission(WebDriver driver,String type)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(By.xpath(manage_Users)).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement mng_groups = driver.findElement(By.xpath(manage_Groups));
		useJavaScript(mng_groups, driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath(edit_options)).click();
		driver.findElement(By.xpath(edit_permission)).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		if(type.equals("app"))
		{
		driver.findElement(By.xpath(search_box)).sendKeys("AutoApp");
		}
		else
		{
		driver.findElement(By.xpath(connector_tab)).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath(search_box)).sendKeys("AutoTestMongo");
		
		}
	}
	void clickSave(WebDriver driver)
	{
		driver.findElement(By.xpath(save_btn)).click();
	}
	public void setEditor(WebDriver driver)
	{
		gotoPermission(driver,"app");
		WebElement editor = driver.findElement(By.xpath(editor_chk));
		useJavaScript(editor, driver);
		clickSave(driver);
	}
	public void setPreview(WebDriver driver)
	{
		gotoPermission(driver,"app");
		WebElement preview = driver.findElement(By.xpath(preview_chk));
		useJavaScript(preview, driver);
		clickSave(driver);
	}
	public void setNone(WebDriver driver)
	{
		gotoPermission(driver,"app");
		WebElement none =driver.findElement(By.xpath(none_chk));
		useJavaScript(none, driver);
		clickSave(driver);
	}
	public void setConnUseOnly(WebDriver driver)
	{
		gotoPermission(driver, "conn");
		WebElement useonly = driver.findElement(By.xpath(conn_useonly));
		useJavaScript(useonly, driver);
		clickSave(driver);
	}
	
	public void setConnNone(WebDriver driver)
	{
		gotoPermission(driver, "conn");
		WebElement useonly = driver.findElement(By.xpath(conn_none));
		useJavaScript(useonly, driver);
		clickSave(driver);
	}
	
	public ArrayList<String> changePermission(String permission,WebDriver driver)
	{
		String menu_opt;
		String hf_value=menu_opt="";
		loginOtherUser(driver);
		switch(permission)
		{
		case("editor"):
			menu_opt=checkEditor(driver);
			hf_value=gethfPermission(driver);
			break;
		case("useonly"):
			menu_opt=checkPreviewOnly(driver);
			hf_value=gethfPermission(driver);
			break;
		case("none"):
			menu_opt=checkNone(driver);
			hf_value="0000";
			break;
			
		}
		
		ArrayList<String> perm = new ArrayList<String>();
		perm.add(0, menu_opt);
		perm.add(1, hf_value);
		
		return perm;
	}
	public ArrayList<String> changeConnPermission(String permission,WebDriver driver)
	{
		loginOtherUser(driver);
		gotoConnector(driver);
		Boolean menu_opt=false;
		switch(permission)
		{
		case("useonly"):
			menu_opt=checkUseOnly(driver);
			
			break;
		case("none"):
			menu_opt=checkConnNone(driver);
			
			break;
			
		}
		
		ArrayList<String> perm = new ArrayList<String>();
		perm.add(0, menu_opt.toString());
		
		
		return perm;
	}
	

	void useJavaScript(WebElement element,WebDriver driver)
	{
		JavascriptExecutor js_exe = (JavascriptExecutor)driver;
		js_exe.executeScript("arguments[0].click();", element);
	}
}
