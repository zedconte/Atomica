package com.industrika.commons.validation;

import com.industrika.commons.dto.User;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface UserValidator {
	public void validate(User user) throws IndustrikaValidationException;
}
