package com.industrika.humanresources.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Department;

public interface DepartmentValidator {
	public void validate(Department dto) throws IndustrikaValidationException;
}
