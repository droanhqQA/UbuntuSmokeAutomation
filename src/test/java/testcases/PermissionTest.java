package testcases;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.connectors.AddingConnector;
import com.dao.UserDAO;
import com.dao.UserLogin;
import com.permission.AppPermission;
import com.permission.ConnectorPermission;
import com.permission.GroupPermission;
import com.utils.TakeScreenshots;

public class PermissionTest {
	AddingConnector connector;
	ChromeDriver driver;
	FileInputStream fs;
	AppPermission appPermission;
	GroupPermission grpPermission; 
	ConnectorPermission connPermission;
	String testName = "";
	ArrayList<String> getPermission;
	@BeforeTest
	public void startTest(final ITestContext testContext) {
		testName = testContext.getName();
		System.out.println(testContext.getName());
	}

	@BeforeMethod
	public void setUp() throws IOException {
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		System.out.println(currentTimestamp);
		// Path of the excel file
		final URL resource = ConnectorsTest.class.getResource("/Automation.xlsx");
		System.out.println(resource);
		fs = new FileInputStream((resource.toString().substring("file:/".length(), resource.toString().length())));

		// Creating a workbook
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet user_sheet = workbook.getSheetAt(0);
		XSSFSheet con_sheet = workbook.getSheetAt(1);
		UserDAO userDAO = new UserDAO(fs, workbook, user_sheet);
		// ConDetailDAO detailDAO = new ConDetailDAO(fs, workbook, con_sheet);

		String u_name = userDAO.getU_name();
		String u_pass = userDAO.getU_pass();
		String con_name, con_string, con_uname, con_upass, con_db;
		// System.out.println(detailDAO.connectionName()+"\n"+detailDAO.connectionString());
		final URL driver_path = ConnectorsTest.class.getResource("/chromedriver.exe");
		System.out.println(driver_path);

		System.setProperty("webdriver.chrome.driver",
				(driver_path.toString().substring("file:/".length(), driver_path.toString().length())));
		
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				options.addArguments("--window-size=1920,1080");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-setuid-sandbox");
				options.setAcceptInsecureCerts(true);

		driver = new ChromeDriver(options);
		
		//driver = new ChromeDriver();
		UserLogin user = new UserLogin(driver);
		appPermission = new AppPermission();
		grpPermission = new GroupPermission();
		connPermission = new ConnectorPermission();
		getPermission = new ArrayList<String>();
		driver.get("https://ubuntu.onprem.dronahq.com/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		user.login(u_name, u_pass);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class,priority = 1)
	public void testGroupEditorPrem()
	{
		grpPermission.setEditor(driver);
		getPermission=grpPermission.changePermission("editor", driver);
		System.out.println(getPermission.get(0)+" "+getPermission.get(1));
		boolean result = getPermission.get(0).contains(" Edit Details Update app") &&
				 getPermission.get(1).contentEquals("0010"); 
		assertTrue(result);
	}
	@Test(retryAnalyzer = RetryAnalyzer.class,priority = 2)
	public void testGroupPreviewPrem()
	{
		grpPermission.setPreview(driver);
		getPermission=grpPermission.changePermission("useonly", driver);
		System.out.println(getPermission.get(0)+" "+getPermission.get(1));
		boolean result = getPermission.get(0).contains("icon-info app-owner-popup")&&
				 getPermission.get(1).contentEquals("0001");
		assertTrue(result);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class,priority = 3)
	public void testGroupNonePrem()
	{
		grpPermission.setNone(driver);
		getPermission=grpPermission.changePermission("none", driver);
		System.out.println(getPermission.get(0)+" "+getPermission.get(1));
		boolean result=getPermission.get(0).contains("No Apps Found") &&
				   getPermission.get(1).contains("0000");
		assertTrue(result);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class,priority = 4)
	public void testNone()
	{
		getPermission=appPermission.changePermission("none", driver);
		System.out.println(getPermission.get(0)+" "+getPermission.get(1));
		boolean result=getPermission.get(0).contains("No Apps Found") &&
					   getPermission.get(1).contains("0000");
		assertTrue(result);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class,priority = 5)
	public void testPreviewOnly()
	{
		getPermission=appPermission.changePermission("useonly", driver);
		System.out.println(getPermission.get(0)+" "+getPermission.get(1));
		boolean result = getPermission.get(0).contains("icon-info app-owner-popup")&&
						 getPermission.get(1).contentEquals("0001");
		assertTrue(result);
	}
	@Test(retryAnalyzer = RetryAnalyzer.class,priority = 6)
	
	public void testEditor()
	{
		getPermission=appPermission.changePermission("editor", driver);
		System.out.println(getPermission.get(0)+" "+getPermission.get(1));
		boolean result = getPermission.get(0).contains(" Edit Details Update app") &&
						 getPermission.get(1).contentEquals("0010"); 
		assertTrue(result);
	}
	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void testGroupConnUseonly()
	{
		grpPermission.setConnUseOnly(driver);
		getPermission=	grpPermission.changeConnPermission("useonly", driver);
		boolean result  =Boolean.parseBoolean(getPermission.get(0));
		assertTrue(result);
		
	}
	@Test(retryAnalyzer = RetryAnalyzer.class,dependsOnMethods = "testGroupConnUseonly")
	public void testGroupConnNone()
	{
		grpPermission.setConnNone(driver);
		getPermission=	grpPermission.changeConnPermission("none", driver);
		System.out.println(getPermission.get(0));
		boolean result  =Boolean.parseBoolean(getPermission.get(0));
		assertTrue(result);
		
	}
	@Test(retryAnalyzer = RetryAnalyzer.class,dependsOnMethods = "testGroupConnNone")
	public void testConnUseOnly()
	{
		getPermission=appPermission.changeConnPermission("useonly", driver);
		System.out.println(getPermission.get(0));
		boolean result  =Boolean.parseBoolean(getPermission.get(0));
		assertTrue(result);
		
	}
	@Test(retryAnalyzer = RetryAnalyzer.class,dependsOnMethods = "testConnUseOnly")
	public void testConnNone()
	{
		getPermission=appPermission.changeConnPermission("none", driver);
		System.out.println(getPermission.get(0));
		boolean result  =Boolean.parseBoolean(getPermission.get(0));
		assertTrue(result);
		
	}
	@AfterMethod
	public void closeBrowser(ITestResult result)
	{
		try {
			if(ITestResult.FAILURE==result.getStatus())
			{
			new TakeScreenshots().takeScreenShot(result.getName(),"Permission",driver);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		driver.quit();
	}

}
