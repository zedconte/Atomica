package com.industrika.commons.validation.predefined;

import static org.junit.Assert.*;

import org.junit.Test;

import com.industrika.commons.dto.Person;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public class PersonValidatorPredefinedTest {

	private PersonValidatorPredefined validator = new PersonValidatorPredefined();
	
	@Test
	public void testValidate() {
		Person dto=new Person();
		try{
			validator.validate(dto);
			fail("La validacion fallo, el dto no tiene datos");
		}catch(IndustrikaValidationException ex){
			assertTrue("El mensaje de error es incorrecto",ex.getMessage().indexOf("Nombre") > 0);
		}
		try{
			dto.setFirstName("Juan");
			validator.validate(dto);
			fail("La validacion fallo, el dto no tiene datos");
		}catch(IndustrikaValidationException ex){
			assertTrue("El mensaje de error es incorrecto",ex.getMessage().indexOf("Nombre") == -1 && ex.getMessage().indexOf("Paterno") > 0);
		}
		try{
			dto.setLastName("Perez");
			validator.validate(dto);
			fail("La validacion fallo, el dto no tiene datos");
		}catch(IndustrikaValidationException ex){
			assertTrue("El mensaje de error es incorrecto",ex.getMessage().indexOf("Nombre") == -1 && ex.getMessage().indexOf("Paterno") == -1 && ex.getMessage().indexOf("Sexo") > 0);
		}
		try{
			dto.setGender("M");
			validator.validate(dto);
			assertTrue("",dto != null);
		}catch(IndustrikaValidationException ex){
			fail("La validacion fallo, el dto tiene todos los valores requeridos");
		}
	}

}
