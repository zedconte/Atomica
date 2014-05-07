package com.industrika.productplanning.dao.hibernate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.hibernate.JpaDao;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;
import com.industrika.productplanning.dto.LifeCycle;
import com.industrika.productplanning.dao.LifeCycleDao;

@Repository("lifeCycleDaoHibernate")
public class LifeCycleDaoHibernate extends JpaDao<Integer, LifeCycle> implements LifeCycleDao {

	@Override
	@Transactional
	public List<LifeCycle> find(LifeCycle lifeCycle,
			String[] sortFields) throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
     
		List<LifeCycle> cycles = new ArrayList<LifeCycle>();

		if (lifeCycle.getIdLifeCycle() != null && lifeCycle.getIdLifeCycle() > 0) {
			try {
				lifeCycle = findById(lifeCycle.getIdLifeCycle());
			} catch (IndustrikaPersistenceException ex) {
				throw new RuntimeException(ex);
			}

			if (lifeCycle != null){
				cycles.add(lifeCycle);
			}
				
			return cycles;
		}
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LifeCycle.class);
			try {
				
				if (lifeCycle != null) {
					if (lifeCycle.getIdLifeCycle() != null && lifeCycle.getIdLifeCycle().intValue() > 0){
						criteria.add(Restrictions.eq("idLifeCycle", lifeCycle.getIdLifeCycle()));
					}
					if (lifeCycle.getLifeOrder() != null && lifeCycle.getLifeOrder().intValue() > 0){
						criteria.add(Restrictions.eq("lifeOrder", lifeCycle.getLifeOrder()));
					}
					if (!StringUtils.isEmpty(lifeCycle.getName())) {
						criteria.add(Restrictions.like("name", "%" + lifeCycle.getName() + "%"));
					}
					
					if (!StringUtils.isEmpty(lifeCycle.getLifeCycleName())) {
						criteria.add(Restrictions.like("lifeCycleName", "%" + lifeCycle.getLifeCycleName() + "%"));
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
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery") + lifeCycle.getClass());
			}

			@SuppressWarnings("unchecked")
			List<LifeCycle> list = criteria.list();
			return list;	
	}

	@Autowired
	@Qualifier("lifeCycleValidator")
	public void setValidator(IndustrikaValidator<LifeCycle> validator) {
		this.validator = validator;
	}
	
}
