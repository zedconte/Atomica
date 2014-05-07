package com.industrika.administration.commands;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.industrika.administration.dao.AccountDao;
import com.industrika.administration.dto.Account;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;

@Component ("reportescontablesCommand")
public class ReportsCommand implements IndustrikaCommand {
	@Autowired
	@Qualifier("accountDao")
	private AccountDao dao;
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form","/administration/reportes.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "resultados";
			String[] order={"refNumber","accountName"};
			Vector<Account> lista=null;
			try {
				if(action.equals("resultados")){
					/*Cuentas de Mayor, 1 y 2, level<=2*/	
					lista = new Vector<Account>(dao.findbyLevel(2, order, false));
				}
				if(action.equals("comprobacion")){
					lista = new Vector<Account>(dao.findbyLevel(3, order, false));
				}
			} catch (IndustrikaPersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IndustrikaObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (lista != null && lista.size() >0){
				results.put("list", lista);
			}
			
			results.put("message", action);
		}
		return results;
	}
}
