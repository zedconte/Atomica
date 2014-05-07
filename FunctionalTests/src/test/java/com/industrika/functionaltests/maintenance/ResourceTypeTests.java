package com.industrika.functionaltests.maintenance;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.hamcrest.number.IsCloseTo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;


public class ResourceTypeTests extends MaintenanceBaseTest {
	
private final String idResourceTypeMenuItem = "resourceType-menu-item";

	
	private WebElement ResourceTypeMenuItem;
	
	private final String nameField = "name-field-input";
	private final String searchButton = "searchbutton";
	private String wordToSearch = "";
	
	
	@BeforeMethod
	  public void initMaintenanceTest(){
		ResourceTypeMenuItem = waitById(idResourceTypeMenuItem, "No se encontró elemento de Tipo de recursos.");
		//System.out.println(MaintenanceMenuItem.toString());
	  }
	
	@Test
	  public void ShouldShowMenuItemResourceTypes(){
		  	  
		  assertThat("El texto del menu no es correcto", ResourceTypeMenuItem.getText(), containsString("Tipos de recurso"));
	  }
	
	@Test(dependsOnMethods={"ShouldShowMenuItemResourceTypes"})
	  public void ShouldShowResourceTypesForm(){
		clickWithPause(ResourceTypeMenuItem, 200);
		  
		  WebElement titleTable = waitById(idContentTitle, "No se desplegó la Tabla de Tipos de Recurso");
		  assertThat("El titulo de la tabla no es correcto",titleTable.getText(), containsString("Tipos de recurso"));
	  }
	
	@Test(dependsOnMethods={"ShouldShowMenuItemResourceTypes"})
	  public void SearchByNameResourceTypes(){
		wordToSearch = "Gub";
		clickWithPause(ResourceTypeMenuItem, 200);
		WebElement elemResultTable = performSearch(nameField, searchButton, wordToSearch);
		assertThat("El elemento buscado no es mostrado correctamente.", elemResultTable.getText(), containsString(wordToSearch));
	  }
	
	@Test(dependsOnMethods={"ShouldShowMenuItemResourceTypes"})
	  public void ShouldShowResourceTypesImgResetButton(){
		clickWithPause(ResourceTypeMenuItem, 500);
		assertImageByClass("resetbutton");
	  }

	
}
