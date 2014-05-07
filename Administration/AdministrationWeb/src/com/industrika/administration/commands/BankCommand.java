package com.industrika.administration.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.industrika.administration.dao.BankDao;
import com.industrika.administration.dto.Bank;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.commons.commands.IndustrikaCommand;

@Component ("bankCommand")
public class BankCommand implements IndustrikaCommand {
	@Autowired
	private BankDao dao;
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form","/administration/bank.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Bank dto = makeDtoForWeb(parameters);
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					/*Add the bank and retrieve response*/
					dto.setIdBank(dao.add(dto));
					dto=null;
					results.put("message", AdministrationMessages.getMessage("bank.success.persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					results.put("message", AdministrationMessages.getMessage("bank.update.persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Bank();
					results.put("message", AdministrationMessages.getMessage("bank.remove.persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"idBank","name","acronym"};
					Vector<Bank> lista = new Vector<Bank>(dao.find(dto, order));
					if (lista != null && lista.size() == 1){
						dto = lista.get(0);
					}
					
					if (lista != null && lista.size() >0){
						results.put("list", lista);
					}
					else{
						results.put("message", AdministrationMessages.getMessage("bank.search.notfound"));
					}
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			results.put("dto", dto);
		}
		return results;
	}
	
	private Bank makeDtoForWeb(Map<String, String[]> parameters){
		Bank dto = new Bank();
		if (parameters.get("idBank") != null){
			try{
				dto.setIdBank(new Integer(((String[])parameters.get("idBank"))[0]));
			}catch(Exception ex){
				dto.setIdBank(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		if (parameters.get("acronym") != null){
			dto.setAcronym(((String[])parameters.get("acronym"))[0]);
		}
		
		return dto;
	}
}
