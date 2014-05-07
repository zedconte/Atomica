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
import com.industrika.commons.dao.UserDao;
import com.industrika.commons.dto.Role;
import com.industrika.commons.dto.User;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.view.UserRoleDTO;

/**
 * @author jose.arellano
 */
@Component("userRoleCommand")
public class UserRoleCommand extends AbstractCatalogCommand<Integer, User> {

	@Autowired
	@Qualifier("roleDaoHibernate")
	private RoleDao roleDao;
	
	private Role role;
	private UserRoleDTO result;
	
	private Integer user_id;
	private Integer role_id;
	
	public UserRoleCommand(){
		setSuccessView("/commons/userRole.jsp");
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
			this.user_id = new Integer(parameters.get("user_id")[0]);
			record = ((UserDao)dao).findUserWithRoles(user_id);
			
			//role id can be null when accessing to the formulary by the first time ("search" action)
			if(parameters.get("role_id") != null && parameters.get("role_id").length > 0 &&
					!StringUtils.isBlank(parameters.get("role_id")[0]) && StringUtils.isNumeric(parameters.get("role_id")[0])){
				
				this.role_id = new Integer(parameters.get("role_id")[0]);
				role = roleDao.findById(role_id);
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
		}		
		if(record != null){
			
			result = new UserRoleDTO(record, role);
			result.setAvailableRoles(roleDao.findAll());
			
			results.put("dto", result);
		}
		
		results.put("form", successView);
	}
	
	@Override
	protected void saveRecord(){
		//Add the role to the current list
		try {
			
			UserDao userDao = (UserDao) dao;
			record = userDao.addRole(record, role);
			
			results.put("message", CommonsMessages.getMessage("sucess_persistence"));
			role = null;
		} catch (Exception e) {
			results.put("error",e.getMessage());
		}
	}
	
	@Override
	protected void removeRecord(){
		//remove the rolefrom the current list
		try {
			
			if (record.getRoles().remove(role)){
				
				UserDao userDao = (UserDao) dao;
				
				record = userDao.setRoles(record, record.getRoles());
				
				role = null;
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
	@Qualifier("userDaoHibernate")
	public void setDao(GenericDao<Integer, User> dao) {
		this.dao = dao;
	}

}
