package testcases;

import org.testng.annotations.Test;

import com.dao.UserDAO;
import com.dao.UserLogin;
import com.manageusers.AddCheckUsers;
import com.utils.Base_Class;
import com.utils.TakeScreenshots;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.AssertJUnit;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class AddUserTest extends Base_Class
{
//	ChromeDriver driver ;
//	FileInputStream fs ;
//	String email,pass;
	AddCheckUsers checkUsers;
	public String new_email;
	
	@BeforeMethod
	public void setUp() throws IOException
	{
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		System.out.println(currentTimestamp);
//		final URL driver_path = AddUserTest.class.getResource("/chromedriver.exe");
//	    System.out.println(driver_path);
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vinayak Hene\\Desktop\\DronaHQ\\Browser\\chromedriver.exe");
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("headless");					
//		options.setAcceptInsecureCerts(true);
//	    options.addArguments("window-size=1920,800");
//	    driver = new ChromeDriver(options);
//		driver.get("https://ubuntu.onprem.dronahq.com/apps");
//		driver.manage().window().maximize();
		
		BrowserSetUp();
		
		checkUsers = new AddCheckUsers(driver);
		
	 
	}
	//@Ignore
	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void AddUser()
	{	
		new_email = checkUsers.generateEmail();
		String checkEmail = checkUsers.addCheckUser(new_email);
		System.out.println(checkEmail.contains(new_email));
		AssertJUnit.assertEquals(checkEmail, new_email);
	}
	//@Ignore
	@Test(dependsOnMethods = "AddUser" )
	public void LoginUser()
	{
		
		System.out.println("Email: "+new_email);
		UserLogin user = new UserLogin(driver);
		user.login(new_email,"Test@123");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//div[@class='new-profile-icon']")).click();
		String emailAfter = driver.findElement(By.xpath("//div[@class='profile-email']")).getText();
		System.out.println( new_email+"\n"+emailAfter);
		AssertJUnit.assertEquals(emailAfter, new_email);
	}
	@AfterMethod
	public void closeBrowser(ITestResult result)
	{
		try {
			if(ITestResult.FAILURE==result.getStatus())
			{
			new TakeScreenshots().takeScreenShot(result.getName(),"ManageUser",driver);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();
	}
}
