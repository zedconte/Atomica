package com.industrika.maintenance.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.industrika.commons.commands.AbstractCatalogCommand;
import com.industrika.commons.dao.GenericDao;
import com.industrika.maintenance.dto.ResourceType;

@Component("resourceTypeCommand")
@Scope("prototype")
public class ResourceTypeCommand extends AbstractCatalogCommand<Integer, ResourceType> {

	public ResourceTypeCommand() {
		setSuccessView("/maintenance/resourceType.jsp");
		setSortBy("name");
	}
	
	@Override
	@Autowired
	@Qualifier("ResourceTypeDaoHibernate")
	public void setDao(GenericDao<Integer, ResourceType> dao) {
		this.dao = dao;
	}

}
