package com.industrika.administration.validation;
import com.industrika.administration.dto.Account;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface AccountValidator {

	public void validate(Account dto) throws IndustrikaValidationException;
	public Account validateParents(Account dto) throws IndustrikaValidationException;
	
}
