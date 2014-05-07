package com.industrika.functionaltests.sales;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.WebElement;



import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.*;


public class PersonTests extends SalesBaseTest {
	
	private final String idPersonMenuItem = "person-menu-item";
	
	private WebElement personMenuItem;
	
	  @BeforeMethod
	  public void initPersonTest(){
		  personMenuItem = waitById(idPersonMenuItem, "No se encontr� elemento de Cat�logo de Personas.");
	  }
	  
		  
	  @Test
	  public void ShouldShowMenuItemPersons(){
		  	  
		  assertThat("El texto del menu no es correcto", personMenuItem.getText(), containsString("Cat�logo de Personas"));
	  }
	  
	  @Test(dependsOnMethods={"ShouldShowMenuItemPersons"})
	  public void ShouldShowPersonsForm(){
		  personMenuItem.click();
		  WebElement titleTable = waitById(idContentTitle, "No se despleg� la Tabla de C�talogo de Persona");
		  assertThat("El titulo de la tabla no es correcto",titleTable.getText(), containsString("Cat�logo de Personas"));
	  }
	  
	  
	  
}
