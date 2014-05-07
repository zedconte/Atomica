package com.industrika.commons.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.CountryDao;
import com.industrika.commons.dto.Country;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.CountryValidator;

@Repository("countryDao")
public class CountryDaoHibernate extends RootDao implements CountryDao {
	
	@Autowired
	private CountryValidator validator;
	
	private static final String TITLE = CommonsMessages.getMessage("country.Title");
	
	@Override
	@Transactional
	public Integer add(Country dto) throws IndustrikaValidationException,
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
	public void update(Country dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(dto);
		try{
			if (dto.getIdCountry() == null || dto.getIdCountry().intValue() <= 0){
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
	public void remove(Country dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdCountry() != null && dto.getIdCountry().intValue() > 0){
			remove(dto.getIdCountry());
		} else {
			Country temp = get(dto);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_delete")+TITLE,ex);
			}
		}
	}

	@Override
	@Transactional
	public void remove(Integer idCountry) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (idCountry != null && idCountry.intValue() > 0){
			Country temp = (Country) sessionFactory.getCurrentSession().get(Country.class, idCountry);
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
	public Country get(Country dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdCountry() != null && dto.getIdCountry().intValue() > 0){
			return get(dto.getIdCountry());
		} else {
			List<Country> results=find(dto,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public Country get(Integer idCountry) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Country dto = null;
		try{
			dto = (Country) sessionFactory.getCurrentSession().get(Country.class, idCountry);
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
	public List<Country> find(Country dto, String[] orderFields) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Country.class);
		try{
			if (dto != null){
				if (dto.getIdCountry() != null && dto.getIdCountry().intValue() > 0){
					criteria.add(Restrictions.eq("idCountry", dto.getIdCountry().intValue()));
				} else {
					if (dto.getName() != null && !dto.getName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("name", "%"+dto.getName()+"%"));
					}
					if (dto.getShortName() != null && !dto.getShortName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("shortName", "%"+dto.getShortName()+"%"));
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
		List<Country> results = Collections.checkedList(criteria.list(),Country.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
