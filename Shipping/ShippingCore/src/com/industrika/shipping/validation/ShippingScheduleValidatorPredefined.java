package com.industrika.shipping.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import com.industrika.shipping.dto.ShippingSchedule;
import com.industrika.shipping.i18n.ShippingMessages;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.validation.IndustrikaValidator;

@Component("shippingScheduleValidatorPredefined")
public class ShippingScheduleValidatorPredefined implements IndustrikaValidator<ShippingSchedule> {

	@Override
	public void validate(ShippingSchedule dto) throws IndustrikaValidationException {
		String fields = "";
		if (dto != null){
			if (dto.getDate() == null){
				fields += ShippingMessages.getMessage("shippingschedule.Date") + ", ";
			}
			if (dto.getRoute() == null || dto.getRoute().getIdRoute()==null){
				fields += ShippingMessages.getMessage("shippingschedule.Route") + ", ";
			}
			if (dto.getItemsRows() == null || dto.getItemsRows().size() < 0){
				fields += ShippingMessages.getMessage("shippingschedule.Item") + ", ";
			}
			
		}
		if (!StringUtils.isEmpty(fields)){
			throw new IndustrikaValidationException("Los siguientes datos no son opcionales, favor de especificarlos: "+fields.substring(0,fields.length()-2));
		}				
	}

}
