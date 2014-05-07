package com.industrika.administration.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.administration.dao.DeductionDao;
import com.industrika.administration.dto.Deduction;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.administration.validation.DeductionValidator;
import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;

@Repository("deductionDao")
public class DeductionDaoHibernate extends RootDao implements DeductionDao {
	@Autowired
	private DeductionValidator validator;
	private static final String TITLE = AdministrationMessages.getMessage("deduction.Title");

	@Override
	@Transactional
	public Integer add(Deduction dto) throws IndustrikaValidationException,
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
	public void update(Deduction dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(dto);
		try {
			if (dto.getIdDeduction() == null || dto.getIdDeduction().intValue() <= 0) {
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
	public void remove(Deduction dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdDeduction() != null && dto.getIdDeduction().intValue() > 0) {
			remove(dto.getIdDeduction());
		} else {
			Deduction temp = get(dto);
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
	public void remove(Integer idDeduction) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (idDeduction != null && idDeduction.intValue() > 0) {
			Deduction temp = (Deduction) sessionFactory.getCurrentSession().get(
					Deduction.class, idDeduction);
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
	public Deduction get(Deduction dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdDeduction() != null && dto.getIdDeduction().intValue() > 0) {
			return get(dto.getIdDeduction());
		} else {
			List<Deduction> results = find(dto, null);
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
	public Deduction get(Integer idDeduction) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Deduction dto = null;
		try {
			dto = (Deduction) sessionFactory.getCurrentSession().get(Deduction.class,
					idDeduction);
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
	public List<Deduction> find(Deduction dto, String[] orderFields)
			throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Deduction.class);
		try {
			if (dto != null) {
				if (dto.getIdDeduction() != null && dto.getIdDeduction().intValue() > 0) {
					criteria.add(Restrictions.idEq(dto.getIdDeduction()));
				} else {
					if (dto.getName() != null && !dto.getName().trim().equalsIgnoreCase("")) {
						criteria.add(Restrictions.like("name", "%" + dto.getName() + "%"));
					}
					if (dto.getInitials() != null && !dto.getInitials().trim().equalsIgnoreCase("")) {
						criteria.add(Restrictions.like("initials", "%" + dto.getInitials() + "%"));
					}
					if (dto.getValue() != null	&& dto.getValue().doubleValue() > 0) {
						criteria.add(Restrictions.eq("value",dto.getValue()));
					}
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
		List<Deduction> results = Collections.checkedList(criteria.list(),
				Deduction.class);
		if (results == null || results.size() <= 0) {
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages
							.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
