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
import com.industrika.administration.dao.BankDao;
import com.industrika.administration.dto.Bank;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Repository("bankDao")
public class BankDaoHibernate extends RootDao implements BankDao {

	private IndustrikaValidator<Bank> validator;

	private static final String TITLE = AdministrationMessages.getMessage("bank");

	@Autowired
	@Qualifier("bankValidatorPredefined")
	public void setValidator(IndustrikaValidator<Bank> validator) {
		// TODO Auto-generated method stub
		if (validator==null){
			throw new IllegalArgumentException("Validator should not be null");
		}
		this.validator = validator;
	}

	@Override
	@Transactional
	public Integer add(Bank dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException {
		
		validator.validate(dto);
		Integer id = null;
		try {
			id = (Integer) sessionFactory.getCurrentSession().save(dto);
		} catch (Exception ex) {
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_add") + TITLE,
					ex);
		}
		return id;
	}

	@Override
	@Transactional
	public void update(Bank dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(dto);
		try {
			if (dto.getIdBank() == null || dto.getIdBank().intValue() <= 0) {
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages
								.getMessage("error_persistence_objectnotfound"));
			} else {
				sessionFactory.getCurrentSession().saveOrUpdate(dto);
			}
		} catch (IndustrikaObjectNotFoundException onfe) {
			throw onfe;
		} catch (Exception ex) {
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_update")
							+ TITLE, ex);
		}
	}

	@Override
	@Transactional
	public void remove(Bank dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdBank() != null && dto.getIdBank().intValue() > 0) {
			remove(dto.getIdBank());
		} else {
			Bank temp = get(dto);
			try {
				sessionFactory.getCurrentSession().delete(temp);
			} catch (Exception ex) {
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_delete")
								+ TITLE, ex);
			}
		}
	}

	@Override
	@Transactional
	public void remove(Integer idBank) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (idBank != null && idBank.intValue() > 0) {
			Bank temp = (Bank) sessionFactory.getCurrentSession().get(
					Bank.class, idBank);
			try {
				sessionFactory.getCurrentSession().delete(temp);
			} catch (Exception ex) {
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_delete")
								+ TITLE, ex);
			}
		} else {
			throw new IndustrikaPersistenceException(
					CommonsMessages
							.getMessage("error_persistence_objectnotfound"));
		}
	}

	@Override
	@Transactional
	public Bank get(Bank dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdBank() != null && dto.getIdBank().intValue() > 0) {
			return get(dto.getIdBank());
		} else {
			List<Bank> results = find(dto, null);
			if (results.size() > 1) {
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages
								.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public Bank get(Integer idBank) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Bank dto = null;
		try {
			dto = (Bank) sessionFactory.getCurrentSession().get(Bank.class,
					idBank);
			if (dto == null) {
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages
								.getMessage("error_persistence_objectnotfound"));
			}
		} catch (IndustrikaObjectNotFoundException onfe) {
			throw onfe;
		} catch (Exception ex) {
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_executequery")
							+ TITLE, ex);
		}
		return dto;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Bank> find(Bank dto, String[] orderFields)
			throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Bank.class);
		try {
			if (dto != null) {
				if (dto.getIdBank() != null
						&& !dto.getIdBank().toString().trim().equalsIgnoreCase("")) {
					criteria.add(Restrictions.idEq(dto.getIdBank()));
				}
				if (dto.getName() != null
						&& !dto.getName().trim().equalsIgnoreCase("")) {
					criteria.add(Restrictions.like("name", "%" + dto.getName()
							+ "%"));
				}
				if (dto.getAcronym() != null
						&& !dto.getAcronym().trim().equalsIgnoreCase("")) {
					criteria.add(Restrictions.like("acronym",
							"%" + dto.getAcronym() + "%"));
				}
			}
			if (orderFields != null && orderFields.length > 0) {
				for (int a = 0; a < orderFields.length; a++) {
					if (orderFields[a] != null
							&& !orderFields[a].trim().equalsIgnoreCase("")) {
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
		} catch (Exception ex) {
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages.getMessage("error_persistence_executequery")
							+ TITLE, ex);
		}
		List<Bank> results = Collections.checkedList(criteria.list(),
				Bank.class);
		if (results == null || results.size() <= 0) {
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages
							.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
