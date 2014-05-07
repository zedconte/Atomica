package com.industrika.commons.validation;

import com.industrika.commons.dto.State;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface StateValidator {
	public void validate(State dto) throws IndustrikaValidationException;
}
