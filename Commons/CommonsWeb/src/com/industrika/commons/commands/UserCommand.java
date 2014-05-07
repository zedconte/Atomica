package com.industrika.commons.commands;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dao.UserDao;
import com.industrika.commons.dto.User;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;

/**
 * @author jose.arellano
 */
@Component("userCommand")
@Scope("prototype")
public class UserCommand extends AbstractCatalogCommand<Integer, User> {

	public UserCommand(){
		setSuccessView("/commons/user.jsp");
		setSortBy("code");
	}
	
	protected void searchRecord() throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException{
		super.searchRecord();
		if (listRecords.size() == 1){
			record = ((UserDao)dao).findUserWithRoles(record.getId());
			listRecords.remove(0);
			listRecords.add(record);
			results.put("dto", record);
		}
	}
	
	protected void initBasicParameters(Map<String, String[]> parameters){
		parameters.remove("confirmPassword");
		super.initBasicParameters(parameters);
	}
	
	@Override
	@Autowired
	@Qualifier("userDaoHibernate")
	public void setDao(GenericDao<Integer, User> dao) {
		this.dao = dao;
	}

}
