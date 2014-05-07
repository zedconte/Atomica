package com.industrika.administration.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.administration.dao.DeductionDao;
import com.industrika.administration.dto.Deduction;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.utils.TextFormatter;

@Component ("deductionCommand")
public class DeductionCommand implements IndustrikaCommand {
	
	@Autowired
	private DeductionDao dao;
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form","/administration/deduction.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Deduction dto = makeDtoForWeb(parameters);
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					/*Add the Deduction and retrieve response*/
					dao.add(dto);
					dto=null;
					results.put("message", AdministrationMessages.getMessage("deduction.success.persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"idDeduction","name","initials"};
					Vector<Deduction> lista = new Vector<Deduction>(dao.find(dto, order));
					if (lista != null && lista.size() == 1){
						dto = lista.get(0);
					}
					
					if (lista != null && lista.size() >0){
						results.put("list", lista);
					}
					else{
						results.put("message", AdministrationMessages.getMessage("deduction.search.notfound"));
					}
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					results.put("message", AdministrationMessages.getMessage("deduction.operation.update.persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
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
	
	private Deduction makeDtoForWeb(Map<String, String[]> parameters){
		Deduction dto = new Deduction();
		
		if (parameters.get("idDeduction") != null){
			try{
				dto.setIdDeduction(new Integer(((String[])parameters.get("idDeduction"))[0]));
			}
			catch(Exception e){
				dto.setIdDeduction(null);
			}
		}
		
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		
		if (parameters.get("initials") != null){
			dto.setInitials(((String[])parameters.get("initials"))[0]);
		}	
		
		if (parameters.get("value") != null){
			try{
				dto.setValue((TextFormatter.getNumber(((String[])parameters.get("value"))[0])).doubleValue());
			}
			catch(Exception e){
				dto.setValue(null);
			}
		}
		
		return dto;
	}
	

}
