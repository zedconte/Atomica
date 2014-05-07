package com.industrika.inventory.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.InventoryMovement;

public interface InventoryMovementValidator {
	public void validate(InventoryMovement dto) throws IndustrikaValidationException;
}
