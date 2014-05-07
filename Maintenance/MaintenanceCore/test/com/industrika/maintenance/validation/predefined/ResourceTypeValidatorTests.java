package com.industrika.maintenance.validation.predefined;


import org.testng.annotations.*;


import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.maintenance.dto.ResourceType;

public class ResourceTypeValidatorTests {

	private ResourceTypeValidator validator;
	
	@BeforeMethod
	public void setUp() throws Exception {
		validator = new ResourceTypeValidator();
	}

	@Test(expectedExceptions=IndustrikaValidationException.class)
	public void Validate_ShouldThrowExceptionIfInstanceIsNull() throws IndustrikaValidationException {
		validator.validate(null);
	}
	
	
	@Test(expectedExceptions=IndustrikaValidationException.class)
	public void Validate_ShouldThrowExceptionIfNameIsEmpty() throws IndustrikaValidationException {
		ResourceType type = new ResourceType();
		type.setId(10);
		type.setName("");
		validator.validate(type);
	}
	
	@Test
	public void Validate_ShouldNotThrowExceptionIfinstanceIsValid() throws IndustrikaValidationException {
		ResourceType type = new ResourceType();
		type.setId(10);
		type.setName("name");
		
		validator.validate(type);
	}
}
