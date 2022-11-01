package com.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenshots {
	static String FILEPATH = ".\\screenshot";
	public void takeScreenShot(String testName,String className, WebDriver driver) {
		// TODO Auto-generated method stub
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		System.out.println("Test Case Name: "+testName);
		//The below method will save the screen shot in path mentioned with test method name 
           try {
				FileUtils.copyFile(scrFile, new File(FILEPATH+"\\"+className+"\\"+testName+".png"));
				System.out.println("***Placed screen shot in "+FILEPATH+" ***");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
