package com.industrika.administration.validation.predefined;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import com.industrika.administration.dto.Account;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.administration.validation.AccountValidator;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
@Component
public class AccountValidatorPredefined implements AccountValidator {
	@Override
	public void validate(Account dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getLevel() == null || dto.getLevel()<0 || dto.getLevel()>3 ){
				if (message.equalsIgnoreCase("")){
					message += AdministrationMessages.getMessage("account.Level.select")+": ";
				}
			}
			if (dto.getRefNumber() == null || dto.getRefNumber().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += AdministrationMessages.getMessage("account.Name")+", ";
			}
			else{
				message=isValidLevel(dto.getRefNumber(), dto.getLevel());
			}
			
			if (dto.getAccountName() == null || dto.getAccountName().trim().equalsIgnoreCase("")){
				if (message.equalsIgnoreCase("")){
					message += CommonsMessages.getMessage("error_not_empty")+": ";
				}
				message += AdministrationMessages.getMessage("account.AccountName")+", ";
			}
			
			if (!message.equalsIgnoreCase("")){
				throw new IndustrikaValidationException(message);
			}
		}
	}
	
	private String isValidLevel(String refNumber, int level){
		String message="";
		Pattern pattern;
		Matcher matcher;
		Boolean matches=false;
	
		pattern = Pattern.compile("[a-zA-Z]+");
		matcher = pattern.matcher(refNumber);
		matches = matcher.matches();
		if(matches){
			return AdministrationMessages.getMessage("account.default.warning");
		}
		
		switch(level){
		case 1:
			pattern = Pattern.compile("([0-9]{1,3})$");
			matcher = pattern.matcher(refNumber);
			matches = !matcher.matches();
			break;
		case 2:
			pattern = Pattern.compile("([0-9]{1,3})([-]{1})([0-9]{1,3})$");
			matcher = pattern.matcher(refNumber);
			matches = !matcher.matches();
			break;
		case 3:
			pattern = Pattern.compile("([0-9]{1,3})([-]{1}[0-9]{1,3})([-]{1}[0-9]{1,3})$");
			matcher = pattern.matcher(refNumber);
			matches = !matcher.matches();
			break;
		}
		
		if(matches){
			switch(level){
			case 1:
				message = AdministrationMessages.getMessage("account.level1.warning");
				break;
			case 2:
				message = AdministrationMessages.getMessage("account.level2.warning");
				break;
			case 3:
				message = AdministrationMessages.getMessage("account.level3.warning");
				break;
			default:
				message = AdministrationMessages.getMessage("account.default.warning");
				break;
			}
		}
		return message;
	}
	@Override
	public Account validateParents(Account dto)
			throws IndustrikaValidationException {
		int level=dto.getLevel();
		String refnum="";
		Account ac=null;
		switch(level){
		case 2:
			refnum=(dto.getRefNumber().split("-"))[0];
			ac= new Account();
			ac.setRefNumber(refnum);
			break;
		case 3:
			refnum=(dto.getRefNumber().split("-"))[0]+"-"+(dto.getRefNumber().split("-"))[1];
			ac= new Account();
			ac.setRefNumber(refnum);
			break;
		}
		return ac;
	}
	
}
