package com.industrika.maintenance.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.maintenance.dao.IResourceDao;
import com.industrika.maintenance.dao.IResourceTypeDao;
import com.industrika.maintenance.dto.Resource;

@Component("resourceCommand")
public class ResourceCommand implements IndustrikaCommand {
	@Autowired
	@Qualifier("ResourceDaoHibernate")
	private IResourceDao dao;

	@Autowired
	@Qualifier("ResourceTypeDaoHibernate")
	private IResourceTypeDao daoResourceType;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/maintenance/resource.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Resource dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Resource>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dao.save(dto);
					dto = new Resource();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Resource();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Resource();
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
					Vector<Resource> lista = new Vector<Resource>(dao.find(dto, order));
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

	private Resource makeDtoForWeb(Map<String, String[]> parameters){
		Resource dto = new Resource();
		if (parameters.get("id") != null){
			try{
				dto.setId(new Integer(((String[])parameters.get("id"))[0]));
			}catch(Exception ex){
				dto.setId(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		if (parameters.get("resourceType") != null){
			String resourceType=((String[])parameters.get("resourceType"))[0];
			if (resourceType != null && !resourceType.equalsIgnoreCase("")){
				try {
					dto.setResourceType(daoResourceType.findById(new Integer(((String[])parameters.get("resourceType"))[0])));
				} catch (NumberFormatException | IndustrikaPersistenceException
						| IndustrikaObjectNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		return dto;
	}
}
