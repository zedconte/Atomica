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
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;
import com.industrika.administration.dao.BankOperationDao;
import com.industrika.administration.dto.BankOperation;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.administration.validation.predefined.BankOperationValidatorPredefined;

@Repository("bankOperationDaoHibernate")
public class BankOperationDaoHibernate extends RootDao implements BankOperationDao{

	private IndustrikaValidator<BankOperation> validator;
	private static final String TITLE = AdministrationMessages.getMessage("bank.operation.Title");
	
	@Autowired
	@Qualifier("bankOperationValidatorPredefined")
	public void setValidator(IndustrikaValidator<BankOperation> validator) {
		if (validator==null){
			throw new IllegalArgumentException("Validator should not be null");
		}
		this.validator = validator;
	}

	@Override
	@Transactional
	public BankOperation save(BankOperation dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException {
		validator.validate(dto);
		Integer id = null;
		try {
			id = (Integer)sessionFactory.getCurrentSession().save(dto);
			dto.setIdOperation(id);
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
	@Transactional
	public void update(BankOperation vo) throws IndustrikaValidationException,
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
	public void remove(BankOperation vo) throws IndustrikaPersistenceException,
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
	@Transactional
	public BankOperation findById(Integer id) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BankOperation.class);
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
		List<BankOperation> results = Collections.checkedList(criteria.list(),BankOperation.class);
		
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
	public List<BankOperation> findAll() throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		return null;
	}

	@Override
	@Transactional
	public List<BankOperation> find(BankOperation dto, String[] sortFields)
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BankOperation.class);
		try {
			BankOperationValidatorPredefined myValidator=(BankOperationValidatorPredefined) validator;
			myValidator.validateSearch(dto);
			if (dto != null) {
				if (dto.getIdOperation() != null
						&& dto.getIdOperation()>0) {
					criteria.add(Restrictions.idEq(dto.getIdOperation()));
				}
				/*if (dto.getBankId() != null
						&& dto.getBankId()>0) {
					criteria.add(Restrictions.eq("bank", dto.getBankId()));
				}*/
				if (dto.getAmount() != null) {
					criteria.add(Restrictions.eq("amount",dto.getAmount()));
				}
				
				/*if (dto.getOperationType() != null && !dto.getOperationType().trim().isEmpty()) {
					criteria.add(Restrictions.eq("operationType",dto.getOperationType()));
				}*/
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
		List<BankOperation> results = Collections.checkedList(criteria.list(),BankOperation.class);
		
		if (results == null || results.size() <= 0) {
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages
							.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
