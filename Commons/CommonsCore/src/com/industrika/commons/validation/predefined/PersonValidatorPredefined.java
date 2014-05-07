package com.industrika.commons.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.dto.Person;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.PersonValidator;

@Component("personValidator")
public class PersonValidatorPredefined implements PersonValidator {

	@Override
	public void validate(Person dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getFirstName() == null || dto.getFirstName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += CommonsMessages.getMessage("firstName")+", ";
			}
			if (dto.getLastName() == null || dto.getLastName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}				
				message += CommonsMessages.getMessage("lastName")+", ";
			}
			if (dto.getGender() == null || dto.getGender().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += CommonsMessages.getMessage("gender")+", ";
			}	
			if (!message.equalsIgnoreCase("")){
				message = message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}
		
	}

}
