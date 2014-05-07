package com.industrika.shipping.dao.hibernate;

import java.util.Collections;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;
import com.industrika.shipping.dao.ShippingRouteDao;
import com.industrika.shipping.dto.ShippingRoute;
import com.industrika.shipping.i18n.ShippingMessages;

@Repository("shippingRouteDao")
public class ShippingRouteDaoHibernate extends RootDao implements ShippingRouteDao {

	private IndustrikaValidator<ShippingRoute> validator;

	private static final String TITLE = ShippingMessages.getMessage("shipping.Title");

	@Autowired
	@Qualifier("shippingRouteValidatorPredefined")
	public void setValidator(IndustrikaValidator<ShippingRoute> validator) {
		// TODO Auto-generated method stub
		if (validator==null){
			throw new IllegalArgumentException("Validator should not be null");
		}
		this.validator = validator;
	}

	
	
	@Override
	@Transactional
	public ShippingRoute save(ShippingRoute vo)
			throws IndustrikaValidationException,
			IndustrikaPersistenceException {
		validator.validate(vo);
		Integer id=null;
		try {
			id = (Integer) sessionFactory.getCurrentSession().save(vo);
			vo.setIdRoute(id);
		} catch (Exception ex) {
			
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_add") + TITLE,ex);
		}
		finally{
			sessionFactory.getCurrentSession().flush();
		}
		vo.setIdRoute(id);
		return vo;
	}

	@Override
	@Transactional
	public void update(ShippingRoute vo) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(vo);
		try {
				sessionFactory.getCurrentSession().saveOrUpdate(vo);
		} catch (Exception ex) {
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_update")
							+ TITLE, ex);
		}
	}

	@Override
	@Transactional
	public void remove(ShippingRoute vo) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
			try {
				validator.validate(vo);
				sessionFactory.getCurrentSession().delete(vo);
			} catch (Exception ex) {
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_delete")
								+ TITLE, ex);
			}
		
		
	}
	@Override
	public ShippingRoute findById(Integer id)
			throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ShippingRoute> findAll() throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<ShippingRoute> find(ShippingRoute dto, String[] sortFields)
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ShippingRoute.class);
		
		try {
			if (dto != null) {
				if (dto.getIdRoute() != null
						&& dto.getIdRoute()>0) {
					criteria.add(Restrictions.idEq(dto.getIdRoute()));
				}
				
				if (dto.getName() != null) {
					if(!dto.getName().isEmpty()){
						criteria.add(Restrictions.like("name","%"+dto.getName()+"%"));
					}
				}
				
				if (dto.getName() != null) {
					if(!dto.getName().isEmpty()){
						criteria.add(Restrictions.like("name","%"+dto.getName()+"%"));
					}
				}
				
				if (dto.getAcronym() != null) {
					if(!dto.getName().isEmpty()){
						criteria.add(Restrictions.like("acronym","%"+dto.getName()+"%"));
					}
				}
				
			}
			if (sortFields != null && sortFields.length > 0) {
				for (int a = 0; a < sortFields.length; a++) {
					if (sortFields[a] != null
							&& !sortFields[a].trim().equalsIgnoreCase("")) {
						criteria.addOrder(Order.asc(sortFields[a]));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages.getMessage("error_persistence_executequery")
							+ TITLE, ex);
			
		}
		
		@SuppressWarnings("unchecked")
		List<ShippingRoute> results = Collections.checkedList(criteria.list(),ShippingRoute.class);
		
		if (results == null || results.size() <= 0) {
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages
							.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
