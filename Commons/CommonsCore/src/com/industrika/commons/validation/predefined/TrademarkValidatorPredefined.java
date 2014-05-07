package com.industrika.commons.validation.predefined;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.industrika.commons.dto.Trademark;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Component("trademarkValidator")
public class TrademarkValidatorPredefined implements
		IndustrikaValidator<Trademark> {

	@Override
	public void validate(Trademark dto) throws IndustrikaValidationException {
		String message = "";
		if (dto != null) {
			if (StringUtils.isEmpty(dto.getName())
					|| dto.getName().trim().equals("")) {
				if (message.equalsIgnoreCase("")) {
					message += CommonsMessages.getMessage("error_not_empty")
							+ ": ";
				}
				message += CommonsMessages.getMessage("name") + ", ";
			}

			if (!message.equalsIgnoreCase("")) {
				message = message.substring(0, message.length() - 2);
				throw new IndustrikaValidationException(message);
			}
		}
	}
}
