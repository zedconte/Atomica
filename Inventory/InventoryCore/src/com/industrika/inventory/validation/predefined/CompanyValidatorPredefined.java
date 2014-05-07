package com.industrika.inventory.validation.predefined;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.predefined.PersonValidatorPredefined;
import com.industrika.inventory.dto.Company;
import com.industrika.inventory.i18n.InventoryMessages;
import com.industrika.inventory.validation.CompanyValidator;

@Component("companyValidator")
public class CompanyValidatorPredefined extends PersonValidatorPredefined implements CompanyValidator {

	@Override
	public void validate(Company company) throws IndustrikaValidationException {
		if (company != null){
			String fields=verifyFields(company);
			if (!StringUtils.isEmpty(fields)){
				throw new IndustrikaValidationException("Los siguientes datos no son opcionales, favor de especificarlos: "+fields.substring(0,fields.length()-2));
			}
		}
	}

	@Override
	public String verifyFields(Company company) {
		String fields="";
		if (company != null){
			if (company.getAddresses() == null || company.getAddresses().size() <= 0){
				fields+=CommonsMessages.getMessage("address")+", ";
			}
			if (StringUtils.isEmpty(company.getBusinessName())){
				fields+=InventoryMessages.getMessage("provider.BusinessName")+", ";
			}
			if (StringUtils.isEmpty(company.getRfc())){
				fields+=InventoryMessages.getMessage("provider.Rfc")+", ";
			}
		}
		return fields;
	}

}
