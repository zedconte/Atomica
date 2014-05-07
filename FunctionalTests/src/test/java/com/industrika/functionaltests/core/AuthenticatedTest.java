package com.industrika.functionaltests.core;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public abstract class AuthenticatedTest extends BaseTest {
	  
	  protected final String idContentTitle = "content-title";
	
	  @BeforeMethod
	  public void initSession(){
		  doLogin();
	  }
	  
	  @AfterMethod
	  public void closeSession(){
		  doLogout();
	  }
	 
}
