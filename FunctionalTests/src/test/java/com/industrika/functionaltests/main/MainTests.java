package com.industrika.functionaltests.main;

import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.industrika.functionaltests.core.BaseTest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;



public class MainTests extends BaseTest {
	
	
	private final String messageInvalidCredentials = "Nombre de Usuario o Password Erróneo";
	
	
		
	@Test(timeOut=20000, groups={"main"})
	 public void ShouldLogin(){
		 
		 doLogin();
		 
		 checkLogin(true);
		 
		 doLogout();
	 }
	
	@Test(timeOut=20000,dependsOnMethods={"ShouldLogin"})
	 public void ShouldLogout(){
		doLogin();
		
		doLogout();
		
		waitByName("blogin", "El sistema no dejó salir correctamente.");
	 }
	
	
	@Test(timeOut=20000,dependsOnMethods={"ShouldLogin", "ShouldLogout"}, dataProvider="invalidCredentials")
	 public void ShouldFailWithInvalidCredentials(String user, String pass){
		
		
		doLogin(user,pass);
		
		checkLogin(false);
		checkErrorMessage(messageInvalidCredentials);
		
		
	 }
	
	
	
	@DataProvider(name="invalidCredentials")
	public Object[][] getInvalidCredentials(){
		return new Object[][]{
				{ "", "" },
				{ "", "pass" },
				{ "user", "" },
				{ "wronguser", "pass" },
				{ "user", "wrongpass" },
				{ "wronguser", "wrongpass" },
				
			};
	}
	
	
	private void checkLogin(boolean isSuccess){

		if (isSuccess){
			waitByName("blogout", "El sistema no dejo entrar con user/pass autorizado.");
		} else {
			try{
				initWait("El sistema dejo entrar con user/pass no autorizado.").until(ExpectedConditions.invisibilityOfElementLocated(By.name("blogout")));
			}
			catch(TimeoutException ex){
				doLogout();
				throw ex;
			}
		}
			
	  }
}
