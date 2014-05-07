package com.industrika.administration.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.industrika.administration.dao.AccountDao;
import com.industrika.administration.dto.Account;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.commons.commands.IndustrikaCommand;

@Component ("accountCommand")
public class AccountCommand implements IndustrikaCommand {
	@Autowired
	@Qualifier("accountDao")
	private AccountDao dao;
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form","/administration/account.jsp");
		
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Account dto = makeDtoForWeb(parameters);
			
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					/*Add the bank and retrieve response*/
					dto.setRefNumber(dao.add(dto));
					dto=null;
					results.put("message", AdministrationMessages.getMessage("account.success.persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto,false);
					results.put("message", AdministrationMessages.getMessage("bank.update.persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"refNumber","accountName"};
					dto.setLevel(null);
					Vector<Account> lista = new Vector<Account>(dao.find(dto, order));
					if (lista != null && lista.size() == 1){
						dto = lista.get(0);
					}
					
					if (lista != null && lista.size() >0){
						results.put("list", lista);
					}
					else{
						results.put("message", AdministrationMessages.getMessage("account.search.notfound"));
					}
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			results.put("dto", dto);
		}
		return results;
	}
	
	private Account makeDtoForWeb(Map<String, String[]> parameters){
		Account dto = new Account();
		dto.setBalance(0.0);
		if (parameters.get("refNumber") != null){
			try{
				dto.setRefNumber((((String[])parameters.get("refNumber"))[0]));
			}catch(Exception ex){
				dto.setRefNumber(null);
			}
		}
		if (parameters.get("accountName") != null){
			dto.setAccountName(((String[])parameters.get("accountName"))[0]);
		}
		if (parameters.get("level") != null){
			dto.setLevel(new Integer(((String[])parameters.get("level"))[0]));
		}
		
		return dto;
	}
}
