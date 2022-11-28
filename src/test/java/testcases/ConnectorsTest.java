package testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.connectors.*;

import com.dao.UserDAO;
import com.dao.UserLogin;
import com.utils.TakeScreenshots;

public class ConnectorsTest {
	AddingConnector connector;
	ChromeDriver driver;
	FileInputStream fs;

	String testName = "";

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
		fs = new FileInputStream("/"+(resource.toString().substring("file:/".length(), resource.toString().length())));

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
				"/var/lib/jenkins/driver/chromedriver");
		
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				options.addArguments("--window-size=1920,1080");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-setuid-sandbox");
				options.setAcceptInsecureCerts(true);
						//options.addArguments("--remote-debugging-port=9222");
//						options.addArguments("disable-gpu");
//						ChromeDriver driver = new ChromeDriver(options);

		driver = new ChromeDriver(options);
		
		//driver = new ChromeDriver();
		UserLogin user = new UserLogin(driver);

		connector = new AddingConnector(driver);
		
		driver.get("https://ubuntu.onprem.dronahq.com/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		// driver.manage().timeouts().pageLoadTimeout(-1,TimeUnit.SECONDS);
		//driver.manage().window().maximize();
		user.login(u_name, u_pass);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// connector.connectorDetails(con_string);
		connector.navigatetoConnector();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}
//@Ignore
	//,retryAnalyzer = RetryAnalyzer.class
	@Test(priority = 1)
	@Parameters({ "sheet", "db" })
	public void connectionTest(String sheet, String DB) {
		System.out.println("Inside Connection Test " + " " + Integer.parseInt(sheet));
		int sheet_no = Integer.parseInt(sheet);
		try {
			connector.connectorDetails(DB, sheet_no, "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String actual, expected, actual_xpath;
		expected = "Connection successful! You can go ahead and add your connector and create queries.";
		actual_xpath = "//div[@class='p-4 pb-0 testing-messages']/div[3]/div/div/div";
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.textToBe(By.xpath(actual_xpath), expected));
		actual = driver.findElement(By.xpath(actual_xpath)).getText();
		AssertJUnit.assertEquals(expected, actual);

	}

	//,retryAnalyzer = RetryAnalyzer.class
	// @Ignore
	@Parameters({ "db", "sheet" })
	@Test(priority = 2,retryAnalyzer = RetryAnalyzer.class)
	public void saveConnection(String db, String sheet) {

		// connector.connectorDetails("");
		int sheet_no = Integer.parseInt(sheet);
		String res = "";
		try {
			res = connector.connectorDetails(db, sheet_no, "save");
			System.out.println(res + ": inside save connection try catch");
			String expected = "true";
			AssertJUnit.assertEquals(res.trim(), expected);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
//,retryAnalyzer = RetryAnalyzer.class
	//@Ignore
	// (dependsOnMethods = "saveConnection")
	@Test(priority = 3,dependsOnMethods = "saveConnection",retryAnalyzer = RetryAnalyzer.class)
	@Parameters({ "db", "sheet" })
	public void addQuery(@Optional String db, String sheet) throws InterruptedException, IOException {
		// fs = new FileInputStream("D:\\Nitin\\DronaDeletecs\\Automation.xlsx");
		final URL resource = ConnectorsTest.class.getResource("/Automation.xlsx");
		System.out.println(resource);
		fs = new FileInputStream((resource.toString().substring("file:/".length(), resource.toString().length())));
		int sheetNo = Integer.parseInt(sheet);
		System.out.println("Sheet number: " + sheetNo);
		if (db.equalsIgnoreCase("mongo")) {
			System.out.println("Mongo");
			new AddQuery(fs, sheetNo).mongoQuery(driver);
		} else {
			System.out.println("Postgre");
			new AddQuery(fs, sheetNo).postgreQuery(driver);
		}

		driver.manage().timeouts().implicitlyWait(25, TimeUnit.MILLISECONDS);
		@SuppressWarnings("deprecation")
//		WebDriverWait wait = new WebDriverWait(driver, 20);
//		wait.until(ExpectedConditions.textToBe(By.xpath("//div[@class='hq-card p-4 messages-container']/descendant::div[@class='hq-title']"), "Configuration test successful."));
		String queryTest = driver
				.findElement(
						By.xpath("//div[@class='hq-card p-4 messages-container']/descendant::div[@class='hq-title']"))
				.getText();
		System.out.println(queryTest);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@class='hq-primary-btn d-flex align-center pointer savequery']")).click();

		driver.findElement(By.xpath("//header/div[2]/a[2]")).click();// cross-button

	}

	@Test(retryAnalyzer = RetryAnalyzer.class)
	@Parameters({ "sheet", "db" })
	public void connectionTestApi(int sheetNo, String db) {
		String res;
		if (db.equalsIgnoreCase("graphql"))
			res = connector.connectorDetails_graphql(sheetNo, "N");
		else
			res = connector.connectorDetails_api(sheetNo, "N");
		AssertJUnit.assertEquals(res, "true");
	}
//retryAnalyzer = RetryAnalyzer.class
	@Test(retryAnalyzer = RetryAnalyzer.class)
	@Parameters({ "sheet", "db" })
	public void connectionSaveApi(int sheetNo, String db) {
		String res;
		if (db.equalsIgnoreCase("graphql"))
			res = connector.connectorDetails_graphql(sheetNo, "save");
		else
			res = connector.connectorDetails_api(sheetNo, "save");
		AssertJUnit.assertEquals(res, "true");
	}
//(retryAnalyzer = RetryAnalyzer.class)
	@Test(retryAnalyzer = RetryAnalyzer.class,dependsOnMethods = "connectionSaveApi")
	@Parameters({ "sheet", "db" })
	public void connectionAddApi(int sheetNo, String db) {
		String res;
		if (db.equalsIgnoreCase("graphql"))
			res = connector.connectorDetails_graphql(sheetNo, "add");
		else
			res = connector.connectorDetails_api(sheetNo, "add");
		AssertJUnit.assertEquals(res, "true");
	}
	
	@AfterMethod
	public void driverClose(ITestResult result, ITestContext testContext) {
		try {
			if (ITestResult.FAILURE == result.getStatus()) {
				System.out.println(testContext.getName() + " " + result.getMethod().getMethodName());
				new TakeScreenshots().takeScreenShot(testContext.getName() + "_" + result.getMethod().getMethodName(),
						"Connectors", driver);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();
	}

}
