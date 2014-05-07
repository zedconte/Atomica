package com.industrika.humanresources.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Payroll;

public interface PayrollValidator {
	public void validate(Payroll dto) throws IndustrikaValidationException;
}
