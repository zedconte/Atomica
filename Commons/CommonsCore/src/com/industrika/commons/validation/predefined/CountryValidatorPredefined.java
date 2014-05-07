package com.industrika.commons.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.dto.Country;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.CountryValidator;

@Component
public class CountryValidatorPredefined implements CountryValidator {
	@Override
	public void validate(Country dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getName() == null || dto.getName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += CommonsMessages.getMessage("country.Name")+", ";
			}
			if (dto.getShortName() == null || dto.getShortName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}				
				message += CommonsMessages.getMessage("country.shortName")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}
		
	}
}