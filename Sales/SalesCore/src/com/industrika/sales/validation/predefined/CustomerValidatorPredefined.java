package com.industrika.sales.validation.predefined;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.validation.predefined.CompanyValidatorPredefined;
import com.industrika.sales.dto.Customer;
import com.industrika.sales.validation.CustomerValidator;

@Component("customerValidator")
public class CustomerValidatorPredefined extends CompanyValidatorPredefined implements CustomerValidator {

	@Override
	public void validate(Customer customer) throws IndustrikaValidationException {
		String fields = super.verifyFields(customer);
		if (!StringUtils.isEmpty(fields)){
			throw new IndustrikaValidationException("Los siguientes datos no son opcionales, favor de especificarlos: "+fields.substring(0,fields.length()-2));
		}
	}

}
