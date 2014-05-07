package com.industrika.inventory.validation.predefined;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.Provider;
import com.industrika.inventory.validation.ProviderValidator;

@Component("providerValidator")
public class ProviderValidatorPredefined extends CompanyValidatorPredefined implements ProviderValidator {
	@Override
	public void validate(Provider provider) throws IndustrikaValidationException {
		String fields = super.verifyFields(provider);
		if (!StringUtils.isEmpty(fields)){
			throw new IndustrikaValidationException("Los siguientes datos no son opcionales, favor de especificarlos: "+fields.substring(0,fields.length()-2));
		}
	}

}
