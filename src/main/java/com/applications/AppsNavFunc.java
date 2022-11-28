package com.applications;

import java.net.URL;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utils.GetTimeouts;


public class AppsNavFunc {
	WebDriver driver;
	Properties prop;
	WebDriverWait explicitwait ;
	By tablegrid = By.xpath("//div[@class='page-body-box']/div[2]/div/div[1]");
	Long max_time,min_time,avg_time;
	public AppsNavFunc(WebDriver driver) {
		super();
		this.driver = driver;
		explicitwait =  new WebDriverWait(driver, Duration.ofMinutes(5));
		GetTimeouts timeouts = new GetTimeouts();
		max_time = timeouts.getMax_time();
		min_time = timeouts.getMin_time();
		avg_time = timeouts.getAvg_time();
	}
	public void getImplicit()
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(5));
	}

	public void createApp(WebDriver driver)
	{
		driver.findElement(By.className("add-microapp-card-image")).click();
		driver.findElement(By.xpath("//div[@class='showcase-new-app'][@data-type='blank']")).click();
		driver.findElement(By.xpath("//input[@class='app-name']")).sendKeys("AutoApp");
		driver.findElement(By.xpath("//textarea[@class='app-desc']")).sendKeys("Description");
		driver.findElement(By.xpath("//*[@id=\"icons-suggestion-list-container\"]/div[3]")).click();
		driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]")).click();
		driver.findElement(By.xpath("//button[@class='sideshow-next-step-button']")).click();
			}
	
	public void addControl(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
		new WebDriverWait(driver,Duration.ofMinutes(2))
		.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='sideshow-mask-part sideshow-invisible']")));
		new WebDriverWait(driver, Duration.ofMinutes(2))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='btnlbl' and text()='Controls']")));
		driver.findElement(By.xpath("//div[@class='btnlbl' and text()='Controls']")).click();//controls-tab
		driver.findElement(By.xpath("//input[@class='search-controls-input']")).sendKeys("table");
	    driver.findElement(By.xpath("//*[@data-popup_doc_id='Table Grid']/parent::a")).click();
	    							 ///html/body/div[4]/div[15]/div[2]/div[1]/div/div[2]/div[1]/div[4]/div[1]/div[1]/div/div[2]/div/a[2]
	    driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	    driver.findElement(By.xpath("//div[@class='page-body-box']/div[2]")).click();//click tablegrid 
	}
	public void gotoApp(WebDriver driver)
	{
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
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
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(By.xpath("//button[@class='sideshow-next-step-button']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		WebElement skipBtn = driver.findElement(By.xpath("//div[@class='skip-button']"));
		new WebDriverWait(driver, Duration.ofSeconds(60))
		.until(ExpectedConditions.elementToBeClickable(skipBtn));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", skipBtn);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addMongo(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
		explicitwait.until(ExpectedConditions.elementToBeClickable(
				tablegrid));

		driver.findElement(tablegrid).click();
		explicitwait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='top-prop-tabs']/a[2]")));
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		driver.findElement(By.xpath("//*[@data-type='bindapi']/div/span")).click();//click connectors
		//Finding the remove connector btn

		driver.manage().timeouts().implicitlyWait(min_time,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='selected-connector-infor-wrap']")).click();//add-conectors
		driver.findElement(By.xpath("//input[@class='search-service']")).sendKeys("Auto");
		System.out.println("Finding Connectors");
		driver.findElement(By.xpath("//div[@title='AutoTestMongo']")).click(); //connectors finding
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		System.out.println("Clicked on Mongo");
		driver.findElement(By.xpath("//div[@class='list pt-2 vertical']/div")).click();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		System.out.println("Selected Query");
		driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[3]/div[2]/a")).click();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);

	}
	public void addPostgre(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
		explicitwait.until(ExpectedConditions.elementToBeClickable(
				tablegrid));
		driver.findElement(tablegrid).click();//click tablegrid
		driver.findElement(By.xpath("//a[@data-tab='delete']")).click();
		System.out.println("Table grid deleted");
		addControl(driver);
		
		explicitwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='page-body-box']/div[2]/div/div[1]")));
		driver.findElement(tablegrid).click();//click tablegrid
		getImplicit();
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		getImplicit();
		//driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		driver.findElement(By.xpath("//*[@data-type='bindapi']/div/span")).click();//click connectors
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='selected-connector-infor-wrap']")).click();//add-conectors
		driver.findElement(By.xpath("//input[@class='search-service']")).sendKeys("Auto");
		System.out.println("Finding Connectors");
		driver.findElement(By.xpath("//div[@title='AutoTestPostgre']")).click(); //connectors finding
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		System.out.println("Clicked on Postgre");
		driver.findElement(By.xpath("//div[@class='list pt-2 vertical']/div")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		System.out.println("Selected Query");
		driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[3]/div[2]/a")).click();
	
		System.out.println("Clicked on Continue");

	}
	public void addRestApi(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
		explicitwait.until(ExpectedConditions.elementToBeClickable(
				tablegrid));
		driver.findElement(tablegrid).click();//click tablegrid
		driver.findElement(By.xpath("//a[@data-tab='delete']")).click();
		System.out.println("Table grid deleted");
		addControl(driver);
		
		explicitwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='page-body-box']/div[2]/div/div[1]")));
		driver.findElement(tablegrid).click();//click tablegrid
		getImplicit();
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		getImplicit();
		//driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		driver.findElement(By.xpath("//*[@data-type='bindapi']/div/span")).click();//click connectors
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='selected-connector-infor-wrap']")).click();//add-conectors
		driver.findElement(By.xpath("//input[@class='search-service']")).sendKeys("Auto");
		System.out.println("Finding Connectors");
		getImplicit();
		driver.findElement(By.xpath("//div[@title='AutoApi']")).click(); //connectors finding
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='list pt-2 vertical']/div")).click();//First Api Query
		driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer footer-action config-service']")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		System.out.println("Clicked on Continue");
		driver.findElement(By.xpath("//div[@class='sel-keys-dp-wrap d-flex f-column']/div[1]")).click();//dropList
		driver.findElement(By.xpath("//div[@title='records.id']")).click();
		driver.findElement(By.xpath("//div[@title='records.fields']")).click();
		driver.findElement(By.xpath("//div[@title='records.fields.Name']")).click();
		driver.findElement(By.xpath("//div[@title='records.fields.Status']")).click();
	}
	
	public void addGraphQL(WebDriver driver)
	{
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
		explicitwait.until(ExpectedConditions.elementToBeClickable(
				tablegrid));
		driver.findElement(tablegrid).click();//click tablegrid
		driver.findElement(By.xpath("//a[@data-tab='delete']")).click();
		System.out.println("Table grid deleted");
		addControl(driver);
		
		explicitwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='page-body-box']/div[2]/div/div[1]")));
		driver.findElement(tablegrid).click();//click tablegrid
		getImplicit();
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		getImplicit();
		//driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[2]")).click();//click on datatab(right)
		driver.findElement(By.xpath("//*[@data-type='bindapi']/div/span")).click();//click connectors
		getImplicit();
		driver.findElement(By.xpath("//div[@class='selected-connector-infor-wrap']")).click();//add-conectors
		driver.findElement(By.xpath("//input[@class='search-service']")).sendKeys("Auto");
		System.out.println("Finding Connectors");
		driver.findElement(By.xpath("//div[@title='AutoGraphQL']")).click(); //connectors finding
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='list pt-2 vertical']/div")).click();//First Api Query
		driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer footer-action config-service']")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		System.out.println("Clicked on Continue");
		driver.findElement(By.xpath("//div[@class='sel-keys-dp-wrap d-flex f-column']/div[1]")).click();//dropList
		driver.findElement(By.xpath("//div[@title='data.country.name']")).click();
		driver.findElement(By.xpath("//div[@title='data.country.native']")).click();
		driver.findElement(By.xpath("//div[@title='data.country.currency']")).click();
		driver.findElement(By.xpath("//div[@title='data.country.languages.name']")).click();
		
		
	}
	public String getResultMsg(WebDriver driver)
	{
		driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer footer-action finish-service-config']")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='ui simple button fml-actn-button save-bindapi-formula']")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		WebElement msgElement = driver.findElement(By.xpath("//*[@data-type='bindapi']/div[@class='ui form opt-body']/div/div[3]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", msgElement);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		msgElement.click();
		String resMsg = msgElement.getText();
		System.out.println(resMsg);
		return resMsg;
	}
	public String buttonFlow(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='page-body-box']/div[2]")).click();//click tablegrid 
		driver.findElement(By.xpath("//a[@data-tab='delete']")).click();
		driver.findElement(By.xpath("//*[@data-tooltip='Desktop view']")).click();
		driver.findElement(By.xpath("//div[@class='top-butttons']/div[2]")).click();//controls-tab
		driver.findElement(By.xpath("//input[@class='search-controls-input']")).sendKeys("bu");
		driver.findElement(By.xpath("/html/body/div[4]/div[15]/div[2]/div[1]/div/div[2]/div[1]/div[4]/div[1]/div[1]/div/div[2]/div/a[9]")).click();
		driver.findElement(By.xpath("//div[@class='top-butttons']/div[2]")).click();//controls-tab
		
		//going in action
		driver.findElement(By.xpath("//div[@class='control-button filled-theme']")).click();
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[3]")).click();
		driver.findElement(By.xpath("//div[@class='flex-center action-discription']")).click();
		System.out.println("Inside Action");
		driver.findElement(By.xpath("//*[@id=\"fbContainer\"]/div[2]/div[11]/div/div[2]/div/div[1]/div/div[2]/i")).click();
		driver.findElement(By.xpath("//label[@for='hq-tabs-B']")).click();
		//adding google sheet
		
		//input[@type='search']
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys("Sheets");
		driver.findElement(By.xpath("//div[@title='Google Sheets']")).click();
		System.out.println("Inside Google sheet");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='list vertical']/div[4]")).click();
		driver.findElement(By.xpath("//*[@value='production']/parent::div/div/*[text()='Account']")).click();
		driver.findElement(By.xpath("//div[@class='menu transition visible']/*[text()='AutoSheet']")).click();
		driver.findElement(By.xpath("//a[@class='icon footer-action config-service hq-primary-btn d-flex align-center pointer']")).click();//continue btn
		//driver.findElement(By.xpath("//*[@id='fbContainer']/div[2]/div[11]/div/div[2]/div[1]/div[6]/div[2]/div[3]/div[2]/div[4]/div[2]/div[1]")).click();
		System.out.println("Select from drop down");
		//selecting acc
		driver.findElement(By.xpath("//div[@data-key='param.range']")).click();
		driver.findElement(By.xpath("//*[@data-label='Select Spreadsheet']/parent::div/div")).click();
		driver.findElement(By.xpath("//span[text()='AutoSheet']//parent::div")).click();
		driver.findElement(By.xpath("//input[@data-key='param.sheetName']")).sendKeys("Sheet1");
		driver.findElement(By.xpath("//a[@class='hq-primary-btn footer-action service-usage d-flex align-center pointer']")).click();//continue btn
		driver.findElement(By.xpath("//a[@class=' icon footer-action finish-service-config hq-primary-btn d-flex align-center pointer']")).click();//finish btn
		
		//adding success popup
		
		driver.findElement(By.xpath("//div[@data-type='CALLSERVICE']/div[3]/div[2]/i")).click();
		
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys("popup");
		driver.findElement(By.xpath("//div[@title='To show alert message']")).click();
		driver.findElement(By.xpath("//input[@data-key='message']")).clear();
		driver.findElement(By.xpath("//input[@data-key='message']")).sendKeys("Google Sheet connected");
		driver.findElement(By.xpath("//a[@class='hq-primary-btn footer-action service-usage d-flex align-center pointer']")).click();//continue btn
		driver.findElement(By.xpath("//a[@class=' icon footer-action finish-service-config hq-primary-btn d-flex align-center pointer']")).click();//finish btn
		
		//adding failure popup
		driver.findElement(By.xpath("//div[@data-type='CALLSERVICE']/div[1]/div[2]/i")).click();
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys("popup");
		driver.findElement(By.xpath("//div[@title='To show alert message']")).click();
		driver.findElement(By.xpath("//div[@class='ui input revamped']/div/div/input[2]")).click();
		driver.findElement(By.xpath("//div[@data-value='error']")).click();
		driver.findElement(By.xpath("//input[@class='api-dynamic-fields   form-control mt-2']")).clear();
		driver.findElement(By.xpath("//input[@class='api-dynamic-fields   form-control mt-2']")).sendKeys("Error");
		driver.findElement(By.xpath("//input[@data-key='message']")).clear();
		driver.findElement(By.xpath("//input[@data-key='message']")).sendKeys("Google Sheet not connected");
		driver.findElement(By.xpath("//input[@data-key='confirmButtonText']")).clear();
		driver.findElement(By.xpath("//input[@data-key='confirmButtonText']")).sendKeys("Google Sheet not connected");
		driver.findElement(By.xpath("//a[@class='hq-primary-btn footer-action service-usage d-flex align-center pointer']")).click();//continue btn
		driver.findElement(By.xpath("//a[@class=' icon footer-action finish-service-config hq-primary-btn d-flex align-center pointer']")).click();//finish btn
		
		driver.findElement(By.xpath("/html/body/div[4]/div[15]/div[2]/div[11]/div/div[1]/div[2]/button[3]")).click();
		//button click complete

		checkPreview(driver);
		driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[2]/div[1]/div[2]/div/div/div[2]/div/div/div[3]/div")).click();
		String msg = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();
		System.out.println(msg);
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("/html/body/div[7]/div/div[3]/button[1]")).click();
		//driver.findElement(By.xpath("//*[@class='cross']")).click();
		return msg;
	}
	public String buttonFlowOAuth2(WebDriver driver)
	{
		gotoApp(driver);
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
		explicitwait.until(ExpectedConditions.elementToBeClickable(
				tablegrid));
		driver.findElement(tablegrid).click();//click tablegrid
		driver.findElement(By.xpath("//a[@data-tab='delete']")).click();
		System.out.println("Table grid deleted");
		getImplicit();
		driver.findElement(By.xpath("//div[@class='top-butttons']/div[2]")).click();//controls-tab
		driver.findElement(By.xpath("//input[@class='search-controls-input']")).sendKeys("bu");
		driver.findElement(By.xpath("//*[@data-field-type=\"Button1\"]/span/span[1]")).click();
		driver.findElement(By.xpath("//div[@class='top-butttons']/div[2]")).click();//controls-tab
		
		//going in action
		driver.findElement(By.xpath("//div[@class='control-button filled-theme']")).click();
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[3]")).click();
		driver.findElement(By.xpath("//div[@class='flex-center action-discription']")).click();
		System.out.println("Inside Action");
		driver.findElement(By.xpath("//*[@id=\"fbContainer\"]/div[2]/div[11]/div/div[2]/div/div[1]/div/div[2]/i")).click();
		driver.findElement(By.xpath("//label[@for='hq-tabs-B']")).click();
		//adding google sheet
		
		//input[@type='search']
		//driver.findElement(By.xpath("//input[@type='search']")).sendKeys("Sheets");
		driver.findElement(By.xpath("//div[@title='AutoApiOAuth2']")).click();
		System.out.println("Inside APi");
		getImplicit();
		driver.findElement(By.xpath("//div[@class='item connector-item']/div[1]")).click();
		driver.findElement(By.xpath("//*[@id='fbContainer']/div[2]/div[11]/div/div[2]/div[1]/div[6]/div[3]/div/a[2]")).click();//continue btn
		driver.findElement(By.xpath("//*[@id='fbContainer']/div[2]/div[11]/div/div[2]/div[1]/div[7]/div[5]/div/a[2]")).click();//continue btn
		driver.findElement(By.xpath("//*[@id='fbContainer']/div[2]/div[11]/div/div[2]/div[1]/div[9]/div[3]/div/a[2]")).click();
		
		
		//adding success popup
		
		driver.findElement(By.xpath("//div[@data-type='CALLSERVICE']/div[3]/div[2]/i")).click();
		
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys("popup");
		driver.findElement(By.xpath("//div[@title='To show alert message']")).click();
//		driver.findElement(By.xpath("//input[@data-key='message']")).clear();
//		driver.findElement(By.xpath("//input[@data-key='message']")).sendKeys("Spotify Api connected");
		((JavascriptExecutor)driver)
		.executeScript("document.querySelector('.JSEditor-wrapper .CodeMirror').CodeMirror.setValue('Spotify Api connected');");
		driver.findElement(By.xpath("//a[@class='hq-primary-btn footer-action service-usage d-flex align-center pointer']")).click();//continue btn
		driver.findElement(By.xpath("//a[@class=' icon footer-action finish-service-config hq-primary-btn d-flex align-center pointer']")).click();//finish btn
		
		//adding failure popup
		driver.findElement(By.xpath("//div[@data-type='CALLSERVICE']/div[1]/div[2]/i")).click();
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys("popup");
		driver.findElement(By.xpath("//div[@title='To show alert message']")).click();
		driver.findElement(By.xpath("//div[@class='ui input revamped']/div/div/input[2]")).click();
		driver.findElement(By.xpath("//div[@data-value='error']")).click();
		driver.findElement(By.xpath("//input[@class='api-dynamic-fields   form-control mt-2']")).clear();
		driver.findElement(By.xpath("//input[@class='api-dynamic-fields   form-control mt-2']")).sendKeys("Error");
//		driver.findElement(By.xpath("//input[@data-key='message']")).clear();
//		driver.findElement(By.xpath("//input[@data-key='message']")).sendKeys("Spotify Api not connected");
		((JavascriptExecutor)driver)
		.executeScript("document.querySelector('.JSEditor-wrapper .CodeMirror').CodeMirror.setValue('Spotify Api not connected');"
				+ "document.querySelectorAll('.JSEditor-wrapper .CodeMirror')[1].CodeMirror.setValue('OK');");
//		driver.findElement(By.xpath("//input[@data-key='confirmButtonText']")).clear();
//		driver.findElement(By.xpath("//input[@data-key='confirmButtonText']")).sendKeys("not connected");
		driver.findElement(By.xpath("//a[@class='hq-primary-btn footer-action service-usage d-flex align-center pointer']")).click();//continue btn
		driver.findElement(By.xpath("//a[@class=' icon footer-action finish-service-config hq-primary-btn d-flex align-center pointer']")).click();//finish btn
		
		driver.findElement(By.xpath("//*[@id='fbContainer']/div[2]/div[11]/div/div[1]/div[2]/button[3]")).click();
		//button click complete
		waitForSave();
		checkPreview(driver);
		driver.findElement(By.xpath("//*[contains(@class,'button control-button')]")).click();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();
		System.out.println(msg);
		//driver.findElement(By.xpath("//*[@class='swal2-actions']/button[1]")).click();
		//driver.findElement(By.xpath("//*[@class='cross']")).click();
		return msg;
	}
	public String buttonFlowOAuth1(WebDriver driver)
	{
		gotoApp(driver);
		new WebDriverWait(driver, Duration.ofMinutes(5))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-tooltip='Desktop view']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", 
				driver.findElement(By.xpath("//div[@data-tooltip='Desktop view']")));
		explicitwait.until(ExpectedConditions.elementToBeClickable(
				tablegrid));
		driver.findElement(tablegrid).click();//click tablegrid
		driver.findElement(By.xpath("//a[@data-tab='delete']")).click();
		System.out.println("Table grid deleted");
		getImplicit();
		driver.findElement(By.xpath("//div[@class='top-butttons']/div[2]")).click();//controls-tab
		driver.findElement(By.xpath("//input[@class='search-controls-input']")).sendKeys("bu");
		driver.findElement(By.xpath("//*[@data-field-type=\"Button1\"]/span/span[1]")).click();
		driver.findElement(By.xpath("//div[@class='top-butttons']/div[2]")).click();//controls-tab
		
		//going in action
		driver.findElement(By.xpath("//div[@class='control-button filled-theme']")).click();
		driver.findElement(By.xpath("//div[@class='top-prop-tabs']/a[3]")).click();
		driver.findElement(By.xpath("//div[@class='flex-center action-discription']")).click();
		System.out.println("Inside Action");
		driver.findElement(By.xpath("//*[@id=\"fbContainer\"]/div[2]/div[11]/div/div[2]/div/div[1]/div/div[2]/i")).click();
		driver.findElement(By.xpath("//label[@for='hq-tabs-B']")).click();
		//adding google sheet
		
		//input[@type='search']
		//driver.findElement(By.xpath("//input[@type='search']")).sendKeys("Sheets");
		driver.findElement(By.xpath("//div[@title='AutoApiOAuth1']")).click();
		System.out.println("Inside APi");
		getImplicit();
		driver.findElement(By.xpath("//div[@class='item connector-item']/div[1]")).click();
		driver.findElement(By.xpath("//*[@id='fbContainer']/div[2]/div[11]/div/div[2]/div[1]/div[6]/div[3]/div/a[2]")).click();//continue btn
		driver.findElement(By.xpath("//*[@id='fbContainer']/div[2]/div[11]/div/div[2]/div[1]/div[7]/div[5]/div/a[2]")).click();//continue btn
		driver.findElement(By.xpath("//*[@id='fbContainer']/div[2]/div[11]/div/div[2]/div[1]/div[9]/div[3]/div/a[2]")).click();
		
		
		//adding success popup
		
		driver.findElement(By.xpath("//div[@data-type='CALLSERVICE']/div[3]/div[2]/i")).click();
		
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys("popup");
		driver.findElement(By.xpath("//div[@title='To show alert message']")).click();
		((JavascriptExecutor)driver)
		.executeScript("document.querySelector('.JSEditor-wrapper .CodeMirror').CodeMirror.setValue('Twitter Api connected');");
		driver.findElement(By.xpath("//a[@class='hq-primary-btn footer-action service-usage d-flex align-center pointer']")).click();//continue btn
		driver.findElement(By.xpath("//a[@class=' icon footer-action finish-service-config hq-primary-btn d-flex align-center pointer']")).click();//finish btn
		
		//adding failure popup
		driver.findElement(By.xpath("//div[@data-type='CALLSERVICE']/div[1]/div[2]/i")).click();
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys("popup");
		driver.findElement(By.xpath("//div[@title='To show alert message']")).click();
		driver.findElement(By.xpath("//div[@class='ui input revamped']/div/div/input[2]")).click();
		driver.findElement(By.xpath("//div[@data-value='error']")).click();
		driver.findElement(By.xpath("//input[@class='api-dynamic-fields   form-control mt-2']")).clear();
		driver.findElement(By.xpath("//input[@class='api-dynamic-fields   form-control mt-2']")).sendKeys("Error");
//		driver.findElement(By.xpath("//input[@data-key='message']")).clear();
//		driver.findElement(By.xpath("//input[@data-key='message']")).sendKeys("Spotify Api not connected");
		((JavascriptExecutor)driver)
		.executeScript("document.querySelector('.JSEditor-wrapper .CodeMirror').CodeMirror.setValue('Twitter Api not connected');"
				+ "document.querySelectorAll('.JSEditor-wrapper .CodeMirror')[1].CodeMirror.setValue('OK');");
//		driver.findElement(By.xpath("//input[@data-key='confirmButtonText']")).clear();
//		driver.findElement(By.xpath("//input[@data-key='confirmButtonText']")).sendKeys("not connected");
		driver.findElement(By.xpath("//a[@class='hq-primary-btn footer-action service-usage d-flex align-center pointer']")).click();//continue btn
		driver.findElement(By.xpath("//a[@class=' icon footer-action finish-service-config hq-primary-btn d-flex align-center pointer']")).click();//finish btn
		
		driver.findElement(By.xpath("//*[@id='fbContainer']/div[2]/div[11]/div/div[1]/div[2]/button[3]")).click();
		//button click complete
		waitForSave();
		checkPreview(driver);
		driver.findElement(By.xpath("//*[contains(@class,'button control-button')]")).click();
		

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();
		System.out.println(msg);
		//driver.findElement(By.xpath("//*[@class='swal2-actions']/button[1]")).click();
		//driver.findElement(By.xpath("//*[@class='cross']")).click();
		return msg;
	}
	public static ExpectedCondition<Boolean> waitForSave() {
		  System.out.println("Wait for application to load entirely");
	    return new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        return (Boolean) ((JavascriptExecutor) driver)
	          .executeScript("return document.querySelector('.js-save-form').disabled");
	      }
	    };
	  }
	public String checkPreview(WebDriver driver)
	{
		driver.findElement(By.xpath("//button[contains(@class,'preview-page')]")).click();//preview btn
		
			try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		String text = driver.findElement(By.xpath("/html/head/title")).getAttribute("innerHTML");
		System.out.println("Frame Title: "+text);
		List<WebElement> iframeElements = driver.findElements(By.tagName("iframe"));
		System.out.println("The total number of iframes are " + iframeElements.size());
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='previewFrame']")));
	
		
		String title = driver.findElement(By.xpath("/html/head/title")).getAttribute("innerHTML");
		System.out.println("Frame Title: "+title.trim());
		return title;
	}
	public String checkPublish(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='fbContainer']/div[1]/div[2]/div[3]/div[5]/div[1]/i")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.id("exportAndPublish")).click();
		driver.findElement(By.className("release-notes")).sendKeys("Automation Test");
		driver.findElement(By.id("publishDhqPackage")).click();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='fbContainer']/div[1]/div[2]/div[3]/div[5]/div[1]/i")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.id("exportAndPublish")).click();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String msg = jsExecutor.executeScript("return document.getElementsByClassName('ui floated right red respMessage hide')[0].innerHTML;").toString();
		System.out.println(msg);
		return msg;
	}
	public String AppImport(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		final URL resource = AppsNavFunc.class.getResource("/ExportAuto.json");
	       System.out.println(resource);
		
		String exportApp_path = ((resource.toString().substring("file:/".length(),resource.toString().length())));
		driver.findElement(By.className("add-microapp-card-image")).click();
		driver.findElement(By.xpath("//*[@data-type='upload']")).click();
		driver.findElement(By.xpath("//*[@id='json-file-import']"))
		.sendKeys(exportApp_path);
		driver.findElement(By.xpath("//*[@class='hq-primary-btn d-flex align-center pointer upload-app-btn']")).click();
		driver.findElement(By.xpath("//*[@class='hq-primary-btn d-flex align-center pointer final-install-template']")).click();
		String success = driver.findElement(By.xpath("//*[@class='event-head']")).getText();
		System.out.println(success);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		WebElement openApp= driver.findElement(By.xpath("//*[@class='event-button-click']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", openApp);
		System.out.println("Waiting for application");
		new WebDriverWait(driver,Duration.ofMinutes(5))
		.until(ExpectedConditions.numberOfWindowsToBe(2));
		String winHandleBefore = driver.getWindowHandle();
		System.out.println(winHandleBefore.toString());
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("No. of tabs: " + tabs.size());
		while(tabs.size()!=2)
		{
			 tabs = new ArrayList<String>(driver.getWindowHandles());
		}
		System.out.println("No. of tabs: " + tabs.size());
		driver.switchTo().window(tabs.get(1));
		System.out.println("New Window "+tabs.get(1));
//		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement skipBtn = driver.findElement(By.xpath("//div[@class='skip close']"));
		new WebDriverWait(driver,Duration.ofSeconds(30))
		.until(ExpectedConditions.visibilityOf(skipBtn));
		String title = driver.getTitle();
		System.out.println(title+" ");
		String no_of_controls = ((JavascriptExecutor)driver)
				.executeScript("return 	document.querySelectorAll('.fb-field-container').length","").toString();
		System.out.println(no_of_controls);
		return no_of_controls;
	}
	
	public void addSSHKey(WebDriver driver)
	{
		getImplicit();
		driver.findElement(By.xpath("//*[@class='new-profile-icon']")).click();
		WebElement acc_settings = driver.findElement(By.xpath("//span[text()='Account Settings']"));
		Actions action = new Actions(driver);

		//Performing the mouse hover action on the Account Settings.
		action.moveToElement(acc_settings).perform();
		driver.findElement(By.xpath("//span[text()='Integrations']")).click();
		WebElement manage = driver.findElement(By.xpath("//div[@class='setting-list-int']/div[5]/descendant::a"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", manage);
		manage.click();
		getImplicit();
		driver.findElement(By.xpath("//*[text()=' Add Key ']")).click();
		driver.findElement(By.xpath("//*[@id='key-name-ip']")).sendKeys("ssh_key_"+System.nanoTime());
		driver.findElement(By.xpath("//span[text()='Generate Key & Save']/parent::a")).click();
		explicitwait.until(ExpectedConditions.textToBe(By.xpath("//div[@class='toast-text']"),"Key Added successfully" ));
		String msg  = driver.findElement(By.xpath("//div[@class='toast-text']")).getText();
	}
	public void appPush(WebDriver driver)
	{
		gotoApp(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath("//div[@class='fb-container']/descendant::div[text()='Publish']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.id("openGitSidebar")).click();
		new WebDriverWait(driver, Duration.ofSeconds(60))
		.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("git-repo-URL"))));
		driver.findElement(By.id("git-repo-URL")).clear();
		driver.findElement(By.id("git-repo-URL")).sendKeys("git@github.com:droanhqQA/AutoApp.git");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		driver.findElement(By.xpath("//*[text()='Use existing key']")).click();
//		driver.findElement(By.xpath("//span[text()='Generate SSH key']/parent::a")).click();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//		driver.findElement(By.xpath("//*[text()='Use existing key']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath("//*[text()='Select key']/parent::div")).click();
		
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		
		By ssh_key = By.xpath("//div[@title='AutoTest']");
		
		jsExecutor.executeScript("arguments[0].scrollIntoView(false)",
				driver.findElement(By.xpath(" //*[@id='gitSyncSidebar']/div/div[3]")));
		
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", driver.findElement(ssh_key));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(ssh_key).click();
		driver.findElement(By.xpath("//div[@data-type='connect']/a[1]")).click();
		By ToastMsg = By.xpath("//div[@class='toast-text']");
		new WebDriverWait(driver, Duration.ofSeconds(60))
		.until(ExpectedConditions.textToBe(ToastMsg, "CONNECTION SUCCESSFULL"));
		String message = driver.findElement(ToastMsg).getText();
		System.out.println(message);
		driver.findElement(By.xpath("//label[text()='Push']")).click();
		driver.findElement(By.xpath("//*[@class='screen-wrap']/div/div[3]/textarea")).sendKeys("Testing");
		driver.findElement(By.xpath("//*[text()='Commit & Push']/parent::a")).click();
		new WebDriverWait(driver,Duration.ofMinutes(2))
		.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@class='event-head' and text()='Success !']"))));
		System.out.println(driver.findElement(By.xpath("//*[@class='event-head' and text()='Success !']")).getText());
		driver.findElement(By.xpath("//*[@id='ev1']/div/div[2]/div[3]/div")).click();
		
	}
	public void appPull(WebDriver driver)
	{
		gotoApp(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath("//div[@class='fb-container']/descendant::div[text()='Publish']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.id("openGitSidebar")).click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//label[text()='Pull']")).click();
		driver.findElement(By.xpath("//div[@data-type='pull']/a")).click();
		new WebDriverWait(driver,Duration.ofMinutes(2))
		.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@class='event-head' and text()='Success !']"))));
		System.out.println(driver.findElement(By.xpath("//*[@class='event-head' and text()='Success !']")).getText());
		driver.findElement(By.xpath("//*[@id='ev1']/div/div[2]/div[3]/div")).click();
	}
	public void appGitImport(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(By.className("add-microapp-card-image")).click();
		driver.findElement(By.xpath("//*[@data-type='upload']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath("//span[text()='Git Import']")).click();
//		driver.findElement(By.id("inst-app-git-repo-URL")).clear();
		driver.findElement(By.id("inst-app-git-repo-URL")).click();
		driver.findElement(By.id("inst-app-git-repo-URL")).sendKeys("ExportApp.git");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		new WebDriverWait(driver, Duration.ofSeconds(60))
		.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@for='key-selection-type']"))));
		driver.findElement(By.xpath("//*[@for='key-selection-type']")).click();
		driver.findElement(By.xpath("//*[@id=\"git-repo-install-sidebar\"]/div/div[2]/div[1]/div/div[3]/div[2]/div[2]/div/div")).click();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		By ssh_key = By.xpath("//div[@title='AutoTest']");
		jsExecutor.executeScript("arguments[0].scrollIntoView();", driver.findElement(ssh_key));
		driver.findElement(ssh_key).click();
		driver.findElement(By.xpath("//*[@id='git-repo-install-sidebar']/div/div[3]/div[1]/div/a/span")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(By.xpath("//span[text()='Verify']/parent::a")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(By.xpath("//span[text()='Confirm & Install']/parent::a")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		explicitwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='event-head']")));
		String success = driver.findElement(By.xpath("//*[@class='event-head']")).getText();
		System.out.println(success);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		WebElement openApp= driver.findElement(By.xpath("//*[@class='event-button-click']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", openApp);
		System.out.println("Waiting for application");
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
		String title = driver.getTitle();
		System.out.println(title+" ");
		WebElement skipBtn = driver.findElement(By.xpath("//div[@class='skip close']"));
		new WebDriverWait(driver,Duration.ofSeconds(30))
		.until(ExpectedConditions.visibilityOf(skipBtn));
//		skipBtn.click();
		
	}
	
	public void appUpdateJson(WebDriver driver)
	{
		getImplicit();
		driver.findElement(By.xpath("//*[@data-appname='AutoApp']/div/div[2]/div[2]")).click();//open Menu
		driver.findElement(By.xpath("//*[@data-appname='AutoApp']/div/div[2]/div[2]/div/div/div[2]")).click();//click update app

		final URL resource = AppsNavFunc.class.getResource("/ExportAuto.json");
		String exportApp_path = ((resource.toString().substring("file:/".length(),resource.toString().length())));
		driver.findElement(By.xpath("//*[@id='json-file-import']")).sendKeys(exportApp_path);
		driver.findElement(By.xpath("//*[text()='Continue']/parent::a")).click();
		getImplicit();
		driver.findElement(By.xpath("//*[text()='Confirm & Update']/parent::a")).click();
		getImplicit();
		String success_import = driver.findElement(By.xpath("//div[@class='event-body']")).getText();
		System.out.println(success_import);
		getImplicit();
		driver.findElement(By.xpath("//div[@class='event-button']/div")).click();
		
		new WebDriverWait(driver,Duration.ofMinutes(5))
		.until(ExpectedConditions.numberOfWindowsToBe(2));
		String winHandleBefore = driver.getWindowHandle();
		System.out.println(winHandleBefore.toString());
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("No. of tabs: " + tabs.size());
		while(tabs.size()!=2)
		{
			 tabs = new ArrayList<String>(driver.getWindowHandles());
		}
		System.out.println("No. of tabs: " + tabs.size());
		driver.switchTo().window(tabs.get(1));
		System.out.println("New Window "+tabs.get(1));
//		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement skipBtn = driver.findElement(By.xpath("//div[@class='skip close']"));
		new WebDriverWait(driver,Duration.ofSeconds(30))
		.until(ExpectedConditions.visibilityOf(skipBtn));
		String title = driver.getTitle();
		System.out.println(title+" ");
		String no_of_controls = ((JavascriptExecutor)driver)
				.executeScript("return 	document.querySelectorAll('.fb-field-container').length","").toString();
		System.out.println(no_of_controls);
	}
	public void gitAppUpdate(WebDriver driver)
	{
		getImplicit();
		driver.findElement(By.xpath("//*[@data-appname='AutoApp']/div/div[2]/div[2]")).click();//open Menu
		driver.findElement(By.xpath("//*[@data-appname='AutoApp']/div/div[2]/div[2]/div/div/div[2]")).click();//click update app
		driver.findElement(By.xpath("//*[@for='gitpull']")).click();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@class='git-connection-info']/div[4]/div")).click();
		driver.findElement(By.xpath("//*[@data-value='main']")).click();
		driver.findElement(By.xpath("//*[text()='Continue']/parent::a")).click();
		getImplicit();
		driver.findElement(By.xpath("//*[text()='Confirm & Update']/parent::a")).click();
		getImplicit();
		String success_import = driver.findElement(By.xpath("//div[@class='event-body']")).getText();
		System.out.println(success_import);
		getImplicit();
		driver.findElement(By.xpath("//div[@class='event-button']/div")).click();
		
		new WebDriverWait(driver,Duration.ofMinutes(5))
		.until(ExpectedConditions.numberOfWindowsToBe(2));
		String winHandleBefore = driver.getWindowHandle();
		System.out.println(winHandleBefore.toString());
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("No. of tabs: " + tabs.size());
		while(tabs.size()!=2)
		{
			 tabs = new ArrayList<String>(driver.getWindowHandles());
		}
		System.out.println("No. of tabs: " + tabs.size());
		driver.switchTo().window(tabs.get(1));
		System.out.println("New Window "+tabs.get(1));
//		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement skipBtn = driver.findElement(By.xpath("//div[@class='skip close']"));
		new WebDriverWait(driver,Duration.ofSeconds(30))
		.until(ExpectedConditions.visibilityOf(skipBtn));
		String title = driver.getTitle();
		System.out.println(title+" ");
		String no_of_controls = ((JavascriptExecutor)driver)
				.executeScript("return 	document.querySelectorAll('.fb-field-container').length","").toString();
		System.out.println(no_of_controls);
	}
	
	public void appExport(WebDriver driver)
	{
		gotoApp(driver);
		driver.findElement(By.xpath("//img[@class='setting-icon-image']/parent::button")).click();
		driver.findElement(By.xpath("//*[@id='openConfig']/div/descendant::span[contains(text(),'Export')]")).click();
		getImplicit();
		driver.findElement(By.xpath("//*[@id='exportTemplate']")).click();
		getImplicit();
		driver.findElement(By.xpath("//div[@class='hq-primary-btn download-json-btn ml-2 pointer']")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//div[@class='hq-primary-btn download-json-btn ml-2 pointer']")).click();
	
	}
}
