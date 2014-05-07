package com.industrika.commons.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dto.Trademark;

@Component("trademarkCommand")
@Scope("prototype")
public class TrademarkCommand extends
		AbstractCatalogCommand<Integer, Trademark> {

	public TrademarkCommand() {
		setSuccessView("/commons/trademark.jsp");
		setSortBy("name");
	}

	@Override
	@Autowired
	@Qualifier("trademarkDaoHibernate")
	public void setDao(GenericDao<Integer, Trademark> dao) {
		this.dao = dao;

	}
}
