package com.industrika.sales.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.sales.dto.Sale;

public interface SaleValidator {
	public void validate(Sale dto) throws IndustrikaValidationException;
}
