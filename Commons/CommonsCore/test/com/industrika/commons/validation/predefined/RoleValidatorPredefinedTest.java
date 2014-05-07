package com.industrika.commons.validation.predefined;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.industrika.commons.dto.Role;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;

public class RoleValidatorPredefinedTest {

	private RoleValidatorPredefined validator = new RoleValidatorPredefined();
	
	@Test
	public void testValidate() {
		Role dto=new Role();
		try{
			validator.validate(dto);
			fail("La validacion fallo, el dto no tiene datos");
		}catch(IndustrikaValidationException ex){
			assertTrue("El mensaje de error es incorrecto",ex.getMessage().indexOf(CommonsMessages.getMessage("name")) > 0);
		}
		try{
			dto.setName("Rol");
			validator.validate(dto);
			fail("La validacion fallo, el dto no tiene datos");
		}catch(IndustrikaValidationException ex){
			assertTrue("El mensaje de error es incorrecto",ex.getMessage().indexOf(CommonsMessages.getMessage("name"))== -1 && 
					ex.getMessage().indexOf(CommonsMessages.getMessage("initials")) > 0);
		}
		try{
			dto.setInitials("MR");
			validator.validate(dto);
		}catch(IndustrikaValidationException ex){
			fail("Validacion fallo, el dto tiene todos los atributos requeridos");
		}
	}

}
