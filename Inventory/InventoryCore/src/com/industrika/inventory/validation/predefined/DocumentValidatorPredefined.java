package com.industrika.inventory.validation.predefined;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.inventory.dto.Document;
import com.industrika.inventory.i18n.InventoryMessages;
import com.industrika.inventory.validation.DocumentValidator;

@Component("documentValidator")
public class DocumentValidatorPredefined implements DocumentValidator {

	@Override
	public void validate(Document dto) throws IndustrikaValidationException {
		if (dto != null){
			String fields=verifyFields(dto);
			if (!StringUtils.isEmpty(fields)){
				throw new IndustrikaValidationException("Los siguientes datos no son opcionales, favor de especificarlos: "+fields.substring(0,fields.length()-2));
			}
		}
	}

	@Override
	public String verifyFields(Document dto) {
		String fields="";
		if (dto != null){
			if (dto.getRows() == null || dto.getRows().size() <= 0){
				fields+=CommonsMessages.getMessage("buyorder.Rows")+", ";
			}
			if (StringUtils.isEmpty(dto.getFolio())){
				fields+=InventoryMessages.getMessage("buyorder.Folio")+", ";
			}
		}
		return fields;
	}

}
