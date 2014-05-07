package com.industrika.commons.validation.predefined;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.industrika.commons.dto.Option;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@Component("optionValidator")
public class OptionValidatorPredefined implements IndustrikaValidator<Option> {
	
	@Value("${error_not_empty}")
	private static String NOT_EMPTY_MSG;
	
	@Override
	public void validate(Option dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (StringUtils.isEmpty(dto.getResourceName()) || dto.getResourceName().trim().equals("")){
				message += NOT_EMPTY_MSG+": ";
				message += CommonsMessages.getMessage("resourcename")+", ";
			}
			if (dto.getCreationDate() == null){
				if (message.isEmpty()){
					message += NOT_EMPTY_MSG+": ";
				}
				message += CommonsMessages.getMessage("creationdate")+", ";
			}
			if (dto.getModule() == null || 
					dto.getModule().getId() == null || dto.getModule().getId() <= 0){
				if (message.isEmpty()){
					message += NOT_EMPTY_MSG+": ";
				}
				message += CommonsMessages.getMessage("module")+", ";
			}
			if (!message.isEmpty()){
				message = message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}		
	}


}
