package com.industrika.commons.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.StateDao;
import com.industrika.commons.dto.State;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.StateValidator;

@Repository("stateDao")
public class StateDaoHibernate extends RootDao implements StateDao {
	
	@Autowired
	private StateValidator validator;
	
	private static final String TITLE = CommonsMessages.getMessage("state.Title");
	
	@Override
	@Transactional
	public Integer add(State dto) throws IndustrikaValidationException,
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
	public void update(State dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(dto);
		try{
			if (dto.getIdState() == null || dto.getIdState().intValue() <= 0){
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
	public void remove(State dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdState() != null && dto.getIdState().intValue() > 0){
			remove(dto.getIdState());
		} else {
			State temp = get(dto);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_delete")+TITLE,ex);
			}
		}
	}

	@Override
	@Transactional
	public void remove(Integer idState) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (idState != null && idState.intValue() > 0){
			State temp = (State) sessionFactory.getCurrentSession().get(State.class, idState);
			try{
				temp.getCountry().getStates().remove(temp);				
				sessionFactory.getCurrentSession().update(temp.getCountry());
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
	public State get(State dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdState() != null && dto.getIdState().intValue() > 0){
			return get(dto.getIdState());
		} else {
			List<State> results=find(dto,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public State get(Integer idState) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		State dto = null;
		try{
			dto = (State) sessionFactory.getCurrentSession().get(State.class, idState);
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
	public List<State> find(State dto, String[] orderFields) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(State.class);
		try{
			if (dto != null){
				if (dto.getIdState() != null && dto.getIdState().intValue() > 0){
					criteria.add(Restrictions.eq("idState", dto.getIdState().intValue()));
				} else {
					if (dto.getName() != null && !dto.getName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("name", "%"+dto.getName()+"%"));
					}
					if (dto.getShortName() != null && !dto.getShortName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("shortName", "%"+dto.getShortName()+"%"));
					}		
					if (dto.getCountry() != null && dto.getCountry().getIdCountry() != null && dto.getCountry().getIdCountry().intValue() > 0){
						criteria.add(Restrictions.eq("country.idCountry", dto.getCountry().getIdCountry()));
					}
				}
			}
			if (orderFields != null && orderFields.length > 0){
				for (int a=0;a<orderFields.length;a++){
					if (orderFields[a] != null && !orderFields[a].trim().equalsIgnoreCase("")){
						if (orderFields[a].indexOf("country") > -1){
							criteria.createAlias("country", "country");
						}
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<State> results = Collections.checkedList(criteria.list(),State.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
