package com.industrika.commons.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dto.Warehouse;

@Component("warehouseCommand")
@Scope("prototype")
public class WarehouseCommand extends AbstractCatalogCommand<Integer, Warehouse> {

	public WarehouseCommand() {
		setSuccessView("/commons/warehouse.jsp");
		setSortBy("name");
	}

	@Override
	@Autowired
	@Qualifier("warehouseDaoHibernate")
	public void setDao(GenericDao<Integer, Warehouse> dao) {
		this.dao = dao;
	}
}
