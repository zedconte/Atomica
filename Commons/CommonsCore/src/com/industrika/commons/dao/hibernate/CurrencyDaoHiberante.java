package com.industrika.commons.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.CurrencyDao;
import com.industrika.commons.dto.Currency;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.CurrencyValidator;

@Repository("currencyDao")
public class CurrencyDaoHiberante extends RootDao implements CurrencyDao {
	
	@Autowired
	private CurrencyValidator validator;
	
	private static final String TITLE = CommonsMessages.getMessage("currency.Title");
	
	@Override
	@Transactional
	public Integer add(Currency dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException {
		validator.validate(dto);
		Integer id = null;
		try{
			id = (Integer) sessionFactory.getCurrentSession().save(dto);
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_add")+TITLE,ex);
		}
		return id;
	}

	@Override
	@Transactional
	public void update(Currency dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(dto);
		try{
			if (dto.getIdCurrency() == null || dto.getIdCurrency().intValue() <= 0){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
			} else {
				sessionFactory.getCurrentSession().saveOrUpdate(dto);
			}
		}catch(IndustrikaObjectNotFoundException onfe){
			throw onfe;
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_update")+TITLE,ex);
		}
	}

	@Override
	@Transactional
	public void remove(Currency dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdCurrency() != null && dto.getIdCurrency().intValue() > 0){
			remove(dto.getIdCurrency());
		} else {
			Currency temp = get(dto);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_delete")+TITLE,ex);
			}
		}
	}

	@Override
	@Transactional
	public void remove(Integer idCurrency) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (idCurrency != null && idCurrency.intValue() > 0){
			Currency temp = (Currency) sessionFactory.getCurrentSession().get(Currency.class, idCurrency);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_delete")+TITLE,ex);
			}
		} else {
			throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
	}

	@Override
	@Transactional
	public Currency get(Currency dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdCurrency() != null && dto.getIdCurrency().intValue() > 0){
			return get(dto.getIdCurrency());
		} else {
			List<Currency> results=find(dto,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public Currency get(Integer idCurrency) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Currency dto = null;
		try{
			dto = (Currency) sessionFactory.getCurrentSession().get(Currency.class, idCurrency);
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
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Currency> find(Currency dto, String[] orderFields) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Currency.class);
		try{
			if (dto != null){
				if (dto.getIdCurrency() != null && dto.getIdCurrency().intValue() > 0){
					criteria.add(Restrictions.eq("idCurrency", dto.getIdCurrency()));
				} else {
					if (dto.getName() != null && !dto.getName().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("name", "%"+dto.getName()+"%"));
					}
					if (dto.getShortName() != null && !dto.getShortName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("shortName", "%"+dto.getShortName()+"%"));
					}
					if (dto.getSymbol() != null && !dto.getSymbol().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("symbol", "%"+dto.getSymbol()+"%"));
					}
				}
			}
			if (orderFields != null && orderFields.length > 0){
				for (int a=0;a<orderFields.length;a++){
					if (orderFields[a] != null && !orderFields[a].trim().equalsIgnoreCase("")){
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<Currency> results = Collections.checkedList(criteria.list(),Currency.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
