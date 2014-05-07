package com.industrika.humanresources.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dao.DirectionDao;
import com.industrika.humanresources.dto.Direction;

@Component ("directionCommand")
public class DirectionCommand implements IndustrikaCommand {
	@Autowired
	private DirectionDao dao;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/humanresources/direction.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Direction dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Direction>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdDirection(dao.add(dto));
					dto = new Direction();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Direction();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Direction();
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
					Vector<Direction> lista = new Vector<Direction>(dao.find(dto, order));
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

	private Direction makeDtoForWeb(Map<String, String[]> parameters){
		Direction dto = new Direction();
		if (parameters.get("idDirection") != null){
			try{
				dto.setIdDirection(new Integer(((String[])parameters.get("idDirection"))[0]));
			}catch(Exception ex){
				dto.setIdDirection(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}

		return dto;
	}
}
