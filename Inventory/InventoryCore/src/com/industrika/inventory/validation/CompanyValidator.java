package com.industrika.inventory.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.Company;

public interface CompanyValidator {
	public void validate(Company company) throws IndustrikaValidationException;
	
	public String verifyFields(Company company);

}
