package com.industrika.commons.validation.predefined;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.industrika.commons.dto.Privilege;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@Component("privilegeValidator")
public class PrivilegeValidatorPredefined implements IndustrikaValidator<Privilege> {

	@Value("${error_not_empty}")
	private static String NOT_EMPTY_MSG;
	
	@Override
	public void validate(Privilege dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getAction() == null || 
					dto.getAction().getId() == null || dto.getAction().getId() <= 0){
				if (message.isEmpty()){
					message += NOT_EMPTY_MSG+": ";
				}
				message += CommonsMessages.getMessage("action")+", ";
			}
			if (dto.getOption() == null || 
					dto.getOption().getId() == null || dto.getOption().getId() <= 0){
				if (message.isEmpty()){
					message += NOT_EMPTY_MSG+": ";
				}
				message += CommonsMessages.getMessage("option")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}		
	}


}
