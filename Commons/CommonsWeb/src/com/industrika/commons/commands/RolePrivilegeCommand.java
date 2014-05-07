package com.industrika.commons.commands;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dao.RoleDao;
import com.industrika.commons.dao.SystemPrivilegeDao;
import com.industrika.commons.dto.Privilege;
import com.industrika.commons.dto.Role;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.view.RolePrivilegeDTO;

/**
 * @author jose.arellano
 */
@Component("rolePrivilegeCommand")
public class RolePrivilegeCommand extends AbstractCatalogCommand<Integer, Role> {

	@Autowired
	@Qualifier("systemPrivilegeDaoHibernate")
	private SystemPrivilegeDao privilegeDao;
	
	private Privilege privilege;
	private RolePrivilegeDTO result;
	
	private Integer role_id;
	private Integer privilege_id;
	
	public RolePrivilegeCommand(){
		setSuccessView("/commons/rolePrivilege.jsp");
	}
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters){
		
		

		results = new HashMap<String, Object>();
		
		initBasicParameters(parameters);
		
		//read custom attributes from the parameter map
		try {
			readExtraArguments(parameters);
			
			processRequest();
			
		}catch(DataIntegrityViolationException e){
			results.put("error","No puede ser eliminado pues existe información que depende de ésta");
		} catch (Exception e) {
			results.put("error",e.getMessage());
		}
		
		
		return results;
	}
	
	private void readExtraArguments(Map<String, String[]> parameters) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException{
		
		try{
			this.role_id = new Integer(parameters.get("role_id")[0]);
			record = dao.findById(role_id);
		
			//privilege id can be null when accessing to the formulary by the first time ("search" action)
			if(parameters.get("privilege_id") != null && parameters.get("privilege_id").length > 0 &&
					!StringUtils.isBlank(parameters.get("privilege_id")[0]) && StringUtils.isNumeric(parameters.get("privilege_id")[0])){
				
				this.privilege_id = new Integer(parameters.get("privilege_id")[0]);
				
				privilege = privilegeDao.findById(privilege_id);
			}
		}catch(Exception ex){
			ex.getMessage();
		}			
	}
	
	private void processRequest() throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException{
		if (basicParameters.action != null && !basicParameters.action.equals(ACTIONS[4])){
			if (basicParameters.action.equals(ACTIONS[0])){
				//add
				saveRecord();
			}
			else if (basicParameters.action.equals(ACTIONS[2])){
				//delete
				removeRecord();
			}
			else if (basicParameters.action.equals(ACTIONS[3])){
				//search, when accessing by first time
				searchRecord();
			}
			else{
				throw new RuntimeException("Action not supported: "+basicParameters.action);
			}
			
			if(record != null){
				
				result = new RolePrivilegeDTO(record, privilege);
				result.setAvailablePrivileges(privilegeDao.findAll());
				
				results.put("dto", result);
			}
		}		
		results.put("form", successView);
	}
	
	@Override
	protected void saveRecord(){
		//Add the privilege to the current list
		try {
			
			RoleDao roleDao = (RoleDao) dao;
			record = roleDao.addPrivilege(record, privilege);
			
			results.put("message", CommonsMessages.getMessage("sucess_persistence"));
			privilege = null;
		} catch (Exception e) {
			results.put("error",e.getMessage());
		}
	}
	
	@Override
	protected void removeRecord(){
		//remove the privilege from the current list
		try {
			
			if (record.getPrivileges().remove(privilege)){
				
				RoleDao roleDao = (RoleDao) dao;
				
				record = roleDao.setPrivileges(record, record.getPrivileges());
				
				privilege = null;
			}
			
			results.put("message", CommonsMessages.getMessage("sucess_persistence"));
			
		} catch (Exception e) {
			results.put("error",e.getMessage());
		}
	}
	
	@Override
	protected void searchRecord(){
		
		
	}
	
	@Override
	@Autowired
	@Qualifier("roleDaoHibernate")
	public void setDao(GenericDao<Integer, Role> dao) {
		this.dao = dao;
	}

}
