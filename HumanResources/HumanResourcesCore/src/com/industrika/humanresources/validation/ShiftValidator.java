package com.industrika.humanresources.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Shift;

public interface ShiftValidator {
	public void validate(Shift dto) throws IndustrikaValidationException;
}
