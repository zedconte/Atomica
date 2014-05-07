package com.industrika.sales.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.sales.dto.Quotation;

public interface QuotationValidator {
	public void validate(Quotation dto) throws IndustrikaValidationException;
}
