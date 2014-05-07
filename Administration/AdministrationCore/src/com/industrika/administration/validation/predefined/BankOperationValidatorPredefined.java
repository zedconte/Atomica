package com.industrika.administration.validation.predefined;

import org.springframework.stereotype.Component;

import com.industrika.administration.dto.BankOperation;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Component("bankOperationValidatorPredefined")
public class BankOperationValidatorPredefined implements IndustrikaValidator<BankOperation> {

	public void validateSearch(BankOperation dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if(dto.getIdOperation()!=null){
				if (dto.getIdOperation() == -1 ){
					message += AdministrationMessages.getMessage("bank.operation.id.valid")+", ";
				}
			}
			
			if(dto.getAmount()!=null){
				if (dto.getAmount() == -1 ){
					message += AdministrationMessages.getMessage("bank.operation.amount.valid")+", ";
				}
			}
			
			if (!message.equalsIgnoreCase("")){
				throw new IndustrikaValidationException(message);
			}
		}
		
	}
	
	@Override
	public void validate(BankOperation dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getIdOperation() == null ){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += AdministrationMessages.getMessage("bank.operation.id.notempty")+", ";
			}else{
				if (dto.getIdOperation() == -1 ){
					message += AdministrationMessages.getMessage("bank.operation.id.valid")+", ";
				}
			}
			
			if (dto.getAmount() == null ){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += AdministrationMessages.getMessage("bank.operation.amount.notempty")+", ";
			}else if (dto.getAmount() == -1 ){
				message += AdministrationMessages.getMessage("bank.operation.amount.valid")+", ";
			}
			
			if (!message.equalsIgnoreCase("")){
				throw new IndustrikaValidationException(message);
			}
		}
		
	}

}
