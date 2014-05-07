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
		  personMenuItem = waitById(idPersonMenuItem, "No se encontró elemento de Catálogo de Personas.");
	  }
	  
		  
	  @Test
	  public void ShouldShowMenuItemPersons(){
		  	  
		  assertThat("El texto del menu no es correcto", personMenuItem.getText(), containsString("Catálogo de Personas"));
	  }
	  
	  @Test(dependsOnMethods={"ShouldShowMenuItemPersons"})
	  public void ShouldShowPersonsForm(){
		  personMenuItem.click();
		  WebElement titleTable = waitById(idContentTitle, "No se desplegó la Tabla de Cátalogo de Persona");
		  assertThat("El titulo de la tabla no es correcto",titleTable.getText(), containsString("Catálogo de Personas"));
	  }
	  
	  
	  
}
