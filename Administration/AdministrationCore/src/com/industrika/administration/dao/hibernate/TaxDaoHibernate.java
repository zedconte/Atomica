package com.industrika.administration.dao.hibernate;
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
import com.industrika.administration.dao.TaxDao;
import com.industrika.administration.dto.Tax;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.administration.validation.predefined.TaxValidatorPredefined;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Repository("taxDao")
public class TaxDaoHibernate extends RootDao implements TaxDao {

	private IndustrikaValidator<Tax> validator;

	private static final String TITLE = AdministrationMessages.getMessage("tax.Title");

	@Autowired
	@Qualifier("taxValidatorPredefined")
	public void setValidator(IndustrikaValidator<Tax> validator) {
		if (validator==null){
			throw new IllegalArgumentException("Validator should not be null");
		}
		this.validator = validator;
	}

	@Override
	@Transactional
	public Tax save(Tax dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException {
		validator.validate(dto);
		Integer id=null;
		try {
			id = (Integer) sessionFactory.getCurrentSession().save(dto);
		} catch (Exception ex) {
			
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_add") + TITLE,ex);
		}
		finally{
			sessionFactory.getCurrentSession().flush();
		}
		dto.setIdTax(id);
		return dto;
	}

	@Override
	@Transactional
	public void update(Tax vo) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		TaxValidatorPredefined myValidator=(TaxValidatorPredefined) validator;
		try {
			myValidator.validateUpdate(vo);
		} catch (IndustrikaValidationException e) {
			throw new IndustrikaPersistenceException(e.getMessage());
		}
		
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
	public void remove(Tax vo) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
			TaxValidatorPredefined myValidator=(TaxValidatorPredefined) validator;
			try {
				myValidator.validateDelete(vo);
			} catch (IndustrikaValidationException e) {
				throw new IndustrikaPersistenceException(e.getMessage());
			}
		
			try {
				sessionFactory.getCurrentSession().delete(vo);
			} catch (Exception ex) {
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_delete")
								+ TITLE, ex);
			}
	}

	@Override
	@Transactional
	public Tax findById(Integer id) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Tax dto = null;
		try{
			dto = (Tax) sessionFactory.getCurrentSession().get(Tax.class, id);
			if (dto == null){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
			}
		}catch(IndustrikaObjectNotFoundException onfe){
			throw onfe;
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE,ex);
		}		
		return dto;
	}

	@Override
	public List<Tax> findAll() throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		return null;
	}

	@Override
	@Transactional
	public List<Tax> find(Tax dto, String[] sortFields)
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tax.class);
		TaxValidatorPredefined myValidator=(TaxValidatorPredefined) validator;
	     try {
			myValidator.validateSearch(dto);
		} catch (IndustrikaValidationException e) {
			throw new IndustrikaObjectNotFoundException(e.getMessage());
		}
		try {
			if (dto != null) {
				if (dto.getIdTax() != null
						&& dto.getIdTax()>0) {
					criteria.add(Restrictions.idEq(dto.getIdTax()));
				}
				if (dto.getTaxValue() != null
						&& dto.getTaxValue()>0) {
					criteria.add(Restrictions.eq("taxValue", dto.getTaxValue()));
				}
				if (dto.getName() != null) {
					if(!dto.getName().isEmpty()){
						criteria.add(Restrictions.like("name","%"+dto.getName()+"%"));
					}
				}
				if (dto.isPercentage() != null) {
					criteria.add(Restrictions.eq("percentage",dto.isPercentage()));
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
		List<Tax> results = Collections.checkedList(criteria.list(),Tax.class);
		
		if (results == null || results.size() <= 0) {
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages
							.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}


	
}
