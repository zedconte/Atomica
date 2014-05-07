package com.industrika.inventory.validation.predefined;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.Purchase;
import com.industrika.inventory.i18n.InventoryMessages;
import com.industrika.inventory.validation.PurchaseValidator;

@Component("purchaseValidator")
public class PurchaseValidatorPredefined extends DocumentValidatorPredefined implements PurchaseValidator {

	@Override
	public void validate(Purchase dto) throws IndustrikaValidationException {
		String fields=super.verifyFields(dto);
		if (dto != null){
			if (dto.getProvider() == null || dto.getProvider().getIdPerson() == null || dto.getProvider().getIdPerson().intValue() <= 0){
				fields+=InventoryMessages.getMessage("purchase.Provider")+", ";
			}
			if (!StringUtils.isEmpty(fields)){
				throw new IndustrikaValidationException("Los siguientes datos no son opcionales, favor de especificarlos: "+fields.substring(0,fields.length()-2));
			}
		}

	}

}
