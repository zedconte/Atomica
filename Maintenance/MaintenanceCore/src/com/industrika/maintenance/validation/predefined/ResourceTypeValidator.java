package com.industrika.maintenance.validation.predefined;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.maintenance.dto.ResourceType;
import com.industrika.maintenance.validation.IResourceTypeValidator;

@Component("resourceTypeValidator")
public class ResourceTypeValidator implements IResourceTypeValidator {

	@Override
	public void validate(ResourceType object) throws IndustrikaValidationException {
		String msgNotEmpty = CommonsMessages.getMessage(CommonsMessages.ERROR_NOT_EMPTY);
		if (object==null)
			throw new IndustrikaValidationException(msgNotEmpty + " tipo de recurso");
		String name = object.getName();
		if (StringUtils.isEmpty(name))
			throw new IndustrikaValidationException(msgNotEmpty + " nombre de tipo de recurso");
	}

}
