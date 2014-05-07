package com.industrika.humanresources.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Employee;

public interface EmployeeValidator {
	public void validate(Employee dto) throws IndustrikaValidationException;
}
