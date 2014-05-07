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

import com.industrika.commons.dto.Action;
import com.industrika.commons.dto.Option;
import com.industrika.commons.dto.Privilege;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/commons_context.xml"})
public class PrivilegeValidatorPredefinedTest {

	private static final Logger LOGGER = Logger.getLogger(PrivilegeValidatorPredefinedTest.class);
	
	@Autowired
	@Qualifier("privilegeValidator")
	private IndustrikaValidator<Privilege> validator;
	
	@Test
	public void testValidate() {
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing validate method in OptionValidatorPredefined....");
		
		Privilege pr = new Privilege();
		
		try{
			validator.validate(pr);
		}
		catch(IndustrikaValidationException e){
			assertTrue("Incorrect error message",
					e.getMessage().contains(
							CommonsMessages.getMessage("action")) );
			assertTrue("Incorrect error message",
					e.getMessage().contains(
							CommonsMessages.getMessage("option")) );
		}
		
		pr.setAction(new Action(21,null,null));
		try{
			validator.validate(pr);
		}
		catch(IndustrikaValidationException e){
			assertTrue("Incorrect error message",
					e.getMessage().contains(
							CommonsMessages.getMessage("option")) );
		}
		
		pr.setOption(new Option(21,null,null,null));
		try{
			validator.validate(pr);
		}
		catch(IndustrikaValidationException e){
			Assert.fail("Validation failed, all required attributes are present");
		}
		
		
		LOGGER.info("Finished testing validate method in OptionValidatorPredefined....");
		LOGGER.info("---------------------------------------------");
	}

}
