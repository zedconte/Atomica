package com.industrika.commons.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.BranchDao;
import com.industrika.commons.dao.CityDao;
import com.industrika.commons.dto.Address;
import com.industrika.commons.dto.Branch;
import com.industrika.commons.i18n.CommonsMessages;

@Component("branchCommand")
public class BranchCommand implements IndustrikaCommand {
	
	@Autowired
	private BranchDao dao;

	@Autowired
	private CityDao daoCity;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/commons/branch.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Branch dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Branch>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dao.save(dto);
					dto = new Branch();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Branch();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Branch();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"businessName"};
					Vector<Branch> lista = new Vector<Branch>(dao.find(dto, order));
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

	private Branch makeDtoForWeb(Map<String, String[]> parameters){
		Branch dto = new Branch();
		if (parameters.get("idBranch") != null){
			try{
				dto.setIdBranch(new Integer(((String[])parameters.get("idBranch"))[0]));				
			}catch(Exception ex){
				dto.setIdBranch(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		if (parameters.get("responsibleName") != null){
			dto.setResponsibleName(((String[])parameters.get("responsibleName"))[0]);
		}
		if (parameters.get("street") != null){
			String[] ids = ((String[])parameters.get("idaddress"));
			String[] streets = ((String[])parameters.get("street"));
			String[] externalNumbers = ((String[])parameters.get("extNumber"));
			String[] internalNumbers = ((String[])parameters.get("intNumber"));
			String[] suburbs = ((String[])parameters.get("suburb"));
			String[] cities = ((String[])parameters.get("city"));
			String[] zipCodes = ((String[])parameters.get("zipCode"));
			for (int a=0;a<streets.length;a++){
				Address address = new Address();
				try{
					address.setIdAddress(new Integer(ids[a]));
				}catch(Exception ex){
					ex.getMessage();
				}
				address.setExtNumber(externalNumbers[a]);
				address.setIntNumber(internalNumbers[a]);
				address.setStreet(streets[a]);
				address.setSuburb(suburbs[a]);
				address.setZipCode(zipCodes[a]);
				try{
					address.setCity(daoCity.get(new Integer(cities[a])));
				}catch(Exception ex){
					ex.getMessage();
				}
				dto.setAddress(address);
			}
		}

		return dto;
	}
}
