/**
 * 
 */
package com.industrika.commons.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dto.Action;

/**
 * @author jose.arellano
 *
 */
@Component("systemActionCommand")
@Scope("prototype")
public class SystemActionCommand extends AbstractCatalogCommand<Integer, Action> {

	public SystemActionCommand(){
		setSuccessView("/commons/action.jsp");
		setSortBy("type","description");
	}
	
	@Override
	@Autowired
	@Qualifier("systemActionDaoHibernate")
	public void setDao(GenericDao<Integer, Action> dao) {
		this.dao = dao;
		
	}

}
