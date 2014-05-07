package com.industrika.commons.validation.predefined;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.industrika.commons.dao.hibernate.SystemActionDaoHibernateTest;
import com.industrika.commons.dto.Action;
import com.industrika.commons.dto.Role;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/commons_context.xml"})
public class ActionValidatorPredefinedTest {

	private static final Logger LOGGER = Logger.getLogger(ActionValidatorPredefinedTest.class);
	
	@Autowired
	@Qualifier("actionValidator")
	private IndustrikaValidator<Action> validator;
	
	@Test
	public void testValidate() {
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing validate method in ActionValidatorPredefined....");
		
		Action action = new Action();
		
		try{
			validator.validate(action);
		}
		catch(IndustrikaValidationException e){
			assertTrue("Incorrect error message",
					e.getMessage().contains(
							CommonsMessages.getMessage("type")) );
		}
		
		action.setType("Mock type");
		try{
			validator.validate(action);
		}
		catch(IndustrikaValidationException e){
			Assert.fail("Validation failed, all required attributes are present");
		}
		
		LOGGER.info("Finished testing validate method in ActionValidatorPredefined....");
		LOGGER.info("---------------------------------------------");
	}

}
