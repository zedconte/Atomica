package com.industrika.humanresources.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dto.Position;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.PositionValidator;

@Component("positionValidator")
public class PositionValidatorPredefined implements PositionValidator {

	@Override
	public void validate(Position dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getName() == null || dto.getName().trim().equalsIgnoreCase("")){
				message += HRMessages.getMessage("position.Name")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = CommonsMessages.getMessage("error_not_empty")+": "+message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}		
	}

}
