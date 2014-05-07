package com.industrika.commons.validation;

import com.industrika.commons.dto.Country;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface CountryValidator {
	public void validate(Country dto) throws IndustrikaValidationException;
}
