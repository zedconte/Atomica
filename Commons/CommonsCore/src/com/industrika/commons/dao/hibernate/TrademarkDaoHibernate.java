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

import com.industrika.commons.dao.TrademarkDao;
import com.industrika.commons.dto.Trademark;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Repository("trademarkDaoHibernate")
public class TrademarkDaoHibernate extends JpaDao<Integer, Trademark> implements TrademarkDao {
	
	@Override
	@Transactional
	public List<Trademark> find(Trademark trademark, String[] sortFields)
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		
		List<Trademark> trademarks = new ArrayList<Trademark>();
		
		if (trademark.getIdTrademark() != null && trademark.getIdTrademark() > 0) {
			try {
				trademark = findById(trademark.getIdTrademark());
			} catch (IndustrikaPersistenceException ex) {
				throw new RuntimeException(ex);
			}
			
			if (trademark != null)
				trademarks.add(trademark);
			return trademarks;
		} else {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Trademark.class);
						
			try {
				
				if(trademark!= null){
					if(!StringUtils.isEmpty(trademark.getName())){
						criteria.add(Restrictions.like("name", "%" + trademark.getName() + "%"));
					}	
				}				

				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				if(sortFields != null && sortFields.length > 0){
					for(String field : sortFields){
						if(field!= null && StringUtils.isEmpty(field.trim())){
							criteria.addOrder(Order.asc(field));
						}
					}
				}

			} catch (Exception ex) {
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages
								.getMessage("error_persistence_executequery")
								+ trademark.getClass());
			}
			
			@SuppressWarnings("unchecked")
			List<Trademark> list = criteria.list();
			return list;
		}
	}
	
	@Autowired
	@Qualifier("trademarkValidator")
	public void setValidator(IndustrikaValidator<Trademark> validator){
		this.validator = validator;
	}
}
