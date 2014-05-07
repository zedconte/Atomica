package com.industrika.commons.validation.predefined;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.industrika.commons.dto.Currency;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public class CurrencyValidatorPredefinedTest {
private CurrencyValidatorPredefined validator = new CurrencyValidatorPredefined();
	
	@Test
	public void testValidate() {
		Currency dto=new Currency();
		try{
			validator.validate(dto);
			fail("La validacion fallo, el dto no tiene datos");
		}catch(IndustrikaValidationException ex){
			assertTrue("El mensaje de error es incorrecto",ex.getMessage().indexOf("Nombre de la divisa") > 0);
		}
		try{
			dto.setName("Dollar");
			validator.validate(dto);
			fail("La validacion fallo, el dto no tiene datos");
		}catch(IndustrikaValidationException ex){
			assertTrue("El mensaje de error es incorrecto",ex.getMessage().indexOf("Nombre de la divisa") == -1);
		}
		try{
			dto.setShortName("Dll");
			validator.validate(dto);
			fail("La validacion fallo, el dto no tiene datos");
		}catch(IndustrikaValidationException ex){
			assertTrue("El mensaje de error es incorrecto",ex.getMessage().indexOf("Nombre de la divisa") == -1 && ex.getMessage().indexOf("Nombre corto de la divisa") == -1 );
		}
		try{
			dto.setSymbol("$");
			validator.validate(dto);
			assertTrue("",dto != null);
		}catch(IndustrikaValidationException ex){
			fail("La validacion fallo, el dto tiene todos los valores requeridos");
		}
	}
}
