package com.industrika.administration.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.administration.dto.Deduction;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.administration.validation.DeductionValidator;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;


@Component("deductionValidator")
public class DeductionValidatorPredefined implements DeductionValidator {

	@Override
	public void validate(Deduction dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if ((dto.getName() == null) || (dto.getName().trim().isEmpty())){
				message += AdministrationMessages.getMessage("deduction.Name")+", ";
			}			
			if ((dto.getInitials() == null) || (dto.getInitials().trim().isEmpty())){
				message += AdministrationMessages.getMessage("deduction.Initials")+", ";
			}
			if ((dto.getValue() == null) || dto.getValue().intValue() <= 0){
				message += AdministrationMessages.getMessage("deduction.Value")+", ";
			} else {
				if (dto.getValue() >= 100){
					message += "El porcentaje de la deducción no puede ser mayor ni igual a 100";
				}
			}
			if (!message.equalsIgnoreCase("")){
				message = CommonsMessages.getMessage("error_not_empty")+": "+message;
				throw new IndustrikaValidationException(message);
			}
		}
	}

}
