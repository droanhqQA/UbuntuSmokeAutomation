package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utility_Class 
  {
		
	static FileInputStream FilePath;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	
//  This method is use to get test data from excel sheet
//  Need to provide 3 inputs: 1- sheetIndex 2-rowIndex 3-colIndex
	
	public static  String GetExcelData(int sheetIndex, int rowIndex, int celIndex  ) throws IOException
  {
		 FilePath= new FileInputStream ("C:\\Users\\Vinayak Hene\\eclipse-workspace\\UbuntuSmokeAutomation123\\src\\main\\resources\\Automation.xlsx");
		 workbook=new XSSFWorkbook(FilePath);
	     sheet =workbook.getSheetAt(sheetIndex);
		 String value = sheet.getRow(rowIndex).getCell(celIndex).toString();
		
		 return value;
	}
	
   }
