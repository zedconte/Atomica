package com.industrika.functionaltests.maintenance;

import org.testng.annotations.BeforeMethod;

import com.industrika.functionaltests.core.AuthenticatedTest;

public abstract class MaintenanceBaseTest extends AuthenticatedTest {
	
	  private final String idMaintenanceMenuHeader = "maintenance-menu-header";
	  
	  @BeforeMethod
	  public void initTest(){
		  clickById(idMaintenanceMenuHeader, "No carga el Menu Maintenance");
		  
	  }
}
