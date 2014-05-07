package com.industrika.humanresources.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dto.Direction;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.DirectionValidator;

@Component("directionValidator")
public class DirectionValidatorPredefined implements DirectionValidator {

	@Override
	public void validate(Direction dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getName() == null || dto.getName().trim().equalsIgnoreCase("")){
				message += HRMessages.getMessage("direction.Name")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = CommonsMessages.getMessage("error_not_empty")+": "+message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}		
	}

}
