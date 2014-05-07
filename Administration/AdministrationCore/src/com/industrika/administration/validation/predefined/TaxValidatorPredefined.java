package com.industrika.administration.validation.predefined;
import org.springframework.stereotype.Component;

import com.industrika.administration.dto.Policy;
import com.industrika.administration.dto.Tax;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;
@Component("taxValidatorPredefined")
public class TaxValidatorPredefined implements IndustrikaValidator<Tax> {

	@Override
	public void validate(Tax dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if ((dto.getName() == null) || (dto.getName().trim().isEmpty())){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += AdministrationMessages.getMessage("tax.operation.name.notempty")+", ";
			}
			
			if ((dto.getInitials() == null) || (dto.getName().trim().isEmpty())){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += AdministrationMessages.getMessage("tax.operation.initial.notempty")+", ";
			}
			
			message+=isValidAmount(dto.getTaxValue());
			
			if (!message.equalsIgnoreCase("")){
				throw new IndustrikaValidationException(message);
			}
		}
	
		
	}
	
	public void validateSearch(Tax dto) throws IndustrikaValidationException {
		String message="";
		if ((dto.getTaxValue() != null )&&(dto.getTaxValue() == -1)){
			message += AdministrationMessages.getMessage("tax.operation.amount.valid");
		}
		
		if ((dto.getIdTax() != null )&&(dto.getIdTax() == -1)){
			message += AdministrationMessages.getMessage("tax.operation.id.valid");
		}
		
		if (!message.equalsIgnoreCase("")){
			throw new IndustrikaValidationException(message);
		}
	}
	
	public void validateUpdate(Tax dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			message=isValidID(dto.getIdTax());
			message+=isValidAmount(dto.getTaxValue());
			if (!message.equalsIgnoreCase("")){
				throw new IndustrikaValidationException(message);
			}
		}
	}
	
	public void validateDelete(Tax dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			message=isValidID(dto.getIdTax());
			if (!message.equalsIgnoreCase("")){
				throw new IndustrikaValidationException(message);
			}
		}
	}
	
	private String isValidID(Integer id){
		String message="";
		if (id == null ){
			if (message.equalsIgnoreCase("")){
				message += CommonsMessages.getMessage("error_not_empty")+": ";
			}
			message += AdministrationMessages.getMessage("tax.operation.id.notempty");
		}else if (id == -1 ){
			message += " "+AdministrationMessages.getMessage("tax.operation.amount.valid");
		}
		
		return message;
	}
	
	private String isValidAmount(Double amt){
		String message="";
		if (amt== null ){
			if (message.equalsIgnoreCase("")){
				message += CommonsMessages.getMessage("error_not_empty")+": ";
			}
			message += AdministrationMessages.getMessage("tax.operation.amount.notempty");
		}else if (amt == -1 ){
			message += " / "+AdministrationMessages.getMessage("tax.operation.amount.valid");
		}
		
		return message;
	}
	

}
