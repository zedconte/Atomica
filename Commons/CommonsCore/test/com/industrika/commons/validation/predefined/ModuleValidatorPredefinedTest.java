package com.industrika.commons.validation.predefined;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.industrika.commons.dto.Module;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/commons_context.xml"})
public class ModuleValidatorPredefinedTest {

	private static final Logger LOGGER = Logger.getLogger(ModuleValidatorPredefinedTest.class);
	
	@Autowired
	@Qualifier("moduleValidator")
	private IndustrikaValidator<Module> validator;
	
	@Test
	public void testValidate() {
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing validate method in ModuleValidatorPredefined....");
		
		Module module = new Module();
		
		try{
			validator.validate(module);
		}
		catch(IndustrikaValidationException e){
			assertTrue("Incorrect error message",
					e.getMessage().contains(
							CommonsMessages.getMessage("name")) );
		}
		
		module.setName("Mock module");
		try{
			validator.validate(module);
		}
		catch(IndustrikaValidationException e){
			Assert.fail("Validation failed, all required attributes are present");
		}
		
		LOGGER.info("Finished testing validate method in ModuleValidatorPredefined....");
		LOGGER.info("---------------------------------------------");
	}

}
