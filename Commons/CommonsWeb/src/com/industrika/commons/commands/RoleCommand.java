package com.industrika.commons.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dto.Role;

/**
 * @author jose.arellano
 */
@Component("roleCommand")
@Scope("prototype")
public class RoleCommand extends AbstractCatalogCommand<Integer, Role> {

	public RoleCommand(){
		setSuccessView("/commons/role.jsp");
		setSortBy("name","initials");
	}
	
	@Override
	@Autowired
	@Qualifier("roleDaoHibernate")
	public void setDao(GenericDao<Integer, Role> dao) {
		this.dao = dao;
	}

}
