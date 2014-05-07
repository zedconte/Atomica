package com.industrika.humanresources.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Direction;

public interface DirectionValidator {
	public void validate(Direction dto) throws IndustrikaValidationException;
}
