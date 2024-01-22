package com.base;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;



public class ReadExcelDatafromdataprovider 
{
	@DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {
        String filePath = "C:\\Users\\raghun.INFICS\\Desktop\\MDE Credentilas.xlsx";
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Sheet1");

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        System.out.println("Number of Rows: " + rowCount);
        System.out.println("Number of Columns: " + colCount);
        
        // Adjusting the size of data array to handle potential null values
        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            
            // Skipping null rows (blank or empty rows)
            if (row != null) {
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    data[i - 1][j] = cell.toString();
                }
            }
        }

        workbook.close();
        fis.close();

        return data;
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {
        // Your test logic using username and password
    	
    	System.setProperty("webdriver.chrome.driver", "D:/Documents/Automation/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://mde4vets-qa.lsgsmde.com/");

        driver.findElement(By.xpath("//input[@id='UserName']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@id='Password']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@id='btnLogin']")).click();

        // Add verification logic based on the behavior of the website after login

        // Close the browser
        driver.quit();
        System.out.println("Username: " + username + ", Password: " + password);
    }
}


