package com.industrika.humanresources.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dto.Payroll;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.PayrollValidator;

@Component("payrollValidator")
public class PayrollValidatorPredefined implements PayrollValidator {

	@Override
	public void validate(Payroll dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getBegin() == null){
				message += HRMessages.getMessage("payroll.Begin")+", ";
			}
			if (dto.getEnd() == null){
				message += HRMessages.getMessage("payroll.End")+", ";
			}
			if (dto.getBegin() != null && dto.getEnd() != null){
				if (dto.getBegin().after(dto.getEnd())){
					message += "La fecha de inicio no puede ser mayor a la de término, ";
				}
			}
			if (dto.getDays() != null && dto.getDays().intValue() <= 0){
				message += "La nómina debe tener al menos 1 día de duración, ";
			}
			
			if (!message.equalsIgnoreCase("")){
				message = CommonsMessages.getMessage("error_not_empty")+": "+message.substring(0,message.length()-2);
				throw new IndustrikaValidationException(message);
			}
		}
	}

}
