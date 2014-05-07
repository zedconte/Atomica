package com.industrika.inventory.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.BuyOrder;

public interface BuyOrderValidator {
	public void validate(BuyOrder dto) throws IndustrikaValidationException;
}
