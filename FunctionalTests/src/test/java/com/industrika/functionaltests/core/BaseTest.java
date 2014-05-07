package com.industrika.functionaltests.core;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public abstract class BaseTest {
	 
	 protected WebDriver driver;
	 protected int browserTimeout = 5; 
	 
	 protected final String idErrorMessage = "error-text";
	  
	  @BeforeTest
	  @Parameters ({"browser_type", "browser_timeout"})
	  public void setUp(String browser_type ,@Optional("5") int timeout ) {
		driver=WebDriverManager.startDriver(browser_type);
		browserTimeout = timeout;
	  }
	  
	  @AfterTest
	  public void tearDown() {
		WebDriverManager.stopDriver();
	  }
	  
	  @BeforeMethod
		public void initMainTest(){
			gotoMainPage();
		}
	  
	  protected void gotoMainPage(){
		  driver.get("http://localhost:8080/IndustrikaSIE/main");
	  }
	  
	  protected void doLogin(){
		 doLogin("Industrika","Test123");
	  }
	  
	  protected void doLogin(String user, String pass){
		  driver.findElement(By.id("sieuserid")).sendKeys(user);
		  driver.findElement(By.id("sieuserpass")).sendKeys(pass);
		  driver.findElement(By.name("blogin")).submit();
	  }
	  
	  protected void doLogout(){
		  driver.findElement(By.name("blogout")).submit();
	  }
	  
	  protected void deleteAllCookies(){
		  driver.manage().deleteAllCookies();
	  }
	  
	  protected WebDriverWait initWait(String message){
		  WebDriverWait wait = new WebDriverWait(driver, browserTimeout);
		   wait.withMessage(message);
		   return wait;
	  }
	  
	  protected WebElement waitById(String id, String message){
		  return waitBy(By.id(id), message);
	  }
	  
	  protected WebElement waitByName(String name, String message){
		  return waitBy(By.name(name), message);
	  }
	  
	  protected WebElement waitByXpath(String path, String message){
		return waitBy(By.xpath(path), message);
	  }
	  
	  protected WebElement waitByClass(String className, String message){
			return waitBy(By.className(className), message);
	  }
	  
	  protected WebElement waitBy(By by, String message){
		  return initWait(message).until(ExpectedConditions.visibilityOfElementLocated(by));
	  }
	  
	  
	  protected void checkErrorMessage(String errorMessage){
			WebElement errMessage = waitById(idErrorMessage, "No se desplegó el mensaje de error");
			assertThat("El mensaje de error es incorrecto", errMessage.getText(), equalTo(errorMessage));
		}
	  
	  protected void clickWithPause(WebElement element, int pause){
		  try {
				Thread.sleep(pause);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  element.click();
	  }
	  protected void assertImage(WebElement img, String imagen){
		  
		  if (driver instanceof InternetExplorerDriver) { 
			  boolean complete = Boolean.valueOf(img.getAttribute("complete"));
		      assertThat("La imagen "+imagen+" no esta siendo mostrada.", complete, is(true));
		    }
		    else
		    {
		     int width = Integer.valueOf(img.getAttribute("naturalWidth"));
		     assertThat("La imagen "+imagen+" no esta siendo mostrada.", width>0, is(true));
		    }
		    
	 }
	  
	  protected void assertImageByClass(String className){
		WebElement img = waitByClass(className,"No alcanza a cargar la imagen.");
		assertImage(img, className);
	  }
	  
	  protected WebElement performSearch(String nameField, String buttonToClick, String wordToSearch){
		  sendKeysById(nameField, wordToSearch);
		  clickByClass(buttonToClick);
		  WebElement elemResultTable = waitByClass("resultsTable", "No se desplegó la tabal con el Resultado de la búsqueda.");
		  return elemResultTable;
	  }
	  
	  protected WebElement waitClickable(By by, String message){
		  return initWait(message).until(ExpectedConditions.elementToBeClickable(by));
	  }
	  
	  protected void clickById(String id, String message){
		  WebElement element = waitClickable(By.id(id), message);
		  element.click();
	  }
	  
	  protected void clickByClass(String className){
		  String message = "No se puede dar click a ese elemento.";
		  WebElement clickElement = waitClickable(By.className(className), message);
		  clickElement.click();
	  }
	  
	  protected void sendKeysById(String id, String word){
		  String message = "No se encuentra el campo buscado.";
		  WebElement sendKeysElement = waitBy(By.className(id), message);
		  sendKeysElement.sendKeys(word);
	  }
	  
	  
	  
}
