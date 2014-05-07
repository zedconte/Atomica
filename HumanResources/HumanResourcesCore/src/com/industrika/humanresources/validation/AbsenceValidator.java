package com.industrika.humanresources.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Absence;

public interface AbsenceValidator {
	public void validate(Absence dto) throws IndustrikaValidationException;
}
