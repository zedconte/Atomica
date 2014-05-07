package com.industrika.productplanning.validation.predefined;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;
import com.industrika.productplanning.dto.LifeCycle;
import com.industrika.productplanning.i18n.ProductPlanningMessages;

@Component("lifeCycleValidator")
public class LifeCycleValidatorPredefined implements IndustrikaValidator<LifeCycle> {

	@Override
	public void validate(LifeCycle dto)
			throws IndustrikaValidationException {

		String message = "";
		if (dto != null) {
			if (StringUtils.isEmpty(dto.getName())|| dto.getName().trim().equals("")) {
				if (message.equalsIgnoreCase("")) {
					message += CommonsMessages.getMessage("error_not_empty")
							+ ": ";
				}
				message += CommonsMessages.getMessage("name") + ", ";
			}
			
			if (StringUtils.isEmpty(dto.getLifeCycleName())|| dto.getLifeCycleName().trim().equals("")) {
				if (message.equalsIgnoreCase("")) {
					message += CommonsMessages.getMessage("error_not_empty")
							+ ": ";
				}
				message += ProductPlanningMessages.getMessage("lifeCycle.life") + ", ";
			}
			
			if (dto.getLifeOrder() == null || dto.getLifeOrder()<0) {
				if (message.equalsIgnoreCase("")) {
					message += CommonsMessages.getMessage("error_not_empty")
							+ ": ";
				}
				message += ProductPlanningMessages.getMessage("lifeCycle.order") + ", ";
			}

			if (!message.equalsIgnoreCase("")) {
				message = message.substring(0, message.length() - 2);
				throw new IndustrikaValidationException(message);
			}
		}
	}
}
