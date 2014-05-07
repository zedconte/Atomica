package com.industrika.commons.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.CurrencyDao;
import com.industrika.commons.dto.Currency;
import com.industrika.commons.i18n.CommonsMessages;

@Component ("currencyCommand")
public class CurrencyCommand implements IndustrikaCommand {
	@Autowired
	private CurrencyDao dao;
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		/*InputStream form = ReferenceClass.class.getResourceAsStream("person.jsp");
		results.put("form", form);*/
		results.put("form", "/commons/currency.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Currency dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Currency>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdCurrency(dao.add(dto));
					dto = new Currency();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Currency();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Currency();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"name","name"};
					Vector<Currency> lista = new Vector<Currency>(dao.find(dto, order));
					if (lista != null && lista.size() == 1){
						dto = lista.get(0);
					}
					results.put("list", lista);
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			results.put("dto", dto);
		}
		return results;
	}

	private Currency makeDtoForWeb(Map<String, String[]> parameters){
		Currency dto = new Currency();
		if (parameters.get("idCurrency") != null){
			try{
				dto.setIdCurrency(new Integer(((String[])parameters.get("idCurrency"))[0]));
			}catch(Exception ex){
				dto.setIdCurrency(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		if (parameters.get("shortName") != null){
			dto.setShortName(((String[])parameters.get("shortName"))[0]);
		}
		if (parameters.get("symbol") != null){
			dto.setSymbol(((String[])parameters.get("symbol"))[0]);
		}
		return dto;
	}
}
