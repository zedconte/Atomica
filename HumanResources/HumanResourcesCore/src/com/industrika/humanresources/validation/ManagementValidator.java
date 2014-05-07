package com.industrika.humanresources.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Management;

public interface ManagementValidator {
	public void validate(Management dto) throws IndustrikaValidationException;
}
