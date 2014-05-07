package com.industrika.maintenance.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.industrika.commons.dao.hibernate.JpaDao;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.validation.IndustrikaValidator;
import com.industrika.maintenance.dto.BaseDto;

public abstract class BaseDao<T> extends JpaDao<Integer, T> {
	
	@Override
	@Transactional
	public List<T> find(T dto, String[] sortFields) throws IndustrikaObjectNotFoundException,	IndustrikaPersistenceException {
		if (dto!=null){
			
			Integer id = ((BaseDto) dto).getId();
			if (id!=null && id>0)
			{
				return getById(id);
			}
			
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
			setFilters(dto, criteria);
			addOrderByFields(criteria, sortFields);
			return executeCriteria(criteria);
		}
		throw new IllegalArgumentException("dto is null");
	}
	
	@SuppressWarnings("unchecked")
	private List<T> executeCriteria(Criteria criteria)
			throws IndustrikaPersistenceException {
		try
		{
			return new ArrayList<T>(criteria.list());
		}
		catch(HibernateException ex){
			throw new IndustrikaPersistenceException(ex);
		}
	}
	
	private List<T> getById(Integer id)	throws IndustrikaPersistenceException,	IndustrikaObjectNotFoundException {
		T result = findById(id);
		if (result!=null)
		{
			return new ArrayList<T>(Arrays.asList(result));
		}
		return new ArrayList<T>();
	}
	
	protected void addLikeCriterion(Criteria criteria, String name, String value) {
		if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(value))
		{
			criteria.add(Restrictions.like(name, "%" + value + "%"));
		}
	}
	
	protected abstract void setFilters(T dto, Criteria criteria); 
	public abstract void setValidator(IndustrikaValidator<T> validator); 
	
}
