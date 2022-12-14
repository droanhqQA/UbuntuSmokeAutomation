package com.connectors;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utils.GetTimeouts;




public class AddingConnector  {
	WebDriver driver;
	Long max_time,min_time;
	
	public AddingConnector(WebDriver driver){
		// TODO Auto-generated constructor stub
		this.driver=driver;
		GetTimeouts timeouts = new GetTimeouts();
		max_time = timeouts.getMax_time();
		min_time = timeouts.getMin_time();
		
	}
	public void navigatetoConnector()
	{
		System.out.println("inside navigate to connector");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		new WebDriverWait(driver, Duration.ofSeconds(max_time))
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-header='Connectors']/div/div/div[2]")));
		driver.findElement(By.xpath("//*[@data-header='Connectors']/div/div/div[2]")).click();
		//wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("add-text"))));
		
	}
	String connectorDetails_details(int sheet_no,String save) throws IOException
	{
		final URL resource = AddingConnector.class.getResource("/Automation.xlsx");
	       System.out.println(resource);
		
		FileInputStream  fs = new FileInputStream((resource.toString().substring("file:/".length(),resource.toString().length())));
		
		//Creating a workbook
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet con_sheet = workbook.getSheetAt(sheet_no);
		
		String path,host,port,db,username,password,name;
		System.out.println(con_sheet.getSheetName());
		host=con_sheet.getRow(1).getCell(1).toString();
		port = con_sheet.getRow(2).getCell(1).toString();
		db = con_sheet.getRow(3).getCell(1).toString();
		username = con_sheet.getRow(4).getCell(1).toString();
		password = con_sheet.getRow(5).getCell(1).toString();
		path  = con_sheet.getRow(6).getCell(1).toString();
		name = con_sheet.getRow(7).getCell(1).toString();
		System.out.print(host+" "+port+" "+db+" "+username+" "+password+"\n"+path);
		
		System.out.println("inside connector_details");
		String add = "body > div.console-body.pusher > div.header.main > div.header-region > div.header-functions.smaller-font-size > div.add-new-btn.add-category-button";
		new WebDriverWait(driver, Duration.ofSeconds(min_time)).until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(add))));
		driver.findElement(By.cssSelector(add)).click();
		//driver.findElement(By.cssSelector("body > div.console-body.pusher > div.header.main > div.header-region > div.header-functions.smaller-font-size > div.add-new-btn.add-category-button")).click();
		new WebDriverWait(driver, Duration.ofSeconds(min_time)).until(ExpectedConditions.elementToBeClickable(By.cssSelector(path)));
		driver.findElement(By.cssSelector(path)).click();
		

		//new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("categoryName"))).sendKeys("hello");;
		//System.out.println(driver.findElement(By.xpath("/html/body/div[55]/div[2]/div/div[3]/div[1]/div[1]/div/input")).isDisplayed());
		new WebDriverWait(driver, Duration.ofSeconds(min_time)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='categoryName'][@class='form-control mt-2']")));
		driver.findElement(By.xpath("//*[@id='categoryName'][@class='form-control mt-2']")).sendKeys(name);
//		driver.findElement(By.id("connectionstring")).sendKeys("mongodb+srv://fj:deltecs123@cluster0.z03w2.mongodb.net/sample_airbnb?retryWrites=true&w=majority");
		driver.findElement(By.id("fields.host")).sendKeys(host);
		driver.findElement(By.id("fields.port")).sendKeys(port);
		driver.findElement(By.id("fields.dbname")).sendKeys(db);
		driver.findElement(By.id("fields.username")).sendKeys(username);
		driver.findElement(By.id("fields.password")).sendKeys(password);
		WebElement element = driver.findElement(By.xpath("//label[text()='Connect using SSL' and @class='input-name']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		element.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(min_time));
		driver.findElement(By.xpath("//*[@id=\"connectorSidebar\"]/div[2]/div/div[6]/div[2]/a[1]")).click();
		System.out.println("TestConnection Btn clicked");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(min_time));
		if(save.contains("save"))
		{
			return saveConnection(driver);
		}
		String actual,expected,actual_xpath;
		expected="Connection successful! You can go ahead and add your connector and create queries.";
		actual_xpath="//div[@class='p-4 pb-0 testing-messages']/div[3]/div/div/div";
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(min_time));
		wait1.until(ExpectedConditions.textToBe(By.xpath(actual_xpath), expected));
		actual=driver.findElement(By.xpath(actual_xpath)).getText();
		Boolean res = expected.equals(actual);
		return(res.toString());
		//return(expected.equals(actual));
	}
	@SuppressWarnings("deprecation")
	String connectorDetails_string(int sheet_no,String save) throws IOException
	{
		System.out.println("inside connector_details");
		final URL resource = AddingConnector.class.getResource("/Automation.xlsx");
	       System.out.println(resource);
		
		FileInputStream  fs = new FileInputStream((resource.toString().substring("file:/".length(),resource.toString().length())));
		//Creating a workbook
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet user_sheet = workbook.getSheetAt(0);
		XSSFSheet con_sheet = workbook.getSheetAt(sheet_no);
		String con_name =con_sheet.getRow(1).getCell(1).toString();
		String con_string= con_sheet.getRow(2).getCell(1).toString();
		String path = con_sheet.getRow(3).getCell(1).toString();
		System.out.println(path);
		String add = "body > div.console-body.pusher > div.header.main > div.header-region > div.header-functions.smaller-font-size > div.add-new-btn.add-category-button";
		new WebDriverWait(driver,Duration.ofSeconds(120)).until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(add))));
		driver.findElement(By.cssSelector(add)).click();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(min_time));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(path)));
		driver.findElement(By.cssSelector(path)).click();

		//new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("categoryName"))).sendKeys("hello");;
		//System.out.println(driver.findElement(By.xpath("/html/body/div[55]/div[2]/div/div[3]/div[1]/div[1]/div/input")).isDisplayed());
		driver.findElement(By.xpath("//*[@id='categoryName'][@class='form-control mt-2']")).sendKeys(con_name);
		driver.findElement(By.id("connectionstring")).sendKeys(con_string);

		driver.findElement(By.xpath("//*[@class='hq-link-btn d-flex align-center mr-4 pointer run_test_connection']")).click();
		System.out.println("TestConnection Btn clicked");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		if(save.contains("save"))
		{
			return saveConnection(driver);
		}
		String actual,expected,actual_xpath;
		expected="Connection successful! You can go ahead and add your connector and create queries.";
		actual_xpath="//div[@class='p-4 pb-0 testing-messages']/div[3]/div/div/div";
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(min_time));
		wait1.until(ExpectedConditions.textToBe(By.xpath(actual_xpath), expected));
		actual=driver.findElement(By.xpath(actual_xpath)).getText();
		Boolean res = expected.equals(actual);
		return(res.toString());

	}  
	
	
	
	String getFinalMessage()
	{  
		System.out.println("inside get Final message");
		String expected ="Connection successful! You can go ahead and add your connector and create queries.";
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.textToBe(By.xpath("//div[@class='p-4 pb-0 testing-messages']/div[3]/div/div/div"), expected));
		return driver.findElement(By.xpath("//div[@class='p-4 pb-0 testing-messages']/div[3]/div/div/div")).getText();
	}
	
	public String connectorDetails_sheets(int sheet_no) throws IOException
	{
		final URL resource = AddingConnector.class.getResource("/Automation.xlsx");
	       System.out.println(resource);
		
		FileInputStream  fs = new FileInputStream((resource.toString().substring("file:/".length(),resource.toString().length())));
		//Creating a workbook
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		//XSSFSheet con_sheet = workbook.getSheetAt(sheet_no);
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String add = "body > div.console-body.pusher > div.header.main > div.header-region > div.header-functions.smaller-font-size > div.add-new-btn.add-category-button";
		new WebDriverWait(driver, Duration.ofSeconds(min_time)).until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(add))));
		driver.findElement(By.cssSelector(add)).click();
		String sheet_connector ="//div[@class='section']//div[text()='Google Sheets']/parent::div";
		
		new WebDriverWait(driver,Duration.ofSeconds(min_time)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(sheet_connector)));
		
		driver.findElement(By.xpath(sheet_connector)).click();//googlesheet-connector
		 //System.out.println(driver.findElement(By.xpath("//div[@data-account-name='AutoSheet']")).isDisplayed());
		//driver.findElement(By.xpath("//span[contains(text(),'Connect Google Sheets')]")).click();//connect-google sheet
		driver.findElement(By.id("account_name")).sendKeys("AutoSheet");
		driver.findElement(By.xpath("//span[contains(text(),'continue')]")).click();//sign-in with google
		String winHandleBefore = driver.getWindowHandle();
		System.out.println(winHandleBefore.toString());
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		    System.out.println("Switch to Google Frame");
		}
		//driver.manage().window().maximize();
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("dronahqsmtp@gmail.com");
		//driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[text()='Next']")).click();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("ytrewq@321");
		//find and next button pendin
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("window.scrollBy(0,250)", "");
		String pass_next = "/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/div/div[1]/div/div/button/span";
		new WebDriverWait(driver, Duration.ofSeconds(min_time)).until(ExpectedConditions.elementToBeClickable(By.xpath(pass_next)));
		driver.findElement(By.xpath(pass_next)).click();
		driver.findElement(By.xpath("//span[text()='Continue']")).click();//continue btn
		  driver.switchTo().window(winHandleBefore);
		  new WebDriverWait(driver, Duration.ofSeconds(min_time))
		  .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-account-name='AutoSheet']")));
		Boolean get=  driver.findElement(By.xpath("//div[@data-account-name='AutoSheet']")).isDisplayed();
		System.out.println(get);
		return get.toString();
	}
	public String connectorDetails_api(int sheet_no,String save)
	{
		String name,api_key,url;
		FileInputStream fs;
		XSSFWorkbook workbook;
		XSSFSheet con_sheet=null;
		try {
//			final URL resource = AddingConnector.class.getResource("/Automation.xlsx");
//		       System.out.println(resource);
//			
//			fs = new FileInputStream((resource.toString().substring("file:/".length(),resource.toString().length())));
			fs = new FileInputStream("C:\\Users\\Vinayak Hene\\git\\UbuntuSmokeAutomation\\src\\main\\resources\\Automation.xlsx");
			workbook = new XSSFWorkbook(fs);
			con_sheet = workbook.getSheetAt(sheet_no);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Creating a workbook
		
		name = con_sheet.getRow(1).getCell(1).toString();
		api_key=con_sheet.getRow(2).getCell(1).toString();
		url = con_sheet.getRow(3).getCell(1).toString();
		if(save.contains("add"))
		{
			String qname,qurl,add_path;
			qname=con_sheet.getRow(6).getCell(1).toString();
			qurl=con_sheet.getRow(5).getCell(1).toString();
			//add_path = con_sheet.getRow(4).getCell(1).toString();
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			add_path = "//*[text()='AutoApi']/ancestor-or-self::div[@class='table-row']/div/div[4]/div";
			WebElement add_api = driver.findElement(By.xpath(add_path));
			scrollTo(add_api);
			add_api.click();//query_path
			
			driver.findElement(By.id("apiName")).sendKeys(qname);
			driver.findElement(By.id("urlBlock4")).sendKeys(qurl);
			driver.findElement(By.xpath("//a[@class='hq-link-btn d-flex align-center mr-4 pointer testapi']")).click();
			System.out.println(driver.findElement(By.xpath("//*[@id='connectorSidebar']/div[2]/div/div[4]/div[8]/div/div[1]/div/div[1]")).getText());
			new WebDriverWait(driver, Duration.ofSeconds(min_time))
			.until(ExpectedConditions.textToBe(By.xpath("//*[@id=\"connectorSidebar\"]/div[2]/div/div[4]/div[8]/div[1]/div[1]/div/div"), "Query executed successfully."));
			
			//System.out.println(driver.findElement(By.xpath("//*[@id=\"connectorSidebar\"]/div[2]/div/div[3]/div/div/div[2]")).getText());
			driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer saveapi']")).click();
			String expected = "API Added";
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			new WebDriverWait(driver, Duration.ofSeconds(min_time))
			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#connectorSidebar > div.sidebar-content > div > div.content-wrapper.connector-success > div > div > div.hq-title.text-center.mt-3")));
			
			String actual = driver.findElement(By.cssSelector("#connectorSidebar > div.sidebar-content > div > div.content-wrapper.connector-success > div > div > div.hq-title.text-center.mt-3")).getText();
			System.out.println("actual :"+actual);
			Boolean res=expected.equals(actual);
			
			return res.toString();
		}
		String add = "body > div.console-body.pusher > div.header.main > div.header-region > div.header-functions.smaller-font-size > div.add-new-btn.add-category-button";
		new WebDriverWait(driver, Duration.ofSeconds(min_time)).until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(add))));
		driver.findElement(By.cssSelector(add)).click();
//		new WebDriverWait(driver, 20)
//		.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@data-scheme_type='api_connector']"))));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", 
									driver.findElement(By.xpath("//div[@class='section']/descendant::div[@data-scheme_type='api_connector']")));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='section']/descendant::div[@data-scheme_type='api_connector']")).click();
		new WebDriverWait(driver, Duration.ofSeconds(min_time))
		.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='apiCatName'][@class='form-control mt-2']"))));
		driver.findElement(By.xpath("//*[@id='apiCatName'][@class='form-control mt-2']")).sendKeys(name);
		driver.findElement(By.xpath("//div[@id='scheme_type']")).click();
		driver.findElement(By.xpath("//div[@data-value='api_key' and text()='API Key Authentication']")).click();
		driver.findElement(By.xpath("//a[@class='hq-link-btn simple d-flex align-center pointer justify-start mt-2 add-apikey-field w-30']")).click();
		driver.findElement(By.xpath("//input[@name='key']")).sendKeys("api_key");
		driver.findElement(By.xpath("//input[@name='value']")).sendKeys(api_key);
		driver.findElement(By.xpath("//div[@class='ui dropdown selection form-control w-40 mt-2 keytarget']")).click();
		driver.findElement(By.xpath("//div[@class='item'][@data-value='Querystring']")).click();
		driver.findElement(By.xpath("//input[@id='urlBlock']")).sendKeys(url);
		driver.findElement(By.xpath("//a[@class='hq-link-btn d-flex align-center mr-4 pointer testconnection']")).click();
		By testMsg = By.xpath("//div[@class='hq-card p-4 pb-0 messages-container']/descendant::div[@class='hq-title'][1]");
		new WebDriverWait(driver, Duration.ofSeconds(60))
		.until(ExpectedConditions.invisibilityOfElementWithText(testMsg, "Checking your Authentication"));
		String testMsgString = driver.findElement(testMsg).getText();
		System.out.println("testmsg: "+testMsgString);
		
	
		String expected ="Configuration test successful.";
//		while(!(expected.equals(testMsgString)))
//		{
//			
//			driver.findElement(By.xpath("//a[@class='hq-link-btn d-flex align-center mr-4 pointer testconnection']")).click();
//			new WebDriverWait(driver, 60)
//			.until(ExpectedConditions.invisibilityOfElementWithText(testMsg, "Checking your Authentication"));
//			testMsgString = driver.findElement(testMsg).getText();
//			System.out.println("testmsg: "+testMsgString);
//		}
		
		
		
		String actual = driver.findElement(By.xpath("//div[@class='hq-card p-4 pb-0 messages-container']/descendant::div[@class='hq-title'][1]")).getText();
		System.out.println(driver.findElement(By.xpath("//div[@class='hq-card p-4 pb-0 messages-container']/descendant::div[@class='hq-title'][1]")).getText());
		if(save.contains("save"))
		{
			driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer saveconnection']")).click();
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String expected1="Connector Successfully Added";
			String actual1 = driver.findElement(By.xpath("//div[text()='Connector Successfully Added']")).getText();
			System.out.println(actual1);
			Boolean res=expected1.equals(actual1);
			
			return res.toString();
		}
		
		Boolean res=expected.equals(actual);
		
		return res.toString();
		
	}
	
	public String connectorDetails_graphql(int sheet_no,String save)
	{
		String name,url;
		FileInputStream fs;
		XSSFWorkbook workbook;
		XSSFSheet con_sheet=null;
		try {
			final URL resource = AddingConnector.class.getResource("/Automation.xlsx");
		       System.out.println(resource);
			
			 fs = new FileInputStream((resource.toString().substring("file:/".length(),resource.toString().length())));
			workbook = new XSSFWorkbook(fs);
			con_sheet = workbook.getSheetAt(sheet_no);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Creating a workbook
		
		name = con_sheet.getRow(1).getCell(1).toString();
		url = con_sheet.getRow(2).getCell(1).toString();
		if(save.contains("add"))
		{
			String query =con_sheet.getRow(3).getCell(1).toString();
			//String query='{  country(code: "BR") {    name    native    emoji    currency    languages {      code      name    }  }}';
			//String add_path = con_sheet.getRow(4).getCell(1).toString();
			String add_path = "//*[text()='AutoGraphQL']/ancestor-or-self::div[@class='table-row']/div/div[4]/div";
			WebElement add_graphql =driver.findElement(By.xpath(add_path)); 
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", add_graphql);
			add_graphql.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			driver.findElement(By.id("queryname")).sendKeys("TestQ");
			//String Query="{launchesPast(limit: 10) { mission_name launch_date_local    launch_site {      site_name_long    }    rocket {      rocket_name      first_stage {        cores {          core {           status          }        }      }    }  }}";
			((JavascriptExecutor)driver)
			.executeScript("var evt = {type: 'keyup',keyCode: 32 } ;let CodeMirrorInstance = document.querySelector('.editor-container .CodeMirror-wrap').CodeMirror;CodeMirrorInstance.setValue('"+query+"');CodeMirrorInstance.triggerOnKeyUp(evt)");
			driver.findElement(By.xpath("//a[@class='hq-link-btn d-flex align-center mr-3 pointer testquery']")).click();
			//div[@class='content large']/div //success msg
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			WebElement msg = driver.findElement(By.xpath("//div[@class='content large']/div"));
			System.out.print(msg.getText());
			String expected="Query executed successfully.";
			String actual = msg.getText();
			Boolean res = expected.contains(actual);
			driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer savequery']")).click();
			return res.toString();
		}
		String add = "body > div.console-body.pusher > div.header.main > div.header-region > div.header-functions.smaller-font-size > div.add-new-btn.add-category-button";
		new WebDriverWait(driver, Duration.ofSeconds(min_time)).until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(add))));
		driver.findElement(By.cssSelector(add)).click();
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", 	driver.findElement(By.xpath("//div[@class='section']/descendant::div[@data-scheme_type='graphql']")));//scroll
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.xpath("//div[@class='section']/descendant::div[@data-scheme_type='graphql']")).click();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='apiCatName'][@class='form-control mt-2']")).sendKeys("AutoGraphQL");
		driver.findElement(By.id("urlBlock")).sendKeys(url);
		driver.findElement(By.xpath("//a[@class='hq-link-btn d-flex align-center mr-4 pointer testconnection']")).click();
		By testMsg = By.xpath("//div[@class='hq-card p-4 pb-0 messages-container']/descendant::div[@class='hq-title'][1]");
		new WebDriverWait(driver, Duration.ofSeconds(60))
		.until(ExpectedConditions.invisibilityOfElementWithText(testMsg, "Checking your Authentication"));
		String testMsgString = driver.findElement(testMsg).getText();
		System.out.println("testmsg: "+testMsgString);
		
	
		String expected ="Configuration test successful.";
//		while(!(expected.equals(testMsgString)))
//		{
//			
//			driver.findElement(By.xpath("//a[@class='hq-link-btn d-flex align-center mr-4 pointer testconnection']")).click();
//			new WebDriverWait(driver, 60)
//			.until(ExpectedConditions.invisibilityOfElementWithText(testMsg, "Checking your Authentication"));
//			testMsgString = driver.findElement(testMsg).getText();
//			System.out.println("testmsg: "+testMsgString);
//		}
		String actual = driver.findElement(By.xpath("//*[@id=\"connectorSidebar\"]/div[2]/div/div[4]/div[8]/div[1]/div[1]/div/div")).getText();
		System.out.println(driver.findElement(By.xpath("//*[@id=\"connectorSidebar\"]/div[2]/div/div[4]/div[8]/div[1]/div[1]/div/div")).getText());
		if(save.contains("save"))
		{
			driver.findElement(By.xpath("//a[@class='hq-primary-btn d-flex align-center pointer saveconnection']")).click();
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String expected1="Connector Successfully Added";
			String actual1 = driver.findElement(By.xpath("//*[@id='connectorSidebar']/div[2]/div/div[3]/div/div/div[2]")).getText();
			System.out.println(actual1);
			Boolean res=expected1.equals(actual1);
			
			return res.toString();
		}
		Boolean res=expected.equals(actual);
		
		return res.toString();
	}
	
	public String connectorDetails(String DB,int sheet_no,String save) throws IOException
	{
		//Boolean res=false;
		if(DB.equalsIgnoreCase("Mongo"))
		{
			return connectorDetails_string(sheet_no,save);
			//return res;
		}
		else if(DB.equalsIgnoreCase("postgre"))
		{
			 return connectorDetails_details(sheet_no,save);
			//return res;
		}
		else if(DB.equalsIgnoreCase("googlesheet"))
		{
			return connectorDetails_sheets(sheet_no);
		}
		else if(DB.equalsIgnoreCase("mssql"))
		{
			return connectorDetails_mssql(sheet_no,save);
		}
		return "";
	}
	public String connectorDetails_mssql(int sheet_no, String save) throws IOException {
		final URL resource = AddingConnector.class.getResource("/Automation.xlsx");
	       System.out.println(resource);
		
		FileInputStream  fs = new FileInputStream((resource.toString().substring("file:/".length(),resource.toString().length())));
		
		//Creating a workbook
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet con_sheet = workbook.getSheetAt(sheet_no);
		
		String path,host,port,db,username,password,name;
		System.out.println(con_sheet.getSheetName());
		host=con_sheet.getRow(1).getCell(1).toString();
		port = con_sheet.getRow(2).getCell(1).toString();
		db = con_sheet.getRow(3).getCell(1).toString();
		username = con_sheet.getRow(4).getCell(1).toString();
		password = con_sheet.getRow(5).getCell(1).toString();
		path  = con_sheet.getRow(6).getCell(1).toString();
		name = con_sheet.getRow(7).getCell(1).toString();
		System.out.print(host+" "+port+" "+db+" "+username+" "+password+"\n"+path);
		
		System.out.println("inside connector_details");
		String add = "body > div.console-body.pusher > div.header.main > div.header-region > div.header-functions.smaller-font-size > div.add-new-btn.add-category-button";
		new WebDriverWait(driver, Duration.ofSeconds(min_time)).until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(add))));
		driver.findElement(By.cssSelector(add)).click();
		//driver.findElement(By.cssSelector("body > div.console-body.pusher > div.header.main > div.header-region > div.header-functions.smaller-font-size > div.add-new-btn.add-category-button")).click();
		new WebDriverWait(driver, Duration.ofSeconds(min_time)).until(ExpectedConditions.elementToBeClickable(By.cssSelector(path)));
		driver.findElement(By.cssSelector(path)).click();
		

		//new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("categoryName"))).sendKeys("hello");;
		//System.out.println(driver.findElement(By.xpath("/html/body/div[55]/div[2]/div/div[3]/div[1]/div[1]/div/input")).isDisplayed());
		new WebDriverWait(driver,Duration.ofSeconds(min_time)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='categoryName'][@class='form-control mt-2']")));
		driver.findElement(By.xpath("//*[@id='categoryName'][@class='form-control mt-2']")).sendKeys(name);
		driver.findElement(By.id("fields.host")).sendKeys(host);
		driver.findElement(By.id("fields.port")).sendKeys(port);
		driver.findElement(By.id("fields.dbname")).sendKeys(db);
		driver.findElement(By.id("fields.username")).sendKeys(username);
		driver.findElement(By.id("fields.password")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"connectorSidebar\"]/div[2]/div/div[6]/div[2]/a[1]")).click();
		System.out.println("TestConnection Btn clicked");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(min_time));
		return null;
	}
	String saveConnection(WebDriver driver)
	{
		String save = "//*[@class='hq-primary-btn d-flex align-center pointer save-connector']";
		
		String actual,expected,actual_xpath;
		expected="Connection successful! You can go ahead and add your connector and create queries.";
		actual_xpath="//div[@class='p-4 pb-0 testing-messages']/div[3]/div/div/div";
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.textToBe(By.xpath(actual_xpath), expected));
		new WebDriverWait(driver,Duration.ofSeconds(min_time)).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(save))));
		driver.findElement(By.xpath(save)).click();//savebutton
		WebDriverWait wait1= new WebDriverWait(driver,Duration.ofSeconds(60));
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='connected-block']/div/div")));
		String connected = driver.findElement(By.xpath("//*[@class='connected-block']/div/div")).getText();
		System.out.println(connected);
		//driver.findElement(By.xpath("/html/body/div[55]/div[2]/div/div[1]/div[2]/a[2]")).click();//cross-button
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.MILLISECONDS);
		 Boolean res = (connected.equals("Connected!"));
		return res.toString();
	}
	public void OAuth1(WebDriver driver)
	{
		navigatetoConnector();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(By.xpath("//*[@class='add-new-btn add-category-button']")).click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", 
				driver.findElement(By.xpath("//div[@class='section']/descendant::div[@data-scheme_type='api_connector']")));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='section']/descendant::div[@data-scheme_type='api_connector']")).click();
		new WebDriverWait(driver, Duration.ofSeconds(min_time))
		.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='apiCatName'][@class='form-control mt-2']"))));
		driver.findElement(By.xpath("//*[@id='apiCatName'][@class='form-control mt-2']")).sendKeys("AutoApiOAuth1");
		driver.findElement(By.xpath("//div[@id='scheme_type']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		scrollTo(driver.findElement(By.xpath("//div[@data-value='oauth_v1a'][@class='item']")));
		WebElement oauthv1 = driver.findElement(By.xpath("//div[@data-value='oauth_v1a'][@class='item']"));
		//driver.findElement(By.xpath("//div[@data-value='oauth_v1a'][@class='item']")).click();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", oauthv1);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scrollTo(driver.findElement(By.xpath("//*[@id='clientid-oauthv1']")));
		driver.findElement(By.xpath("//*[@id='clientid-oauthv1']")).sendKeys("nuh7Ahpf0aR3zJQ9X7UGP5MBv");
		driver.findElement(By.xpath("//*[@id='clinetsecret-oauthv1']")).sendKeys("r7830CihHQBgF6mzI6CrIWTqLIhi38ObOiEYvaBP1V2b1K2S5o");
		By request_token =By.xpath("//*[starts-with(@class, 'oauth_v1a')]/descendant::input[@id='urlBlock1']");
		By auth_request = By.xpath("//*[starts-with(@class, 'oauth_v1a')]/descendant::input[@id='urlBlock1-oauthv1']");
		scrollTo(driver.findElement(request_token));
		driver.findElement(request_token).clear();
		driver.findElement(request_token).sendKeys("https://api.twitter.com/oauth/request_token");
		
		driver.findElement(auth_request).clear();
		driver.findElement(auth_request).sendKeys("https://api.twitter.com/oauth/authorize");
		
		By access_token = By.xpath("//*[starts-with(@class,'oauth_v1a')]/descendant::input[@id='urlBlock2-oauthv1']");
		scrollTo(driver.findElement(access_token));
		driver.findElement(access_token).clear();
		driver.findElement(access_token).sendKeys("https://api.twitter.com/oauth/access_token");
		scrollTo(driver.findElement(By.xpath("//*[@id='urlBlock']")));
		driver.findElement(By.xpath("//*[@id='urlBlock']")).sendKeys("https://api.twitter.com/1.1/account/verify_credentials.json");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(By.xpath("//a[@class='hq-link-btn d-flex align-center mr-4 pointer testconnection']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		new WebDriverWait(driver, Duration.ofSeconds(60))
		.until(ExpectedConditions.numberOfWindowsToBe(2));
		Set<String>s=driver.getWindowHandles();
		String parent=driver.getWindowHandle();
		// Now iterate using Iterator
		Iterator<String> I1= s.iterator();

		while(I1.hasNext())
		{

		String child_window=I1.next();


		if(!parent.equals(child_window))
		{
		driver.switchTo().window(child_window);
		
		System.out.println(driver.switchTo().window(child_window).getTitle());
		
		//driver.manage().window().setSize(new Dimension(1920,1080));
		driver.findElement(By.xpath("//*[@id='username_or_email']")).sendKeys("rahul@dronamobile.com");
		driver.findElement(By.xpath("//*[@id='password']")).sendKeys("Qwerty@123");
		WebElement element = driver.findElement(By.xpath("//*[@value='Authorize app']"));
		//driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
		//((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		element.sendKeys(Keys.ENTER);
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
		}
		driver.switchTo().window(parent);
		driver.findElement(By.xpath("//*[@class='hq-primary-btn d-flex align-center pointer saveconnection']")).click();
		String msg = driver.findElement(By.xpath("//*[@class='hq-title text-center mt-3']")).getText();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(msg);
	}
	public void OAuth2withPKCE(WebDriver driver)
	{
		navigatetoConnector();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(By.xpath("//*[@class='add-new-btn add-category-button']")).click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", 
				driver.findElement(By.xpath("//div[@class='section']/descendant::div[@data-scheme_type='api_connector']")));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='section']/descendant::div[@data-scheme_type='api_connector']")).click();
		new WebDriverWait(driver, Duration.ofSeconds(min_time))
		.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='apiCatName'][@class='form-control mt-2']"))));
		driver.findElement(By.xpath("//*[@id='apiCatName'][@class='form-control mt-2']")).sendKeys("AutoApiOAuth2");
		driver.findElement(By.xpath("//div[@id='scheme_type']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		//driver.findElement(By.xpath("//div[@data-value='oauth_v2_pkce'][@class='item']")).click();
		WebElement authv2=driver.findElement(By.xpath("//div[@data-value='oauth_v2_pkce'][@class='item']"));
		scrollTo(authv2);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		JavascriptExecutor executor =(JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click()", authv2);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scrollTo(driver.findElement(By.xpath("//*[@id='clientid-pkce']")));
		driver.findElement(By.xpath("//*[@id='clientid-pkce']")).sendKeys("57f0a3e162d9463892fdcd26b82c995c");
		driver.findElement(By.xpath("//*[@id='urlBlock1-pkce']")).clear();
		driver.findElement(By.xpath("//*[@id='urlBlock1-pkce']")).sendKeys("https://accounts.spotify.com/authorize");
		
		scrollTo(driver.findElement(By.xpath("//*[@id=\"scope-pkce\"]")));
		driver.findElement(By.xpath("//*[@id='scope-pkce']")).sendKeys("user-read-private user-read-email");
		driver.findElement(By.xpath("//*[@id=\"urlBlock2-pkce\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"urlBlock2-pkce\"]")).sendKeys("https://accounts.spotify.com/api/token");
		driver.findElement(By.xpath("//*[@id=\"urlBlock3-pkce\"]")).sendKeys("https://accounts.spotify.com/api/token");
		scrollTo(driver.findElement(By.xpath("//*[@id=\"urlBlock\"]")));
		driver.findElement(By.xpath("//*[@id=\"urlBlock\"]")).sendKeys("https://api.spotify.com/v1/me");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.findElement(By.xpath("//a[@class='hq-link-btn d-flex align-center mr-4 pointer testconnection']")).click();
		Set<String>s=driver.getWindowHandles();
		String parent=driver.getWindowHandle();
		// Now iterate using Iterator
		Iterator<String> I1= s.iterator();

		while(I1.hasNext())
		{

		String child_window=I1.next();


		if(!parent.equals(child_window))
		{
		driver.switchTo().window(child_window);
		
		System.out.println(driver.switchTo().window(child_window).getTitle());
		
		driver.manage().window().setSize(new Dimension(1920,1080));
		scrollTo(driver.findElement(By.xpath("//*[@id=\"login-button\"]/div[1]")));
		driver.findElement(By.xpath("//*[@id=\"login-username\"]")).clear();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@id=\"login-password\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"login-username\"]")).sendKeys("soulehshaikh99@gmail.com");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@id=\"login-password\"]")).sendKeys("j8UL5@v+UpeSCag");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement element = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
		//driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
		//((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		element.sendKeys(Keys.ENTER);
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scrollTo(driver.findElement(By.xpath("//*[@data-testid='auth-accept']/div[1]")));
		driver.findElement(By.xpath("//*[@data-testid='auth-accept']/div[1]")).click();
		}
		}
		driver.switchTo().window(parent);
		driver.findElement(By.xpath("//*[@class='hq-primary-btn d-flex align-center pointer saveconnection']")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void scrollTo(WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", 
				element);
	}
}
