package com.industrika.commons.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.CountryDao;
import com.industrika.commons.dao.StateDao;
import com.industrika.commons.dto.State;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;

@Component ("stateCommand")
public class StateCommands implements IndustrikaCommand {
	@Autowired
	private StateDao dao;

	@Autowired
	private CountryDao daoCountry;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/commons/state.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			State dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<State>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdState(dao.add(dto));
					dto = new State();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new State();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new State();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"country.name","name"};
					Vector<State> lista = new Vector<State>(dao.find(dto, order));
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

	private State makeDtoForWeb(Map<String, String[]> parameters){
		State dto = new State();
		if (parameters.get("idState") != null){
			try{
				dto.setIdState(new Integer(((String[])parameters.get("idState"))[0]));
			}catch(Exception ex){
				dto.setIdState(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		if (parameters.get("shortName") != null){
			dto.setShortName(((String[])parameters.get("shortName"))[0]);
		}
		if (parameters.get("idCountry") != null){
			String country=((String[])parameters.get("idCountry"))[0];
			if (country != null && !country.equalsIgnoreCase("")){
				try {
					dto.setCountry(daoCountry.get(new Integer(((String[])parameters.get("idCountry"))[0])));
				} catch (NumberFormatException | IndustrikaPersistenceException
						| IndustrikaObjectNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		return dto;
	}
}
