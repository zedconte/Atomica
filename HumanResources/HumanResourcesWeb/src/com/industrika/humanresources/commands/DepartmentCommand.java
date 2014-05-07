package com.industrika.humanresources.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dao.DepartmentDao;
import com.industrika.humanresources.dao.ManagementDao;
import com.industrika.humanresources.dto.Department;

@Component ("departmentCommand")
public class DepartmentCommand implements IndustrikaCommand {
	@Autowired
	private DepartmentDao dao;

	@Autowired
	private ManagementDao daoManagement;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/humanresources/department.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Department dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Department>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdDepartment(dao.add(dto));
					dto = new Department();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Department();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Department();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"management.name","name"};
					Vector<Department> lista = new Vector<Department>(dao.find(dto, order));
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

	private Department makeDtoForWeb(Map<String, String[]> parameters){
		Department dto = new Department();
		if (parameters.get("idDepartment") != null){
			try{
				dto.setIdDepartment(new Integer(((String[])parameters.get("idDepartment"))[0]));
			}catch(Exception ex){
				dto.setIdDepartment(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		if (parameters.get("idManagement") != null){
			String management=((String[])parameters.get("idManagement"))[0];
			if (management != null && !management.equalsIgnoreCase("")){
				try {
					dto.setManagement(daoManagement.get(new Integer(((String[])parameters.get("idManagement"))[0])));
				} catch (NumberFormatException | IndustrikaPersistenceException
						| IndustrikaObjectNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		return dto;
	}
}
