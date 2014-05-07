package com.industrika.commons.validation.predefined;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.industrika.commons.dto.Action;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@Component("actionValidator")
public class ActionValidatorPredefined implements IndustrikaValidator<Action> {

	@Override
	public void validate(Action dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (StringUtils.isEmpty(dto.getType()) || dto.getType().trim().equals("")){
				message += CommonsMessages.getMessage("error_not_empty")+": ";
				message += CommonsMessages.getMessage("type");
			}
			if (!message.equalsIgnoreCase("")){
				throw new IndustrikaValidationException(message);
			}
		}		
	}


}
