package com.sophos.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.sophos.src.LeerExcel;

public class TestExcel {
	public static WebDriver driver;
	public LeerExcel leerExcel;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\SELENIUM\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() {
		//driver.get("http://www.google.com.co");
		try {
			leerExcel = new LeerExcel(driver);
			leerExcel.search();
			leerExcel.closeStreams();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
