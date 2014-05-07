package com.industrika.inventory.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.Provider;

public interface ProviderValidator {
	public void validate(Provider provider) throws IndustrikaValidationException;
}
