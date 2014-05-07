package com.industrika.humanresources.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dao.DirectionDao;
import com.industrika.humanresources.dto.Direction;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.DirectionValidator;

@Repository("directionDao")
public class DirectionDaoHibernate extends RootDao implements DirectionDao {
	
	@Autowired
	private DirectionValidator validator;
	
	private static final String TITLE = HRMessages.getMessage("direction.Title");
	
	@Override
	@Transactional
	public Integer add(Direction dto) throws IndustrikaValidationException,
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
	public void update(Direction dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(dto);
		try{
			if (dto.getIdDirection() == null || dto.getIdDirection().intValue() <= 0){
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
	public void remove(Direction dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdDirection() != null && dto.getIdDirection().intValue() > 0){
			remove(dto.getIdDirection());
		} else {
			Direction temp = get(dto);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_delete")+TITLE,ex);
			}
		}
	}

	@Override
	@Transactional
	public void remove(Integer idDirection) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (idDirection != null && idDirection.intValue() > 0){
			Direction temp = (Direction) sessionFactory.getCurrentSession().get(Direction.class, idDirection);
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
	public Direction get(Direction dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdDirection() != null && dto.getIdDirection().intValue() > 0){
			return get(dto.getIdDirection());
		} else {
			List<Direction> results=find(dto,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public Direction get(Integer idDirection) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Direction dto = null;
		try{
			dto = (Direction) sessionFactory.getCurrentSession().get(Direction.class, idDirection);
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
	public List<Direction> find(Direction dto, String[] orderFields) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Direction.class);
		try{
			if (dto != null){
				if (dto.getIdDirection() != null && dto.getIdDirection().intValue() > 0){
					criteria.add(Restrictions.eq("idDirection", dto.getIdDirection().intValue()));
				} else {
					if (dto.getName() != null && !dto.getName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("name", "%"+dto.getName()+"%"));
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
		List<Direction> results = Collections.checkedList(criteria.list(),Direction.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
