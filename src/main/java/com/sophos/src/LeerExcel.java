package com.sophos.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.BasePage;

public class LeerExcel {
	
	private FileInputStream inputStream;
	private Workbook workbook;
	private FileOutputStream outputStream;
	private WebDriver driver;
	private BasePage basePage;

	private String filePath = "C:\\SELENIUM\\Prueba.xlsx";
	private String webPage =  "http:\\www.google.com.co";
	private By inputSearch = By.xpath("//input[@title='Buscar']");
	private By searchResult = By.id("search");
	private By searchResultsDiv = By.xpath("//p[@aria-level='3']");
	
	public LeerExcel(WebDriver driver) {
		this.driver = driver;
		basePage = new BasePage(driver);
	}
	
	public void search() {
		try {
			inputStream = new FileInputStream(new File (filePath));
			workbook =  WorkbookFactory.create(inputStream);
			Sheet primeraHoja = workbook.getSheetAt(0);
			Iterator<?> iterador = primeraHoja.iterator();
			
			DataFormatter formatter = new DataFormatter();
	        while (iterador.hasNext()) {
	            Row fila = (Row) iterador.next();
	            
	            Iterator<Cell> cellIterator = fila.cellIterator();
	            
	            if( fila.getRowNum() != 0 ) {
	            	 while(cellIterator.hasNext()) {
	                     Cell celda = (Cell) cellIterator.next();
	                   
	                     String contenidoCeldaBusqueda = formatter.formatCellValue(fila.getCell(0));
	                   
	                     driver.get(webPage);
	                     
	                     basePage.writeText(inputSearch, contenidoCeldaBusqueda);
	                     basePage.submit(inputSearch);
	            
	             		 WebElement element = basePage.waitPresenceOfElement(searchResult);
	             		 		
	             		
	             		 Dimension sizeContenedorResultado =  element.getSize();	             		
	             		 Boolean sinResultado = basePage.sizeElements(searchResultsDiv) > 0 ;

	             		 if(sinResultado && (sizeContenedorResultado.getHeight() == 0)) {
	             			fila.createCell(1).setCellValue("No");
	             		 }else {
	             			fila.createCell(1).setCellValue("Si");
	             		 }
	             		 
	             		
	                 }
	            }	
	        }
	        outputStream =new FileOutputStream(new File(filePath));
	 		
				workbook.write(outputStream);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void closeStreams() throws Exception {
		inputStream.close();
        outputStream.close();
        System.out.println("Done");
	}
	
	


}