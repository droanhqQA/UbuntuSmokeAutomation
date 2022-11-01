package testcases;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.utils.TakeScreenshots;

import TestJenkins.testjenkins.AppPermission;
import TestJenkins.testjenkins.UserLogin;

public class PermissionTest {
	ChromeDriver driver;
	FileInputStream fs;
	AppPermission appPermission;
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
		final URL resource = PermissionTest.class.getResource("/Automation.xlsx");
		System.out.println("file  "+resource);
		fs = new FileInputStream("/"+(resource.toString().substring("file:/".length(), resource.toString().length())));


		// ConDetailDAO detailDAO = new ConDetailDAO(fs, workbook, con_sheet);

		String u_name = "brijesh@studio.com";
		String u_pass = "qwerty";
		
		// System.out.println(detailDAO.connectionName()+"\n"+detailDAO.connectionString());
		final URL driver_path = PermissionTest.class.getResource("/chromedriver");
		System.out.println("driver:  "+driver_path);

		System.setProperty("webdriver.chrome.driver","/"+
				(driver_path.toString().substring("file:/".length(), driver_path.toString().length())));
		
				ChromeOptions options = new ChromeOptions();
				//options.addArguments("--headless");
				options.addArguments("--window-size=1920,1080");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-setuid-sandbox");
				options.setAcceptInsecureCerts(true);

		driver = new ChromeDriver(options);
		
		//driver = new ChromeDriver();
		UserLogin user = new UserLogin(driver);
		appPermission = new AppPermission();
		getPermission = new ArrayList<String>();
		driver.get("https://ubuntu.onprem.dronahq.com/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		// driver.manage().timeouts().pageLoadTimeout(-1,TimeUnit.SECONDS);
		//driver.manage().window().maximize();
		user.login(u_name, u_pass);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}
	
	@Test
	
	public void testEditor()
	{
		getPermission=appPermission.changePermission("default-editor", driver);
		System.out.println(getPermission.get(0)+" "+getPermission.get(1));
		boolean result = getPermission.get(0).contains(" Edit Details Update app") &&
						 getPermission.get(1).contentEquals("0010"); 
		assertTrue(result);
	}
	
	@Test
	public void testPreviewOnly()
	{
		getPermission=appPermission.changePermission("default-useonly", driver);
		System.out.println(getPermission.get(0)+" "+getPermission.get(1));
		boolean result = getPermission.get(0).contains("icon-info app-owner-popup")&&
						 getPermission.get(1).contentEquals("0001");
		assertTrue(result);
	}
	
	@Test
	public void testNone()
	{
		getPermission=appPermission.changePermission("default-none", driver);
		System.out.println(getPermission.get(0)+" "+getPermission.get(1));
		boolean result=getPermission.get(0).contains("No Apps Found") &&
					   getPermission.get(1).contains("0000");
		assertTrue(result);
	}
	
	@AfterMethod
	public void tearDown(ITestResult result, ITestContext testContext) {
		
			try {
				if (ITestResult.FAILURE == result.getStatus()) {
					System.out.println(testContext.getName() + " " + result.getMethod().getMethodName());
					new TakeScreenshots().takeScreenShot(testContext.getName() + "_" + result.getMethod().getMethodName(),
							"Permission", driver);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		driver.quit();
	}
}
