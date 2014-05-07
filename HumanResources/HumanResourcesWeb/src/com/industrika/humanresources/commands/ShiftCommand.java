package com.industrika.humanresources.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dao.ShiftDao;
import com.industrika.humanresources.dto.Shift;

@Component ("shiftCommand")
public class ShiftCommand implements IndustrikaCommand {
	@Autowired
	private ShiftDao dao;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/humanresources/shift.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Shift dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Shift>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdShift(dao.add(dto));
					dto = new Shift();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Shift();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Shift();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"name"};
					Vector<Shift> lista = new Vector<Shift>(dao.find(dto, order));
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

	private Shift makeDtoForWeb(Map<String, String[]> parameters){
		Shift dto = new Shift();
		if (parameters.get("idShift") != null){
			try{
				dto.setIdShift(new Integer(((String[])parameters.get("idShift"))[0]));
			}catch(Exception ex){
				dto.setIdShift(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}

		return dto;
	}
}
