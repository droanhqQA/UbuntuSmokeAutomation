package com.dao;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UserDAO {
	FileInputStream fs;
	XSSFWorkbook workbook;
	XSSFSheet sheet;


	public UserDAO(FileInputStream fs, XSSFWorkbook workbook, XSSFSheet sheet) {
		super();
		this.fs = fs;
		this.workbook = workbook;
		this.sheet = sheet;
	}
	public String getU_name() {
		return sheet.getRow(0).getCell(1).toString();
	}
	public String getU_pass() {
		return sheet.getRow(1).getCell(1).toString();
	}
	
	
}
