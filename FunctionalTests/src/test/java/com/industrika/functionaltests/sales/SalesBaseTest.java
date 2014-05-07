package com.industrika.functionaltests.sales;

import org.testng.annotations.BeforeMethod;

import com.industrika.functionaltests.core.AuthenticatedTest;

public abstract class SalesBaseTest extends AuthenticatedTest {
	
	  private final String idSalesMenuHeader = "sales-menu-header";
	  
	  @BeforeMethod
	  public void initTest(){
		  clickById(idSalesMenuHeader, "No carga el Menu Sales");
		  
	  }
}
