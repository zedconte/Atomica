package com.industrika.administration.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.industrika.administration.dao.AccountDao;
import com.industrika.administration.dao.PolicyDao;
import com.industrika.administration.dto.Account;
import com.industrika.administration.dto.Policy;
import com.industrika.administration.dto.PolicyRow;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;

@Component ("policyCommand")
public class PolicyCommand implements IndustrikaCommand {
	
	private PolicyDao dao;
	
	@Autowired
	@Qualifier("policyDaoHibernate")
	public void setDao(PolicyDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	@Qualifier("accountDao")
	private AccountDao accountDao;
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form","/administration/policy.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Policy dto = makeDtoForWeb(parameters);
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					if(!this.exist(dto)){
						/*update accounts*/
						for(PolicyRow row: dto.getRows()){
							Account account= new Account();
							double amount=0;
							account.setRefNumber(row.getAccountNumber());
							
							if(row.getCredit()>0){
								amount=amount-row.getCredit();
							}
							
							if(row.getDebit()>0){
								amount=amount+row.getDebit();
							}
							account.setBalance(amount);
							accountDao.update(account, true);
						}
						/*Add the policy*/
						dao.save(dto);
						dto = new Policy();
						results.put("message", AdministrationMessages.getMessage("policy.success.persistence"));
					}
					else{
						/*dao.update(dto);
						String act=dto.getAccountsDB();
						dto = new Policy();
						dto.setAccountsDB(act);*/
						results.put("message", AdministrationMessages.getMessage("policy.success.persistence.update"));
					}
					
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					if(dto.getIdPolicy()!=null){
						if(dto.getIdPolicy()>0){
							Policy search = dao.findById(dto.getIdPolicy());
							if(search!=null){
								search.setAccountsDB(dto.getAccountsDB());
								search.setRowsAsJson();
								dto=search;
							}
							else{
								results.put("message", AdministrationMessages.getMessage("policy.search.notfound"));
							}
						}
						else{
							results.put("message", AdministrationMessages.getMessage("policy.search.id.number"));
						}
					}
					else{
						String[] sort={"idPolicy"};
						dto.setAccountsDB(null);
						List<Policy> list = dao.find(dto, sort);
						if (list != null && list.size() > 0){
							results.put("list", list);
						}
						Vector<Policy> lista = new Vector<Policy>(dao.find(dto, null));
						String act=dto.getAccountsDB();
						if (lista != null && lista.size() == 1){
							dto = lista.get(0);
							dto.setAccountsDB(act);
							dto.setRowsAsJson();
						}
						if (lista != null && lista.size() >0){
							results.put("list", lista);
						}
						else{
							results.put("message", AdministrationMessages.getMessage("policy.search.notfound"));
						}
						
					}
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			results.put("dto", dto);
		}
		return results;
	}
	
	private Policy makeDtoForWeb(Map<String, String[]> parameters){
		Policy dto = new Policy();
		if (parameters.get("policyList") != null){
			try{
				if(((String[])parameters.get("policyList"))!=null){
					String listPolicyJSon=((String[])parameters.get("policyList"))[0];
					if(listPolicyJSon!=null && !listPolicyJSon.isEmpty()){
						Gson gson = new Gson();
						List<PolicyRow> myRows= Arrays.asList(gson.fromJson(listPolicyJSon, PolicyRow[].class));
						List<PolicyRow> myRowsWithPolicy= new ArrayList<PolicyRow>();
						for(PolicyRow row: myRows){
							row.setPolicy(dto);
							myRowsWithPolicy.add(row);
						}
						dto.setRows(new HashSet<PolicyRow>(myRowsWithPolicy));
					}
				}
			}catch(Exception ex){
				throw new RuntimeException(ex);
			}
		}
		
		if (parameters.get("accountsDB") == null || ((String[])parameters.get("accountsDB"))[0].trim() ==""){
			try {
				Account searching= new Account();
				searching.setLevel(3);
				String[] order={"refNumber","accountName"};
				List<Account> acs=accountDao.find(searching,order);
				StringBuilder accountsSt= new StringBuilder("");
				int i=1;
				for(Account ac: acs){
					accountsSt.append(ac.getRefNumber()+":"+ac.getRefNumber());			
					i++;
					if(i<=acs.size()){
						accountsSt.append(";");
					}
				}
				dto.setAccountsDB(accountsSt.toString());
			} catch (IndustrikaPersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IndustrikaObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			dto.setAccountsDB(((String[])parameters.get("accountsDB"))[0].trim());
		}
		
		if (parameters.get("idPolicy") != null){
			if((((String[])parameters.get("idPolicy"))!=null)){
				String id=((String[])parameters.get("idPolicy"))[0];
				if(id!=null && !id.isEmpty()){
					try{
						dto.setIdPolicy(new Integer(id));
					}catch(NumberFormatException e){
						dto.setIdPolicy(-1);
					}
				}
				else{
					dto.setIdPolicy(null);
				}
			}
			else{
				dto.setIdPolicy(null);
			}
		}
		else{
			dto.setIdPolicy(null);
		}
		
		if (parameters.get("policyDate") != null){
			if(((String[])parameters.get("policyDate")!=null)){
				dto.setPolicyDate(((String[])parameters.get("policyDate"))[0]);
			}
			
		}
		
		if (parameters.get("policyType") != null){
			if(((String[])parameters.get("policyType")!=null)){
				dto.setPolicyType(((String[])parameters.get("policyType"))[0]);
			}
			
		}
		
		return dto;
	}
	
	private Boolean exist(Policy policy){
		Policy search = null;
		try {
			search = dao.findById(policy.getIdPolicy());
		} catch (IndustrikaPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndustrikaObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(search!=null){
			return true;
		}
		
		return false;
	}
}
