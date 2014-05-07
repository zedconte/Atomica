package com.industrika.maintenance.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.validation.IndustrikaValidator;
import com.industrika.maintenance.dto.Resource;

@Component("resourceValidator")
public class ResourceValidator implements IndustrikaValidator<Resource> {

	@Override
	public void validate(Resource dto) throws IndustrikaValidationException {
		// TODO Auto-generated method stub
		
	}

}
