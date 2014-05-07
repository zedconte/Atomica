package com.industrika.commons.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dto.Module;

/**
 * @author jose.arellano
 */
@Component ("moduleCommand")
@Scope("prototype")
public class SystemModuleCommand extends AbstractCatalogCommand<Integer,Module> implements IndustrikaCommand {

	public SystemModuleCommand(){
		setSuccessView("/commons/module.jsp");
		setSortBy("name","description");
	}
	
	@Override
	@Autowired
	@Qualifier("systemModuleDaoHibernate")
	public void setDao(GenericDao<Integer,Module> dao){
		this.dao = dao;
	}
	
	
}
