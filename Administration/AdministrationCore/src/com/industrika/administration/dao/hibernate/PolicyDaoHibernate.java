package com.industrika.administration.dao.hibernate;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
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
import com.industrika.administration.dao.PolicyDao;
import com.industrika.administration.dto.Policy;
import com.industrika.administration.i18n.AdministrationMessages;

@Repository("policyDaoHibernate")
public class PolicyDaoHibernate extends RootDao implements PolicyDao{

	private IndustrikaValidator<Policy> validator;
	private static final String TITLE = AdministrationMessages.getMessage("policy.Title");
	
	@Autowired
	@Qualifier("policyValidatorPredefined")
	public void setValidator(IndustrikaValidator<Policy> validator) {
		// TODO Auto-generated method stub
		if (validator==null){
			throw new IllegalArgumentException("Validator should not be null");
		}
		this.validator = validator;
	}

	@Override
	@Transactional
	public Policy save(Policy dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException {
		validator.validate(dto);
		Integer id = null;
		try {
			id = (Integer) sessionFactory.getCurrentSession().save(dto);
			dto.setIdPolicy(id);
		} catch (Exception ex) {
			
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_add") + TITLE,ex);
		}
		finally{
			sessionFactory.getCurrentSession().flush();
		}
		return dto;
	}
	
	

	@Override
	public void update(Policy vo) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		// TODO Auto-generated method stub
	}

	@Override
	public void remove(Policy vo) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public Policy findById(Integer id) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Policy.class);
		try {
				if ((id != null)&& (!id.equals(0))) {
					criteria.add(Restrictions.idEq(id));
				}
		} catch (Exception ex) {
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages.getMessage("error_persistence_executequery")
							+ TITLE, ex);
		}
		@SuppressWarnings("unchecked")
		List<Policy> results = Collections.checkedList(criteria.list(),Policy.class);
		
		if (results == null || results.size() <= 0) {
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages
							.getMessage("error_persistence_objectnotfound"));
		}
		if((results!=null)&&(results.size()>=1)){
			
			return results.get(0);
		}
		return null;
	}

	@Override
	public List<Policy> findAll() throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<Policy> find(Policy dto, String[] sortFields)
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		/*Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Policy.class);
		if (dto != null){
			if (dto.getIdPolicy() != null && dto.getIdPolicy().intValue() > 0 ){
				criteria.add(Restrictions.idEq(dto.getIdPolicy()));
			}
		}
		return criteria.list();*/
		return null;
	}

}
