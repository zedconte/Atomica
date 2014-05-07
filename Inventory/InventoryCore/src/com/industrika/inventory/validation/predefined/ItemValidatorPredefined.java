package com.industrika.inventory.validation.predefined;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.Item;
import com.industrika.inventory.i18n.InventoryMessages;
import com.industrika.inventory.validation.ItemValidator;

@Component("itemValidator")
public class ItemValidatorPredefined implements ItemValidator {

	@Override
	public void validate(Item item) throws IndustrikaValidationException {
		String fields = "";
		if (item != null){
			if (StringUtils.isEmpty(item.getCode())){
				fields += InventoryMessages.getMessage("item.Code") + ", ";
			}
			if (StringUtils.isEmpty(item.getName())){
				fields += InventoryMessages.getMessage("item.Name") + ", ";
			}
		}
		if (!StringUtils.isEmpty(fields)){
			throw new IndustrikaValidationException("Los siguientes datos no son opcionales, favor de especificarlos: "+fields.substring(0,fields.length()-2));
		}
	}

}
