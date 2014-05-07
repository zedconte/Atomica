package com.industrika.shipping.validation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.shipping.dto.ShippingRoute;
import com.industrika.shipping.i18n.ShippingMessages;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Component("shippingRouteValidatorPredefined")
public class ShippingRoutesValidatorPredefined implements IndustrikaValidator<ShippingRoute> {

	@Override
	@Transactional
	public void validate(ShippingRoute dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getName() == null || dto.getName().trim().equalsIgnoreCase("")){
				message += ShippingMessages.getMessage("route.Name")+", ";
			}
			if (dto.getAcronym() == null || dto.getAcronym().trim().equalsIgnoreCase("")){
				message += ShippingMessages.getMessage("route.Acronym")+", ";
			}
			if (dto.getDistance() == null || dto.getDistance().doubleValue() <= 0){
				message += ShippingMessages.getMessage("route.Distance")+", ";
			}
			if (dto.getTime() == null || dto.getTime().doubleValue() <= 0){
				message += ShippingMessages.getMessage("route.Time")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = CommonsMessages.getMessage("error_not_empty")+": "+message;
				throw new IndustrikaValidationException(message);
			}
		}
		
	}

}
