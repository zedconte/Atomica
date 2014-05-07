package com.industrika.inventory.validation.predefined;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.InventoryMovement;
import com.industrika.inventory.i18n.InventoryMessages;
import com.industrika.inventory.validation.InventoryMovementValidator;

@Component("inventorymovementValidator")
public class InventoryMovementValidatorPredefined implements InventoryMovementValidator {

	@Override
	public void validate(InventoryMovement dto)
			throws IndustrikaValidationException {
		String fields = "";
		if (dto != null){
			if (dto.getItem() == null || dto.getItem().getIdItem() == null){
				fields += InventoryMessages.getMessage("inventorymovement.Item") + ", ";
			}
			if (dto.getQuantity() == null || dto.getQuantity().doubleValue() < 0){
				fields += InventoryMessages.getMessage("inventorymovement.Quantity") + ", ";
			}
			if (dto.getType() == null || dto.getType().intValue() < 0){
				fields += InventoryMessages.getMessage("inventorymovement.Type") + ", ";
			}
			if (dto.getConcept() == null || dto.getConcept().getIdMovementConcept() == null){
				fields += InventoryMessages.getMessage("inventorymovement.Concept") + ", ";
			}
		}
		if (!StringUtils.isEmpty(fields)){
			throw new IndustrikaValidationException("Los siguientes datos no son opcionales, favor de especificarlos: "+fields.substring(0,fields.length()-2));
		}				
	}

}
