package com.industrika.humanresources.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Position;

public interface PositionValidator {
	public void validate(Position dto) throws IndustrikaValidationException;
}
