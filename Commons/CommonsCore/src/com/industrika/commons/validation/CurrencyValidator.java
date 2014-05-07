package com.industrika.commons.validation;

import com.industrika.commons.dto.Currency;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface CurrencyValidator {

	public void validate(Currency dto) throws IndustrikaValidationException;
	
}
