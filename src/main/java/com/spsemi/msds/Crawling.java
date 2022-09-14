package com.spsemi.msds;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Crawling {
	private WebDriver driver;
	final String url = "https://occupationalhealthblog.net/msds/";
	
	public void process() {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		// 크롬 드라이버 셋팅 (드라이버 설치한 경로 입력)

		driver = new ChromeDriver();
		// 브라우저 선택
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("msds");
		XSSFRow sheetRow;
		try {
			getFile();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.close(); // 탭 닫기
		driver.quit(); // 브라우저 닫기

		Path file = Paths.get("C:\\Users\\csg\\Downloads\\법적규제정보-2022-09-14.csv");
		Path newFile = Paths.get("C:\\Users\\csg\\Downloads\\법적규제정보-2022-09-14.txt");

		try {
			Path newFilePath = Files.move(file, newFile);
			System.out.println(newFilePath); // D:\example\new_image.jpg
		} catch (IOException e) {
			e.printStackTrace();
		}

		File txtFile = new File("C:\\Users\\csg\\Downloads\\법적규제정보-2022-09-14.txt");
		String xlsxFile = "C:\\Users\\csg\\Downloads\\법적규제정보-2022-09-14.xlsx";
		
		String[] splitedStr = null;
		try {
			// 한글 깨짐현상 때문에 인코딩
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile)));
			FileOutputStream fos = new FileOutputStream(xlsxFile);
			
			String line = null;
			splitedStr = null;
			int iRow = 0;
			while ((line = reader.readLine()) != null) {
				sheetRow = sheet.createRow(iRow);
				splitedStr = null;
				splitedStr = line.split(",");
				System.out.println(splitedStr.length);
				for (int i = 0; i < splitedStr.length; i++) {
					splitedStr[i] = splitedStr[i].replace("\"","").trim();
					sheetRow.createCell(i).setCellValue(splitedStr[i]);
					System.out.println(splitedStr[i]);
				}
				iRow++;
				// 자른 데이터를 원하는 형식에 맞게 넣기
				//saleList.add(new Product(splitedStr[0], splitedStr[1], Integer.valueOf(splitedStr[2])));
			}
			workbook.write(fos);
			fos.close();
			reader.close();
			
			
			
			
		} catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * data가져오기
	 */
	private void getFile() throws InterruptedException {

		driver.get(url); // 브라우저에서 url로 이동한다.
		Thread.sleep(5000); // 브라우저 로딩될때까지 잠시 기다린다.

		String value = "88-73-3|71-43-2|108-88-3";


		driver.findElement(By.id("casInputNoCheck")).click();
		driver.findElement(By.id("casInputNoCheck")).sendKeys(value);
		driver.findElement(By.id("casInputNoCheck")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("lawTableButton")).click();
		Thread.sleep(5000); // 브라우저 로딩될때까지 잠시 기다린다.
		driver.findElement(By.id("downloadFileLawTable_bttn")).click();
		Thread.sleep(5000); // 브라우저 로딩될때까지 잠시 기다린다.

	}

}
