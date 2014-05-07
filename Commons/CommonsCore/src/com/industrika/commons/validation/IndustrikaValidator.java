package com.industrika.commons.validation;

import com.industrika.commons.exceptions.IndustrikaValidationException;

/**
 * @author jose.arellano
 */
public interface IndustrikaValidator<E> {
	
	void validate(E dto) throws IndustrikaValidationException;
}
