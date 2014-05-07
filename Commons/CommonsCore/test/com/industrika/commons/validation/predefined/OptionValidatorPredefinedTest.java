package com.industrika.commons.validation.predefined;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.industrika.commons.dto.Module;
import com.industrika.commons.dto.Option;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/commons_context.xml"})
public class OptionValidatorPredefinedTest {

	private static final Logger LOGGER = Logger.getLogger(OptionValidatorPredefinedTest.class);
	
	@Autowired
	@Qualifier("optionValidator")
	private IndustrikaValidator<Option> validator;
	
	@Test
	public void testValidate() {
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing validate method in OptionValidatorPredefined....");
		
		Option option = new Option();
		
		try{
			validator.validate(option);
		}
		catch(IndustrikaValidationException e){
			assertTrue("Incorrect error message",
					e.getMessage().contains(
							CommonsMessages.getMessage("resourcename")) );
			assertTrue("Incorrect error message",
					e.getMessage().contains(
							CommonsMessages.getMessage("creationdate")) );
			assertTrue("Incorrect error message",
					e.getMessage().contains(
							CommonsMessages.getMessage("module")) );
		}
		
		option.setResourceName("Mock system resource");
		try{
			validator.validate(option);
		}
		catch(IndustrikaValidationException e){
			assertTrue("Incorrect error message",
					e.getMessage().contains(
							CommonsMessages.getMessage("creationdate")) );
			assertTrue("Incorrect error message",
					e.getMessage().contains(
							CommonsMessages.getMessage("module")) );
		}
		
		option.setCreationDate(Calendar.getInstance());
		try{
			validator.validate(option);
		}
		catch(IndustrikaValidationException e){
			assertTrue("Incorrect error message",
					e.getMessage().contains(
							CommonsMessages.getMessage("module")) );
		}
		
		option.setModule(new Module(1,null,null));
		try{
			validator.validate(option);
		}
		catch(IndustrikaValidationException e){
			Assert.fail("Validation failed, all required attributes are present");
		}
		
		
		LOGGER.info("Finished testing validate method in OptionValidatorPredefined....");
		LOGGER.info("---------------------------------------------");
	}

}
