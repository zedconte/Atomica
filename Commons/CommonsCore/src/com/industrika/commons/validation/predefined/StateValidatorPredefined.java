package com.industrika.commons.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.dto.State;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.StateValidator;

@Component
public class StateValidatorPredefined implements StateValidator {
	@Override
	public void validate(State dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getName() == null || dto.getName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += CommonsMessages.getMessage("state.Name")+", ";
			}
			if (dto.getShortName() == null || dto.getShortName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}				
				message += CommonsMessages.getMessage("state.shortName")+", ";
			}
			if (dto.getCountry() == null || dto.getCountry().getIdCountry() == null || dto.getCountry().getIdCountry().intValue() <= 0){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}				
				message += CommonsMessages.getMessage("state.Country")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}
		
	}
}