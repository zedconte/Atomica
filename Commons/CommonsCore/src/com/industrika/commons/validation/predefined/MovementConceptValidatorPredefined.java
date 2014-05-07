package com.industrika.commons.validation.predefined;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.industrika.commons.dto.MovementConcept;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Component("movementConceptValidator")
public class MovementConceptValidatorPredefined implements IndustrikaValidator<MovementConcept> {

	@Override
	public void validate(MovementConcept dto)
			throws IndustrikaValidationException {

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
