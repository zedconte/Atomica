package com.industrika.inventory.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.Document;

public interface DocumentValidator {
	public void validate(Document dto) throws IndustrikaValidationException;
	
	public String verifyFields(Document dto);

}
