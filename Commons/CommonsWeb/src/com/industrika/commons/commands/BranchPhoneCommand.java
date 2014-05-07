package com.industrika.commons.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dto.Branch;

@Component("branchPhoneCommand")
public class BranchPhoneCommand extends AbstractCatalogCommand<Integer, Branch> {

	@Override
	@Autowired
	@Qualifier("branchDaoHibernate")
	public void setDao(GenericDao<Integer, Branch> dao) {
		this.dao = dao;
	}
}
