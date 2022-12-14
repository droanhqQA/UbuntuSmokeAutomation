package testcases;

import java.io.File;
import static org.testng.Assert.assertTrue;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import com.applications.AppsNavFunc;
import org.openqa.selenium.remote.*;
import com.connectors.*;
import com.dao.UserDAO;
import com.dao.UserLogin;
import com.utils.Base_Class;
import com.utils.TakeScreenshots;
import com.utils.Utility_Class;


public class ApplicationTest extends Base_Class
{
//	public ChromeDriver driver;
	public AppsNavFunc navFunc;
	public final String RESULT_MSG="Record(s) found";
//	WebDriverWait wait;
	String downloadFilepath;
	@BeforeMethod
	public void setUp() throws IOException
	{
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		System.out.println(currentTimestamp);
		
//		final URL resource = ApplicationTest.class.getResource("/Automation.xlsx");
//	    System.out.println(resource);
//		FileInputStream  fs = new FileInputStream("/"+(resource.toString().substring("file:/".length(),resource.toString().length())));
//		XSSFWorkbook workbook= new XSSFWorkbook(fs);
//		// con_sheet = workbook.getSheetAt(1);
//		XSSFSheet user_sheet = workbook.getSheetAt(0);
//		UserDAO userDAO = new UserDAO(fs, workbook, user_sheet);
//		String u_name = userDAO.getU_name();
//		String u_pass = userDAO.getU_pass();
//		final URL driver_path = ApplicationTest.class.getResource("/chromedriver.exe");
//	    System.out.println(driver_path);
//		System.setProperty("webdriver.chrome.driver",driver_path.toString());
		
		BrowserSetUp();
		String u_name =Utility_Class.GetExcelData(0, 0, 1) ;
		String u_pass = Utility_Class.GetExcelData(0, 1, 1);
		
		downloadFilepath = "/home/nitin/Downloads";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
//		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
//		options.addArguments("headless");
//		options.addArguments("window-size=1500,800");
//		options.addArguments("incognito");
//		options.addArguments("disable-infobars");
//		options.setAcceptInsecureCerts(true);
//		driver = new ChromeDriver(options);
//		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		UserLogin user = new UserLogin(driver);
		navFunc = new AppsNavFunc(driver);
//		driver.get("https://ubuntu.onprem.dronahq.com/");
		driver.manage().deleteAllCookies();
		//driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
//		driver.manage().window().maximize();
		user.login(u_name,u_pass);
		
	}
	//@Ignore
	@Test(priority = 1)
	public void createApp()
	{
		try {
		Thread.sleep(30000);
		}
		catch(Exception e)
		{}
		driver.findElement(By.className("add-microapp-card-image")).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"showcase-wrap\"]/div[3]/div/div[1]/div[1]")).click();
		driver.findElement(By.xpath("//input[@class='app-name']")).sendKeys("AutoApp");
		driver.findElement(By.xpath("//textarea[@class='app-desc']")).sendKeys("Description");
		driver.findElement(By.xpath("//*[@id=\"icons-suggestion-list-container\"]/div[3]")).click();
		driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]")).click();
		String winHandleBefore = driver.getWindowHandle();
		new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.numberOfWindowsToBe(2));
		System.out.println(winHandleBefore.toString());
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("No. of tabs: " + tabs.size());
		driver.switchTo().window(tabs.get(1));
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("New Window "+tabs.get(1));
//		new WebDriverWait(driver, 50)
//		.until(ExpectedConditions.titleContains("AutoApp"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		String title = driver.getTitle();
		System.out.println("Title: "+title);
		AssertJUnit.assertEquals(title, "AutoApp");
		
	}

	//@Ignore	
	@Test(dependsOnMethods = "createApp",priority = 2)
	public void addControls()
	{
		System.out.println("Add Controls");
		navFunc.gotoApp(driver);
		navFunc.addControl(driver);
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='page-body-box']/div[2]")).click();//click tablegrid 
	    String un_name = driver.findElement(By.xpath("//input[@data-rv-input='model.field_display_key_name']")).getAttribute("value");
	    System.out.println(un_name);
	    wait.until(waitForSave());
	    AssertJUnit.assertEquals(un_name,"tablegrid");
	    
	}
	
	//@Ignore
	@Test(dependsOnMethods = "addControls",priority = 3)
	//@Test
	public void addMongoConnector()
	{
		System.out.println("Add Mongo Connector");
		 //driver.findElement(By.xpath("//div[@data-appname='AutoApp']")).click();
		navFunc.gotoApp(driver);
		navFunc.addMongo(driver);
		String resMsg = navFunc.getResultMsg(driver);
		System.out.println(resMsg.contains(RESULT_MSG));
		wait.until(waitForSave());
		AssertJUnit.assertEquals(resMsg.contains(RESULT_MSG),true);
	}
	//@Ignore
	@Test(dependsOnMethods = "addControls",priority = 4)
	//@Test
	public void addPostgreConnector()
	{
		System.out.println("Add Postgre Connector");
		navFunc.gotoApp(driver);
		navFunc.addPostgre(driver);
		String resMsg = navFunc.getResultMsg(driver);
		wait.until(waitForSave());
		System.out.println(resMsg.contains(RESULT_MSG));
		AssertJUnit.assertEquals(resMsg.contains(RESULT_MSG),true);
		
	}
	
	//@Ignore
	@Test(dependsOnMethods = "addControls",priority = 5)
	//@Test
	public void addAutoApi()
	{
		System.out.println("Add API Connector");
		navFunc.gotoApp(driver);
		navFunc.addRestApi(driver);
		String resMsg = navFunc.getResultMsg(driver);
		System.out.println(resMsg.contains(RESULT_MSG));
		wait.until(waitForSave());
		AssertJUnit.assertEquals(resMsg.contains(RESULT_MSG),true);
	}
	//@Ignore
	@Test(dependsOnMethods = "addControls",priority = 6)
	//@Test
	public void addGraphQL()
	{
		System.out.println("Add GraphQL Connector");
		navFunc.gotoApp(driver);
		navFunc.addGraphQL(driver);
		String resMsg = navFunc.getResultMsg(driver);
		System.out.println(resMsg.contains(RESULT_MSG));
		wait.until(waitForSave());
		AssertJUnit.assertEquals(resMsg.contains(RESULT_MSG),true);
	}
	
	//@Ignore
	@Test(priority = 7)
	public void CheckPreview()
	{
		System.out.println("Checking for preview");
		navFunc.gotoApp(driver);
		String title=navFunc.checkPreview(driver);
		AssertJUnit.assertEquals(title.contains("Preview"),true );
	}
	

	//@Ignore
	@Test(priority = 8)
	public void CheckPublish()
	{
		navFunc.gotoApp(driver);
		String msg = navFunc.checkPublish(driver);
		AssertJUnit.assertEquals(msg, "Publish successfully");
	}
	//@Ignore
	@Test(priority = 9,retryAnalyzer = RetryAnalyzer.class)
	public void CheckAppImport()
	{
		
		String res = navFunc.AppImport(driver);
		Assert.assertEquals("4",res);
	}
	
	@Test(priority = 10,retryAnalyzer = RetryAnalyzer.class)
	public void checkGitAppImport()
	{
		System.out.println("Git Import");
		navFunc.appGitImport(driver);
	}
	@Test(priority = 11)
	public void addCheckSSHKey()
	{
		System.out.println("SSH Key");
		navFunc.addSSHKey(driver);
	}
	
	@Test(dependsOnMethods = "addCheckSSHKey",retryAnalyzer = RetryAnalyzer.class)
	public void checkGitPush()
	{
		System.out.println("Git Push");
		navFunc.appPush(driver);
	}
	@Test(dependsOnMethods = "checkGitPush",retryAnalyzer = RetryAnalyzer.class)
	public void checkGitPull()
	{
		System.out.println("Git Pull");
		navFunc.appPull(driver);
	}
	
	@Test(priority = 12,retryAnalyzer = RetryAnalyzer.class)
	public void checkUpdateAppJson()
	{
		System.out.println("Update App");
		navFunc.appUpdateJson(driver);
	}
	@Test(dependsOnMethods = "checkUpdateAppJson",retryAnalyzer = RetryAnalyzer.class)
	public void checkUpdateAppGit()
	{
		System.out.println("Update App Git");
		navFunc.gitAppUpdate(driver);
	}
	
	@Test(priority = 13,retryAnalyzer = RetryAnalyzer.class)
	public void checkAppExport()
	{	
		navFunc.appExport(driver);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean res;
		res= wait.until(filepresent());
		assertTrue(res);
	}
	public ExpectedCondition<Boolean> filepresent() {
	    return new ExpectedCondition<Boolean>() {
	        @Override
	        public Boolean apply(WebDriver driver) {
	        	System.out.println("File Path: "+downloadFilepath);
	            File f = new File(downloadFilepath);
	            
	            File[] dirContents = f.listFiles();

	            for (int i = 0; i < dirContents.length; i++) {
	            	Boolean file_present =  dirContents[i].getName().contains("AutoApp");
	                if (file_present) {
	                    // File has been found, it can now be deleted:
	                	dirContents[i].delete();
	                	System.out.println("is file present: "+file_present);
	                    return true;
	                }
	                    }
	                return false;
	        }
	    };
	}
	@Test(priority = 14,retryAnalyzer = RetryAnalyzer.class)
	public void checkAPicall()
	{
		navFunc.buttonFlowOAuth2(driver);
		
	}
	@Test(priority = 15,retryAnalyzer = RetryAnalyzer.class)
	public void checkApiCallOAuth1()
	{
		navFunc.buttonFlowOAuth1(driver);
	}
	@AfterMethod
	public void closeBrowser(ITestResult result)
	{
		try {
			if(ITestResult.FAILURE==result.getStatus())
			{
			new TakeScreenshots().takeScreenShot(result.getName(),"Apps",driver);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		driver.quit();
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
	
}
