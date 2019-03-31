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

public class LeerExcel {
	
	private FileInputStream inputStream;
	private Workbook workbook;
	private FileOutputStream outputStream;
	private WebDriver driver;
	
	public LeerExcel(WebDriver driver) {
		this.driver = driver;
	}
	
	public void search() {
		try {
			inputStream = new FileInputStream(new File ("C:\\SELENIUM\\Prueba.xlsx"));
			workbook =  WorkbookFactory.create(inputStream);
			Sheet primeraHoja = workbook.getSheetAt(0);
			Iterator iterador = primeraHoja.iterator();
			
			DataFormatter formatter = new DataFormatter();
	        while (iterador.hasNext()) {
	            Row fila = (Row) iterador.next();
	            //System.out.println("row number : " + fila.getRowNum());
	            Iterator cellIterator = fila.cellIterator();
	            
	            if( fila.getRowNum() != 0 ) {
	            	 while(cellIterator.hasNext()) {
	                     Cell celda = (Cell) cellIterator.next();
	                     //System.out.println("column index: " + celda.getColumnIndex());
	                     String contenidoCeldaBusqueda = formatter.formatCellValue(fila.getCell(0));
	                     //System.out.println("celda: " + contenidoCeldaBusqueda);
	                     driver.get("http:\\www.google.com.co");
	             		 driver.findElement(By.name("q")).sendKeys(contenidoCeldaBusqueda);;
	             		 driver.findElement(By.name("btnK")).submit();
	             		 WebElement element = (new WebDriverWait(driver, 10))
	          				  .until(ExpectedConditions.presenceOfElementLocated((By.id("search"))));
	             		 		
	             		 //System.out.println("Element size: " + element.getSize());
	             		 Dimension sizeContenedorResultado =  element.getSize();
	             		 //System.out.println("Element size Height: " + sizeContenedorResultado.getHeight());
	             		 Boolean sinResultado = driver.findElements(By.xpath("//p[@aria-level='3']")).size() > 0 ;
	             		 //System.out.println("Element exist: " + sinResultado);
	             		 if(sinResultado && (sizeContenedorResultado.getHeight() == 0)) {
	             			fila.createCell(1).setCellValue("No");
	             		 }else {
	             			fila.createCell(1).setCellValue("Si");
	             		 }
	             		 
	             		
	                 }
	            }	
	        }
	        outputStream =new FileOutputStream(new File("C:\\SELENIUM\\Prueba.xlsx"));
	 		
				workbook.write(outputStream);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void closeStreams() throws Exception {
		inputStream.close();
        outputStream.close();
        System.out.println("Done");
	}
	
	


}