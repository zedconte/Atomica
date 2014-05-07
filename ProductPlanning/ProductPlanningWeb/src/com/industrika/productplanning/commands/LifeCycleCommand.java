package com.industrika.productplanning.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.industrika.commons.commands.AbstractCatalogCommand;
import com.industrika.commons.dao.GenericDao;
import com.industrika.productplanning.dto.LifeCycle;

@Component("lifeCycleCommand")
@Scope("prototype")
public class LifeCycleCommand extends AbstractCatalogCommand<Integer, LifeCycle> {

	public LifeCycleCommand() {
		setSuccessView("/productplanning/lifeCycle.jsp");
		setSortBy("name");
	}

	@Override
	@Autowired
	@Qualifier("lifeCycleDaoHibernate")
	public void setDao(GenericDao<Integer, LifeCycle> dao) {
		this.dao = dao;
	}
}
