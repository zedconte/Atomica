package com.industrika.commons.validation.predefined;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.industrika.commons.dto.Module;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@Component("moduleValidator")
public class ModuleValidatorPredefined implements IndustrikaValidator<Module> {

	@Override
	public void validate(Module dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (StringUtils.isEmpty(dto.getName()) || dto.getName().trim().equals("")){
				message += CommonsMessages.getMessage("error_not_empty")+": ";
				message += CommonsMessages.getMessage("name");
			}
			if (!message.equalsIgnoreCase("")){
				throw new IndustrikaValidationException(message);
			}
		}		
	}


}
