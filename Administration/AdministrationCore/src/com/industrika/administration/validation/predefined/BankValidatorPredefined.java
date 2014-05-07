package com.industrika.administration.validation.predefined;

import org.springframework.stereotype.Component;
import com.industrika.administration.dto.Bank;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Component("bankValidatorPredefined")
public class BankValidatorPredefined implements IndustrikaValidator<Bank> {

	@Override
	public void validate(Bank dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getName() == null || dto.getName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += AdministrationMessages.getMessage("bank.Name")+", ";
			}
			if (dto.getAcronym() == null || dto.getAcronym().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}				
				message += AdministrationMessages.getMessage("bank.Acronym")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				throw new IndustrikaValidationException(message);
			}
		}
		
	}

}
