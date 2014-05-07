package com.industrika.commons.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.dto.Currency;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.CurrencyValidator;

@Component
public class CurrencyValidatorPredefined implements CurrencyValidator {
	@Override
	public void validate(Currency dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getName() == null || dto.getName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += CommonsMessages.getMessage("currency.Name")+", ";
			}
			if (dto.getShortName() == null || dto.getShortName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}				
				message += CommonsMessages.getMessage("currency.ShortName")+", ";
			}
			if (dto.getSymbol() == null || dto.getSymbol().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += CommonsMessages.getMessage("currency.Symbol")+", ";
			}	
			if (!message.equalsIgnoreCase("")){
				message = message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}
		
	}
}
