package testcases;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.connectors.AddingConnector;
import com.dao.UserDAO;
import com.dao.UserLogin;
import com.utils.TakeScreenshots;

public class HeadFullTestCases {
	AddingConnector connector;
	ChromeDriver driver ;
	FileInputStream fs ;
	
	String testName="";
	@BeforeTest
	public void startTest(final ITestContext testContext) {
		testName = testContext.getName();
	    System.out.println(testContext.getName()); 
	}
	@BeforeMethod
	public void setUp() throws IOException
	{
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		System.out.println(currentTimestamp);
		//Path of the excel file
		final URL resource = ConnectorsTest.class.getResource("/Automation.xlsx");
	       System.out.println(resource);
		 fs = new FileInputStream((resource.toString().substring("file:/".length(),resource.toString().length())));
				
				//Creating a workbook
				XSSFWorkbook workbook = new XSSFWorkbook(fs);
				XSSFSheet user_sheet = workbook.getSheetAt(0);
				XSSFSheet con_sheet = workbook.getSheetAt(1);
				UserDAO userDAO= new UserDAO(fs, workbook, user_sheet);
				//ConDetailDAO detailDAO = new ConDetailDAO(fs, workbook, con_sheet);
				
				
				
				String u_name = userDAO.getU_name();
				String u_pass = userDAO.getU_pass();
				String con_name,con_string,con_uname,con_upass,con_db;
				//System.out.println(detailDAO.connectionName()+"\n"+detailDAO.connectionString());
				final URL driver_path = ConnectorsTest.class.getResource("/chromedriver.exe");
			       System.out.println(driver_path);
				
				System.setProperty("webdriver.chrome.driver", (driver_path.toString().substring("file:/".length(),driver_path.toString().length())));
				ChromeOptions options = new ChromeOptions();
					//	options.addArguments("headless");
						options.addArguments("window-size=1920,1080");
						options.addArguments("incognito");
//						ChromeDriver driver = new ChromeDriver(options);

				driver = new ChromeDriver(options);
			UserLogin user = new UserLogin(driver);
				
				
			connector = new AddingConnector(driver);
				driver.get("https://ubuntu.onprem.dronahq.com/");
				driver.manage().deleteAllCookies();
				//driver.manage().timeouts().pageLoadTimeout(-1,TimeUnit.SECONDS);
				driver.manage().window().maximize();
				user.login(u_name,u_pass);
				
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				//connector.connectorDetails(con_string);
				connector.navigatetoConnector();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
	}
	
		@Test(retryAnalyzer = RetryAnalyzer.class)
	public void checkApiOAuth2()
	{

		connector.OAuth2withPKCE(driver);
	}
	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void checkApiOAuth1()
	{
		connector.OAuth1(driver);
	}
	@AfterMethod
	public void driverClose(ITestResult result,ITestContext testContext)
	{
		try {
			if(ITestResult.FAILURE==result.getStatus())
			{
			System.out.println(testContext.getName()+" "+result.getMethod().getMethodName());	
			new TakeScreenshots().takeScreenShot(testContext.getName()+"_"+result.getMethod().getMethodName(),"Connectors",driver);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();
	}
}
