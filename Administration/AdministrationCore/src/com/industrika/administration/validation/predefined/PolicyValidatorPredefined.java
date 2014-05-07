package com.industrika.administration.validation.predefined;
import org.springframework.stereotype.Component;
import com.industrika.administration.dto.Policy;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.validation.IndustrikaValidator;
@Component("policyValidatorPredefined")
public class PolicyValidatorPredefined implements IndustrikaValidator<Policy> {

	@Override
	public void validate(Policy dto) throws IndustrikaValidationException {
		String message="";
		if (dto != null){
			if (dto.getIdPolicy()<0){
				message += AdministrationMessages.getMessage("policy.search.id.number");
			}

			if (!message.equalsIgnoreCase("")){
				throw new IndustrikaValidationException(message);
			}
		}
		
	}

}
