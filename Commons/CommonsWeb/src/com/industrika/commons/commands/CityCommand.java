package com.industrika.commons.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.StateDao;
import com.industrika.commons.dao.CityDao;
import com.industrika.commons.dto.City;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;

@Component ("cityCommand")
public class CityCommand implements IndustrikaCommand {
	@Autowired
	private CityDao dao;

	@Autowired
	private StateDao daoState;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/commons/city.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			City dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<City>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdCity(dao.add(dto));
					dto = new City();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new City();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dto.setState(null);
					dao.remove(dto);
					dto = new City();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"state.name","name"};
					Vector<City> lista = new Vector<City>(dao.find(dto, order));
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

	private City makeDtoForWeb(Map<String, String[]> parameters){
		City dto = new City();
		if (parameters.get("idCity") != null){
			try{
				dto.setIdCity(new Integer(((String[])parameters.get("idCity"))[0]));
			}catch(Exception ex){
				dto.setIdCity(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		if (parameters.get("shortName") != null){
			dto.setShortName(((String[])parameters.get("shortName"))[0]);
		}
		if (parameters.get("idState") != null){
			String state=((String[])parameters.get("idState"))[0];
			if (state != null && !state.equalsIgnoreCase("")){
				try {
					dto.setState(daoState.get(new Integer(((String[])parameters.get("idState"))[0])));
				} catch (NumberFormatException | IndustrikaPersistenceException
						| IndustrikaObjectNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		return dto;
	}
}
