package com.industrika.sales.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.sales.dto.Customer;

public interface CustomerValidator {
	public void validate(Customer customer) throws IndustrikaValidationException;
}
