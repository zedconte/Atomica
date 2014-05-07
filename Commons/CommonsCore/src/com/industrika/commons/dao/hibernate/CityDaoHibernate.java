package com.industrika.commons.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.CityDao;
import com.industrika.commons.dto.City;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.CityValidator;

@Repository("cityDao")
public class CityDaoHibernate extends RootDao implements CityDao {
	
	@Autowired
	private CityValidator validator;
	
	private static final String TITLE = CommonsMessages.getMessage("city.Title");
	
	@Override
	@Transactional
	public Integer add(City dto) throws IndustrikaValidationException,
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
	public void update(City dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(dto);
		try{
			if (dto.getIdCity() == null || dto.getIdCity().intValue() <= 0){
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
	public void remove(City dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdCity() != null && dto.getIdCity().intValue() > 0){
			remove(dto.getIdCity());
		} else {
			City temp = get(dto);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_delete")+TITLE,ex);
			}
		}
	}

	@Override
	@Transactional
	public void remove(Integer idCity) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (idCity != null && idCity.intValue() > 0){
			City temp = (City) sessionFactory.getCurrentSession().get(City.class, idCity);
			try{
				temp.getState().getCities().remove(temp);				
				sessionFactory.getCurrentSession().update(temp.getState());
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
	public City get(City dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdCity() != null && dto.getIdCity().intValue() > 0){
			return get(dto.getIdCity());
		} else {
			List<City> results=find(dto,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public City get(Integer idCity) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		City dto = null;
		try{
			dto = (City) sessionFactory.getCurrentSession().get(City.class, idCity);
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
	public List<City> find(City dto, String[] orderFields) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(City.class);
		try{
			if (dto != null){
				if (dto.getIdCity() != null && dto.getIdCity().intValue() > 0){
					criteria.add(Restrictions.eq("idCity", dto.getIdCity().intValue()));
				} else {
					if (dto.getName() != null && !dto.getName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("name", "%"+dto.getName()+"%"));
					}
					if (dto.getShortName() != null && !dto.getShortName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("shortName", "%"+dto.getShortName()+"%"));
					}		
					if (dto.getState() != null && dto.getState().getIdState() != null && dto.getState().getIdState().intValue() > 0){
						criteria.add(Restrictions.eq("state.idState", dto.getState().getIdState()));
					}
				}
			}
			if (orderFields != null && orderFields.length > 0){
				for (int a=0;a<orderFields.length;a++){
					if (orderFields[a] != null && !orderFields[a].trim().equalsIgnoreCase("")){
						if (orderFields[a].indexOf("state") > -1){
							criteria.createAlias("state", "state");
						}
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<City> results = Collections.checkedList(criteria.list(),City.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
