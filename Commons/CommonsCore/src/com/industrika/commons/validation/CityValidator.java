package com.industrika.commons.validation;

import com.industrika.commons.dto.City;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface CityValidator {
	public void validate(City dto) throws IndustrikaValidationException;
}
