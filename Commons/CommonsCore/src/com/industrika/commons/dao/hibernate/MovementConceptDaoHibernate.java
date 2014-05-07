package com.industrika.commons.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.industrika.commons.dao.MovementConteptDao;
import com.industrika.commons.dto.MovementConcept;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Repository("movementConceptDaoHibernate")
public class MovementConceptDaoHibernate extends JpaDao<Integer, MovementConcept> implements MovementConteptDao {

	@Override
	@Transactional
	public List<MovementConcept> find(MovementConcept movementConcept,
			String[] sortFields) throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {

		List<MovementConcept> movementConcepts = new ArrayList<MovementConcept>();

		if (movementConcept.getIdMovementConcept() != null
				&& movementConcept.getIdMovementConcept() > 0) {
			try {
				movementConcept = findById(movementConcept
						.getIdMovementConcept());
			} catch (IndustrikaPersistenceException ex) {
				throw new RuntimeException(ex);
			}

			if (movementConcept != null)
				movementConcepts.add(movementConcept);
			return movementConcepts;
		}
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovementConcept.class);

			try {
				
				if (movementConcept != null) {
					if (movementConcept.getIdMovementConcept() != null && movementConcept.getIdMovementConcept().intValue() > 0){
						criteria.add(Restrictions.eq("idMovementConcept", movementConcept.getIdMovementConcept()));
					}
					if (!StringUtils.isEmpty(movementConcept.getName())) {
						criteria.add(Restrictions.like("name", "%" + movementConcept.getName() + "%"));
					}
				}

				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				if (sortFields != null && sortFields.length > 0) {
					for (String field : sortFields) {
						if (field != null && StringUtils.isEmpty(field.trim())) {
							criteria.addOrder(Order.asc(field));
						}
					}
				}

			} catch (Exception ex) {
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery") + movementConcept.getClass());
			}

			@SuppressWarnings("unchecked")
			List<MovementConcept> list = criteria.list();
			return list;
		
	}

	@Autowired
	@Qualifier("movementConceptValidator")
	public void setValidator(IndustrikaValidator<MovementConcept> validator) {
		this.validator = validator;
	}
}
