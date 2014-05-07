package com.industrika.sales.validation.predefined;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.validation.predefined.DocumentValidatorPredefined;
import com.industrika.sales.dto.Sale;
import com.industrika.sales.i18n.SalesMessages;
import com.industrika.sales.validation.SaleValidator;

@Component("saleValidator")
public class SaleValidatorPredefined extends DocumentValidatorPredefined implements SaleValidator {

	@Override
	public void validate(Sale dto) throws IndustrikaValidationException {
		String fields=super.verifyFields(dto);
		if (dto != null){
			if (dto.getCustomer() == null || dto.getCustomer().getIdPerson() == null || dto.getCustomer().getIdPerson().intValue() <= 0){
				fields+=SalesMessages.getMessage("sale.Customer")+", ";
			}
			if (!StringUtils.isEmpty(fields)){
				throw new IndustrikaValidationException("Los siguientes datos no son opcionales, favor de especificarlos: "+fields.substring(0,fields.length()-2));
			}
		}

	}

}
