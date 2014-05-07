package com.industrika.inventory.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.Purchase;

public interface PurchaseValidator {
	public void validate(Purchase dto) throws IndustrikaValidationException;
}
