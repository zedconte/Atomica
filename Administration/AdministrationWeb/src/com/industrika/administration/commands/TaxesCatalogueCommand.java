package com.industrika.administration.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.industrika.administration.dao.TaxDao;
import com.industrika.administration.dto.Tax;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.commons.commands.IndustrikaCommand;

@Component ("taxescatalogueCommand")
public class TaxesCatalogueCommand implements IndustrikaCommand {
	
	@Autowired
	@Qualifier("taxDao")
	private TaxDao dao;
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form","/administration/tax.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Tax dto = makeDtoForWeb(parameters);
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					/*Add the Tax and retrieve response*/
					dao.save(dto);
					dto=null;
					results.put("message", AdministrationMessages.getMessage("tax.success.persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"idTax","name","initials"};
					Vector<Tax> lista = new Vector<Tax>(dao.find(dto, order));
					if (lista != null && lista.size() == 1){
						dto = lista.get(0);
					}
					
					if (lista != null && lista.size() >0){
						results.put("list", lista);
					}
					else{
						results.put("message", AdministrationMessages.getMessage("tax.search.notfound"));
					}
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					results.put("message", AdministrationMessages.getMessage("tax.operation.update.persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dto.setPercentage(null);
					dto.setTaxValue(null);
					dao.remove(dto);
					dto=null;
					results.put("message", AdministrationMessages.getMessage("bank.operation.delete.persistence"));
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
	
	private Tax makeDtoForWeb(Map<String, String[]> parameters){
		Tax dto = new Tax();
		
		if (parameters.get("idTax") != null){
			if(((String[])parameters.get("idTax")!=null)){
					if(!((String[])parameters.get("idTax"))[0].isEmpty()){
						try{
							dto.setIdTax(new Integer(((String[])parameters.get("idTax"))[0]));
						}
						catch(Exception e){
							e.printStackTrace();
							dto.setTaxValue(new Double(-1));
						}
					}
					else{
						dto.setIdTax(null);
					}
			}
		}
		
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		
		if (parameters.get("initials") != null){
			dto.setInitials(((String[])parameters.get("initials"))[0]);
		}
		
		if (parameters.get("taxType") != null){
			String taxType=((String[])parameters.get("taxType"))[0];
			if(Tax.TAXTYPE.PERCENTAGE.toString().equals(taxType)){
				dto.setPercentage(new Boolean(true));
			}
			else{
				dto.setPercentage(new Boolean(false));
			}
		}
		
		
		if (parameters.get("taxValue") != null){
			if(((String[])parameters.get("taxValue")!=null)){
					if(!((String[])parameters.get("taxValue"))[0].isEmpty()){
						try{
							dto.setTaxValue(new Double(((String[])parameters.get("taxValue"))[0]));
						}
						catch(Exception e){
							e.printStackTrace();
							dto.setTaxValue(new Double(-1));
						}
					}
					else{
						dto.setTaxValue(null);
					}
			}
		}
		
		return dto;
	}
	

}
