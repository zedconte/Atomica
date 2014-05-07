package com.industrika.administration.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.industrika.administration.dao.BankDao;
import com.industrika.administration.dao.BankOperationDao;
import com.industrika.administration.dto.Bank;
import com.industrika.administration.dto.BankOperation;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;

@Component ("bankoperationCommand")
public class BankOperationCommand implements IndustrikaCommand {
	@Autowired
	@Qualifier("bankOperationDaoHibernate")
	private BankOperationDao dao;
	
	@Autowired
	@Qualifier("bankDao")
	private BankDao bankDao;
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form","/administration/bankoperations.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "resultados";
			BankOperation dto= makeDtoForWeb(parameters);
			String bankActs=dto.getBankAccounts();
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					/*Search the bank*/
					Bank bank=searchMyBank(dto);
					dto.setBank(bank);
					
					/*Add the BankOperation*/
					dao.save(dto);
					results.put("message", AdministrationMessages.getMessage("bank.operation.success.persistence"));
					dto=new BankOperation();
					dto.setBankAccounts(bankActs);
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"idOperation","bank","amount"};
					Vector<BankOperation> lista = new Vector<BankOperation>(dao.find(dto, order));
					if (lista != null && lista.size() == 1){
						dto = lista.get(0);
						dto.setBankAccounts(bankActs);
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
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					/*Search the bank*/
					Bank bank=searchMyBank(dto);
					dto.setBank(bank);
					dao.update(dto);
					results.put("message", AdministrationMessages.getMessage("bank.operation.update.persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dto.setDescription(null);
					dto.setOperationType(null);
					dto.setBank(null);
					dao.remove(dto);
					dto = new BankOperation();
					dto.setBankAccounts(bankActs);
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
	
	private BankOperation makeDtoForWeb(Map<String, String[]> parameters){
		BankOperation dto = new BankOperation();
		if (parameters.get("bankAccounts") == null || ((String[])parameters.get("bankAccounts"))[0].trim() ==""){
			try{
				Bank bank= new Bank();
				String[] order={"idBank","name","acronym"};
				List<Bank> banks = (bankDao.find(bank, order));
				if (banks != null && banks.size() >= 1){
					/*Convert the banks into JSON*/
					Bank[] banksArray=banks.toArray(new Bank[banks.size()]);
					Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
					String json = gson.toJson(banksArray);
					dto.setBankAccounts(json);
				}
				
				
			}catch(Exception ex){
				dto.setBankAccounts(null);
			}
		}
		else{
			dto.setBankAccounts(((String[])parameters.get("bankAccounts"))[0].trim());
		}
		
		if (parameters.get("operationType") != null){
			if(((String[])parameters.get("operationType")!=null)){
				dto.setOperationType(((String[])parameters.get("operationType"))[0]);
			}
			
		}
		
		/*ID Bank*/
		try{
			if (parameters.get("bankId") != null){
				if(((String[])parameters.get("bankId")!=null)){
					dto.setBankId(new Integer(((String[])parameters.get("bankId"))[0]));
				}
				
			}
		}catch(Exception e){dto.setBankId(null);}
		
		/*ID operation*/
		if (parameters.get("idOperation") != null){
			if(((String[])parameters.get("idOperation")!=null)){
				
					if(!((String[])parameters.get("idOperation"))[0].isEmpty()){
						try{
							dto.setIdOperation(new Integer(((String[])parameters.get("idOperation"))[0]));
						}
						catch(Exception e){
							dto.setIdOperation(-1);
						}
					}
					else{
						dto.setIdOperation(null);
					}
			}
			
		}
		
		
		
		/*description*/
		if (parameters.get("description") != null){
			if(((String[])parameters.get("description")!=null)){
				dto.setDescription(((String[])parameters.get("description"))[0]);
			}
			
		}
		
		/*ID operation*/
		if (parameters.get("amount") != null){
			if(((String[])parameters.get("amount")!=null)){
				
					if(!((String[])parameters.get("amount"))[0].isEmpty()){
						try{
							dto.setAmount(new Double(((String[])parameters.get("amount"))[0]));
						}
						catch(Exception e){
							e.printStackTrace();
							dto.setAmount(new Double(-1));
						}
					}
					else{
						dto.setAmount(null);
					}
			}
		}
		
		
		return dto;
	}
	
	private Bank searchMyBank(BankOperation dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException{
		/*Search my bank*/
		Bank bank= new Bank();
		bank.setIdBank(dto.getBankId());
		String[] order={"idBank","name","acronym"};
		Vector<Bank> lista = new Vector<Bank>(bankDao.find(bank, order));
		if (lista != null && lista.size() == 1){
			bank = lista.get(0);
		}
		return bank;
	}
}
