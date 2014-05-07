package com.industrika.commons.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.PersonDao;
import com.industrika.commons.dto.Person;
import com.industrika.commons.i18n.CommonsMessages;

@Component ("personCommand")
public class PersonCommand implements IndustrikaCommand {
	
	@Autowired
	private PersonDao dao;
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		/*InputStream form = ReferenceClass.class.getResourceAsStream("person.jsp");
		results.put("form", form);*/
		results.put("form", "/commons/person.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Person dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Person>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdPerson(dao.add(dto));
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Person();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"firstName","lastName"};
					Vector<Person> lista = new Vector<Person>(dao.find(dto, order));
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

	private Person makeDtoForWeb(Map<String, String[]> parameters){
		Person dto = new Person();
		if (parameters.get("idPerson") != null){
			try{
				dto.setIdPerson(new Integer(((String[])parameters.get("idPerson"))[0]));
			}catch(Exception ex){
				dto.setIdPerson(null);
			}
		}
		if (parameters.get("firstName") != null){
			dto.setFirstName(((String[])parameters.get("firstName"))[0]);
		}
		if (parameters.get("lastName") != null){
			dto.setLastName(((String[])parameters.get("lastName"))[0]);
		}
		if (parameters.get("middleName") != null){
			dto.setMiddleName(((String[])parameters.get("middleName"))[0]);
		}
		if (parameters.get("gender") != null){
			dto.setGender(((String[])parameters.get("gender"))[0]);
		}
		if (parameters.get("email") != null){
			dto.setEmail(((String[])parameters.get("email"))[0]);
		}
		return dto;
	}
}
