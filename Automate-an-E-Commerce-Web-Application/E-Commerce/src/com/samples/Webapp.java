package com.samples;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Function;

@Test
public class Webapp {

	WebDriver wd;

	@BeforeClass
	public void startBrowser() throws InterruptedException{

		System.setProperty("webdriver.chrome.driver",
				  "D:\\phase 5\\chromedriver_win32/chromedriver.exe");
		wd = new ChromeDriver();

		//System.setProperty("webdriver.edge.driver",
		//"D:\\phase 5\\edgedriver_win64/msedgedriver.exe");
		//wd = new EdgeDriver();

		wd.get("https://www.flipkart.com/");
		wd.manage().window().maximize();

	}

	@AfterClass
	public void closeBrowser() throws InterruptedException {
		wd.close();
	}
	
	@Test(priority = 1)
	public void loadtime() throws InterruptedException {
		long s = System.currentTimeMillis();
		WebDriverWait wait = new WebDriverWait(wd, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.className("L0Z3Pu")));
		long e = System.currentTimeMillis();
		long r = e - s;
	
		System.out.println("load time in milliseconds: " + r);
	

	}
	
	@Test(priority = 2)
	public void search() throws InterruptedException {
		// Search
		wd.findElement(By.cssSelector("._2IX_2-.VJZDxU")).sendKeys(Keys.ESCAPE);
		Thread.sleep(2000);
		wd.findElement(By.className("_3704LK")).sendKeys("iPhone 13");
		Thread.sleep(2000);
		wd.findElement(By.className("_3704LK")).sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		// Check if image loaded
		try {

			boolean image3 = wd.findElement(By.xpath("//img[contains(@src, 'https://rukminim1.flixcart.com/image/312/312/ktketu80/mobile/a/m/7/iphone-13-mlpj3hn-a-apple-original-imag6vpyk3w4zarg.jpeg?q=70')]")).isDisplayed(); 

			//WebElement image3 = wd.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[1]/div[2]/div[4]/div/div/div/a/div[1]/div[1]/div/div/img"));



	
			System.out.println("Image 3 is loaded.");
	
		} catch (org.openqa.selenium.NoSuchElementException e) {
		
			System.out.println("Image 3 is not loaded.");
			
		}

	}
	
	@Test(priority = 3)
	public void scroll() throws InterruptedException {

		String JS_ELEMENT_IS_SCROLLABLE = "return arguments[0].scrollHeight > arguments[0].offsetHeight;";

		JavascriptExecutor jse = (JavascriptExecutor) wd;

		WebElement container = wd.findElement(By.xpath("//div[@id='container']"));
		Boolean isScrollable = (Boolean) jse.executeScript(JS_ELEMENT_IS_SCROLLABLE, container);

		if (isScrollable == true) {
	
			System.out.println("Scroll bar is present");
		
		} else {
			
			System.out.println("Scroll bar is absent.");
		
		}
		
		
		
		
		// Checking frequency of refresh 
		Wait<WebDriver> wait = new FluentWait<WebDriver>(wd).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver wd) {
				return wd.findElement(By.cssSelector("._396cs4._3exPp9"));
			}
		});
	
		System.out.println("Checking the frequency of refresh every 5s for 30s.");


		
		
		// Verify image is downloaded just before scrolling	
		WebElement element = wd.findElement(By.xpath("//img[contains(@src, 'https://rukminim1.flixcart.com/image/312/312/ktketu80/mobile/a/m/7/iphone-13-mlpj3hn-a-apple-original-imag6vpyk3w4zarg.jpeg?q=70')]"));
		boolean dispBefore = element.isDisplayed();
		
		
		
		if (dispBefore == true) {
		System.out.println("Before Scrolling : Image downloaded.");
		}
		else {
			System.out.println("Before Scrolling : Image not downloaded.");
		}
		
		jse.executeScript("arguments[0].scrollIntoView(true);",element);
		boolean dispAfter = element.isDisplayed();
		
		if (dispAfter == true) {
			System.out.println("After Scrolling : Image downloaded.");
		}
		else {
			System.out.println("After Scrolling : Image not downloaded.");
		}
		
		
		Thread.sleep(2000);
	
		
		
		// scroll to the bottom
		Thread.sleep(2000);
		
		System.out.println("Scrolling to the end of the page.");

		jse.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
	}

}