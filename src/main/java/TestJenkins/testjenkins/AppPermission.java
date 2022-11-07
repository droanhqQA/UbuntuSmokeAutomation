package TestJenkins.testjenkins;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




public class AppPermission {
	final String appOptions="//div[@data-appname='AutoApp']/descendant::div[@class='ui vertical menu card-menu']";
	final String editPermission="//div[@data-appname='AutoApp']/descendant::div[contains(@class,'edit-permission')]";
	final String saveBtn="//*[contains(@class,'save-permission')]";
	final String add_query="//*[text()='AutoTestMongo']/ancestor::div[contains(@class,'head')]/descendant::div[contains(@class,'add-query')]";
	final String option_menu="//*[text()='AutoTestMongo']/ancestor::div[contains(@class,'head')]/descendant::div[contains(@class,'option')]";
	final String edit_permission=option_menu+"/*[contains(@class,'edit-permission')]";
	
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
	public void waitMenu(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofSeconds(30))
		.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appOptions)));
	}
	public ArrayList<String> changePermission(String permission,WebDriver driver)
	{
		String menu_opt;
		String hf_value=menu_opt="";
		waitMenu(driver);
		driver.findElement(By.xpath(appOptions)).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath(editPermission)).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		WebElement element=driver.findElement(By.xpath("//div[@class='user-table']/descendant::input[@id='"+permission+"sachin@studion.com']"));
		
		JavascriptExecutor js_exe = (JavascriptExecutor)driver;
		js_exe.executeScript("arguments[0].click();", element);
		driver.findElement(By.xpath(saveBtn)).click();
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
	
	String checkEditor(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		waitMenu(driver);
		driver.findElement(By.xpath(appOptions)).click();
		List<WebElement> options = driver.findElements(By.xpath("//div[@data-appname='AutoApp']/descendant::div[@class='ui vertical menu card-menu']/div/div/div"));
		String menu_opt="";
		for (WebElement webElement : options) {
			System.out.println(webElement.getText());
			menu_opt+=" "+webElement.getText();
		}
		return menu_opt;
	}
	
	String checkPreviewOnly(WebDriver driver)
	{
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		String getClass=driver.findElement(By.xpath("//div[@data-appname='AutoApp']/descendant::div[contains(@class,'card-menu')]/i")).getAttribute("class");
		System.out.println(getClass);
		return getClass;
	}
	
	String checkNone(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.className("search-input")).sendKeys("AutoApp");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		String text =driver.findElement(By.xpath("//div[contains(@class,'no-apps-found')]/div")).getAttribute("innerHTML");
		System.out.println("text "+text);
		return text;
	}
	String gethfPermission(WebDriver driver)
	{
		driver.findElement(By.className("search-input")).sendKeys("Auto");
		driver.findElement(By.xpath("//div[@data-appname='AutoApp']/div[2]")).click();
		System.out.println("Clicked on app");
		new WebDriverWait(driver,Duration.ofMinutes(5))
		.until(ExpectedConditions.numberOfWindowsToBe(2));
		String winHandleBefore = driver.getWindowHandle();
		System.out.println(winHandleBefore.toString());
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("No. of tabs: " + tabs.size());
		driver.switchTo().window(tabs.get(1));
		System.out.println("New Window "+tabs.get(1));
	//	driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		new WebDriverWait(driver, Duration.ofSeconds(300))
		.until(ExpectedConditions.titleContains("App"));
		String title = driver.getTitle();
		System.out.println(title+" ");
		String hfPerm = driver.findElement(By.id("hfPermission")).getAttribute("value");
		System.out.println(hfPerm);
		return hfPerm;
	}
	
	void gotoConnector(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofSeconds(60))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-header='Connectors']/div/div/div[2]")));
		driver.findElement(By.xpath("//*[@data-header='Connectors']/div/div/div[2]")).click();
	}
	
	public ArrayList<String> changeConnPermission(String permission,WebDriver driver)
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
	

	 Boolean checkConnNone(WebDriver driver) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.xpath("//*[text()='AutoTestMongo']"));
			return false;
		}
		catch(NoSuchElementException e) {
			return true;
		}
		
	}

	 Boolean checkUseOnly(WebDriver driver) {
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
