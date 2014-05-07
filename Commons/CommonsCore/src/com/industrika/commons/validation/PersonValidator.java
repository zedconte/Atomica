package com.industrika.commons.validation;

import com.industrika.commons.dto.Person;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface PersonValidator {

	public void validate(Person dto) throws IndustrikaValidationException;
	
}
