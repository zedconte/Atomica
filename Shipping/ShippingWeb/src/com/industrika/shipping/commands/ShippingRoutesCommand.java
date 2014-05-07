package com.industrika.shipping.commands;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.utils.TextFormatter;
import com.industrika.shipping.dao.ShippingRouteDao;
import com.industrika.shipping.dto.ShippingRoute;
import com.industrika.shipping.i18n.ShippingMessages;

@Component ("shippingroutesCommand")
public class ShippingRoutesCommand implements IndustrikaCommand {
	@Autowired
	@Qualifier("shippingRouteDao")
	private ShippingRouteDao dao;
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form","/shipping/shippingroutes.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "resultados";
			ShippingRoute dto = makeDtoForWeb(parameters);
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					/*Add the Tax and retrieve response*/
					dto.setIdRoute(null);
					dao.save(dto);
					dto=null;
					results.put("message", ShippingMessages.getMessage("shipping.success.persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"idRoute","name"};
					Vector<ShippingRoute> lista = new Vector<ShippingRoute>(dao.find(dto, order));
					if (lista != null && lista.size() == 1){
						dto = lista.get(0);
					}
					
					if (lista != null && lista.size() >0){
						results.put("list", lista);
					}
					else{
						results.put("message", ShippingMessages.getMessage("route.search.notfound"));
					}
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					/*Search the bank*/
					dao.update(dto);
					results.put("message", ShippingMessages.getMessage("route.operation.update.persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new ShippingRoute();
					results.put("message", ShippingMessages.getMessage("route.operation.delete.persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			results.put("dto", dto);
			
		}
		
		
		return results;
	}
	
	private ShippingRoute makeDtoForWeb(Map<String, String[]> parameters){
		ShippingRoute dto = new ShippingRoute();
		if (parameters.get("idRoute") != null){
			try{
				dto.setIdRoute(new Integer(((String[])parameters.get("idRoute"))[0]));
			}catch(Exception ex){
				dto.setIdRoute(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		if (parameters.get("acronym") != null){
			dto.setAcronym(((String[])parameters.get("acronym"))[0]);
		}
		if (parameters.get("distance") != null){
			try{
				dto.setDistance((TextFormatter.getNumber(((String[])parameters.get("distance"))[0])).doubleValue());
			}catch(Exception ex){
				dto.setDistance(null);
			}
		}
		if (parameters.get("time") != null){
			try{
				dto.setTime((TextFormatter.getNumber(((String[])parameters.get("time"))[0])).doubleValue());
			}catch(Exception ex){
				dto.setDistance(null);
			}
		}
		
		return dto;
	}
}
