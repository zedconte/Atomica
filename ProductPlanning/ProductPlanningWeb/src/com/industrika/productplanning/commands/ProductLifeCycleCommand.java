package com.industrika.productplanning.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.industrika.commons.commands.AbstractCatalogCommand;
import com.industrika.commons.dao.GenericDao;
import com.industrika.productplanning.dto.ProductLifeCycle;

@Component("productLifeCycleCommand")
@Scope("prototype")
public class ProductLifeCycleCommand extends AbstractCatalogCommand<Integer, ProductLifeCycle> {

	public ProductLifeCycleCommand() {
		setSuccessView("/productplanning/productLifeCycle.jsp");
		setSortBy("name");
	}

	@Override
	@Autowired
	@Qualifier("productLifeCycleDaoHibernate")
	public void setDao(GenericDao<Integer, ProductLifeCycle> dao) {
		this.dao = dao;
	}
}
