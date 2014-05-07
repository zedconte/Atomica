package com.industrika.administration.validation;

import com.industrika.administration.dto.Deduction;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface DeductionValidator {
	public void validate(Deduction dto) throws IndustrikaValidationException;
}
