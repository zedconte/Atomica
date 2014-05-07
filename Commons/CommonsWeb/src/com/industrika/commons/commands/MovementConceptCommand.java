package com.industrika.commons.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dto.MovementConcept;

@Component("movementConceptCommand")
@Scope("prototype")
public class MovementConceptCommand extends AbstractCatalogCommand<Integer, MovementConcept> {

	public MovementConceptCommand() {
		setSuccessView("/commons/movementConcept.jsp");
		setSortBy("name");
	}

	@Override
	@Autowired
	@Qualifier("movementConceptDaoHibernate")
	public void setDao(GenericDao<Integer, MovementConcept> dao) {
		this.dao = dao;
	}
}
