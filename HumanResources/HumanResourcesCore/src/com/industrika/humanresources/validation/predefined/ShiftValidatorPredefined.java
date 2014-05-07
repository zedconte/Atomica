package com.industrika.humanresources.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dto.Shift;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.ShiftValidator;

@Component("shiftValidator")
public class ShiftValidatorPredefined implements ShiftValidator {

	@Override
	public void validate(Shift dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getName() == null || dto.getName().trim().equalsIgnoreCase("")){
				message += HRMessages.getMessage("shift.Name")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = CommonsMessages.getMessage("error_not_empty")+": "+message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}		
	}

}
