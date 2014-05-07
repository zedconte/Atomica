package com.industrika.sales.validation.predefined;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.validation.predefined.DocumentValidatorPredefined;
import com.industrika.sales.dto.Quotation;
import com.industrika.sales.i18n.SalesMessages;
import com.industrika.sales.validation.QuotationValidator;

@Component("quotationValidator")
public class QuotationValidatorPredefined extends DocumentValidatorPredefined implements QuotationValidator {

	@Override
	public void validate(Quotation dto) throws IndustrikaValidationException {
		String fields=super.verifyFields(dto);
		if (dto != null){
			if (dto.getCustomer() == null || dto.getCustomer().getIdPerson() == null || dto.getCustomer().getIdPerson().intValue() <= 0){
				fields+=SalesMessages.getMessage("quotation.Customer")+", ";
			}
			if (!StringUtils.isEmpty(fields)){
				throw new IndustrikaValidationException("Los siguientes datos no son opcionales, favor de especificarlos: "+fields.substring(0,fields.length()-2));
			}
		}

	}

}
