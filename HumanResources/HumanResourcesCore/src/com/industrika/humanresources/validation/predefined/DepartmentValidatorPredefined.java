package com.industrika.humanresources.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.dto.Department;
import com.industrika.humanresources.validation.DepartmentValidator;

@Component("departmentValidator")
public class DepartmentValidatorPredefined implements DepartmentValidator {

	@Override
	public void validate(Department dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getName() == null || dto.getName().trim().equalsIgnoreCase("")){
				message += HRMessages.getMessage("department.Name")+", ";
			}
			if (dto.getManagement() == null || dto.getManagement().getIdManagement() == null || dto.getManagement().getIdManagement().intValue() <= 0){
				message += HRMessages.getMessage("department.Management")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = CommonsMessages.getMessage("error_not_empty")+": "+message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}
	}

}
