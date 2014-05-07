package com.industrika.humanresources.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dto.Employee;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.EmployeeValidator;

@Component("employeeValidator")
public class EmployeeValidatorPredefined implements EmployeeValidator {

	@Override
	public void validate(Employee dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getFirstName() == null || dto.getFirstName().trim().equalsIgnoreCase("")){
				message += HRMessages.getMessage("employee.FirstName")+", ";
			}
			if (dto.getLastName() == null || dto.getLastName().trim().equalsIgnoreCase("")){
				message += HRMessages.getMessage("employee.LastName")+", ";
			}
			if (dto.getGender() == null || dto.getGender().trim().equalsIgnoreCase("")){
				message += HRMessages.getMessage("employee.Gender")+", ";
			}
			if (dto.getRfc() == null || dto.getRfc().trim().equalsIgnoreCase("")){
				message += HRMessages.getMessage("employee.Rfc")+", ";
			}
			if (dto.getSalary() == null || dto.getSalary().doubleValue() <= 0){
				message += HRMessages.getMessage("employee.Salary")+", ";
			}
			if (dto.getDepartment() == null || dto.getDepartment().getIdDepartment() == null || dto.getDepartment().getIdDepartment().intValue() <= 0){
				message += HRMessages.getMessage("employee.Department")+", ";
			}
			if (dto.getPosition() == null || dto.getPosition().getIdPosition() == null || dto.getPosition().getIdPosition().intValue() <= 0){
				message += HRMessages.getMessage("employee.Position")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = CommonsMessages.getMessage("error_not_empty")+": "+message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}
	}

}
