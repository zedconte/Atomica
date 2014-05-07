package com.industrika.humanresources.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dto.Management;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.ManagementValidator;

@Component("managementValidator")
public class ManagementValidatorPredefined implements ManagementValidator {

	@Override
	public void validate(Management dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getName() == null || dto.getName().trim().equalsIgnoreCase("")){
				message += HRMessages.getMessage("management.Name")+", ";
			}
			if (dto.getDirection() == null || dto.getDirection().getIdDirection() == null || dto.getDirection().getIdDirection().intValue() <= 0){
				message += HRMessages.getMessage("management.Direction")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = CommonsMessages.getMessage("error_not_empty")+": "+message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}
	}

}
