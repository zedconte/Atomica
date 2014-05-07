package com.industrika.inventory.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.Item;

public interface ItemValidator {
	public void validate(Item item) throws IndustrikaValidationException;
}
