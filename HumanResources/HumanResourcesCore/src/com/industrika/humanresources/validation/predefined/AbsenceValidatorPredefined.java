package com.industrika.humanresources.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dto.Absence;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.AbsenceValidator;

@Component("absenceValidator")
public class AbsenceValidatorPredefined implements AbsenceValidator {

	@Override
	public void validate(Absence dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getDate() == null){
				message += HRMessages.getMessage("absence.Date")+", ";
			}
			if (dto.getEmployee() == null || dto.getEmployee().getIdPerson() == null || dto.getEmployee().getIdPerson().intValue() <= 0){
				message += HRMessages.getMessage("department.Employee")+", ";
			}
			if (dto.getApplyDiscount() == null || dto.getApplyDiscount().intValue() <= 0){
				message += HRMessages.getMessage("absence.ApplyDiscount")+", ";
			}
			if (!message.equalsIgnoreCase("")){
				message = CommonsMessages.getMessage("error_not_empty")+": "+message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}
	}

}
