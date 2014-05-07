package com.industrika.commons.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.dto.City;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.CityValidator;

@Component
public class CityValidatorPredefined implements CityValidator {
	@Override
	public void validate(City dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getName() == null || dto.getName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += CommonsMessages.getMessage("city.Name")+", ";
			}
			if (dto.getShortName() == null || dto.getShortName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}				
				message += CommonsMessages.getMessage("city.shortName")+", ";
			}
			if (dto.getState() == null || dto.getState().getIdState() == null || dto.getState().getIdState().intValue() <= 0){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}				
				message += CommonsMessages.getMessage("city.State")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}
		
	}
}