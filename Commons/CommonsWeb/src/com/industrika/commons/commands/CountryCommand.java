package com.industrika.commons.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


//import org.hibernate.exception.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.CountryDao;
import com.industrika.commons.dto.Country;
import com.industrika.commons.i18n.CommonsMessages;

@Component ("countryCommand")
public class CountryCommand implements IndustrikaCommand {
	@Autowired
	private CountryDao dao;
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/commons/country.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Country dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Country>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdCountry(dao.add(dto));
					dto = new Country();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Country();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Country();
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
					Vector<Country> lista = new Vector<Country>(dao.find(dto, order));
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

	private Country makeDtoForWeb(Map<String, String[]> parameters){
		Country dto = new Country();
		if (parameters.get("idCountry") != null){
			try{
				dto.setIdCountry(new Integer(((String[])parameters.get("idCountry"))[0]));
			}catch(Exception ex){
				dto.setIdCountry(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		if (parameters.get("shortName") != null){
			dto.setShortName(((String[])parameters.get("shortName"))[0]);
		}
		return dto;
	}

}
