package com.industrika.commons.validation.predefined;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.industrika.commons.dto.User;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Component("userValidator")
public class UserValidatorPredefined implements IndustrikaValidator<User> {

	@Override
	public void validate(User dto) throws IndustrikaValidationException {
		String message = "";
		if (dto != null) {
			if (StringUtils.isEmpty(dto.getName()) || dto.getName().trim().equals("")) {
				message += CommonsMessages.getMessage("name") + ", ";
			}
			if (StringUtils.isEmpty(dto.getCode()) || dto.getCode().trim().equals("")) {
				message += CommonsMessages.getMessage("userName") + ", ";
			}
			if (StringUtils.isEmpty(dto.getPassword()) || dto.getPassword().trim().equals("")) {
				message += CommonsMessages.getMessage("password") + ", ";
			}
			if (!message.equalsIgnoreCase("")) {
				message = CommonsMessages.getMessage("error_not_empty") +": "+ message.substring(0, message.length() - 2);
				throw new IndustrikaValidationException(message);
			}
		}
	}
}
