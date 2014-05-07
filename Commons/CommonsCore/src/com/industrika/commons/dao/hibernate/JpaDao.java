package com.industrika.commons.dao.hibernate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dto.Role;
import com.industrika.commons.dto.User;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

public abstract class JpaDao<K, E> implements GenericDao<K, E> {

	@SuppressWarnings("rawtypes")
	protected Class entityClass;
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	protected IndustrikaValidator<E> validator;
	
	@SuppressWarnings("rawtypes")
	public JpaDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] inheritanceTree = genericSuperclass.getActualTypeArguments();
		this.entityClass = (Class) inheritanceTree[inheritanceTree.length-1];
	}
	
	@Override
	@Transactional
	public E save(E vo) throws IndustrikaValidationException,
			IndustrikaPersistenceException {
		if(validator != null)
			validator.validate(vo);
		sessionFactory.getCurrentSession().persist(vo);
		return vo;
	}

	@Override
	@Transactional
	public void update(E vo) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		if(validator != null)
			validator.validate(vo);
		sessionFactory.getCurrentSession().update(vo);
	}

	@Override
	@Transactional
	public void remove(E vo) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		try{
			if (!(vo instanceof User)){
				if (!(vo instanceof Role)){
					sessionFactory.getCurrentSession().delete(vo);
				} else if (((Role)vo).getId().intValue() > 1){
					sessionFactory.getCurrentSession().delete(vo);
				}		
			} else if (((User)vo).getId().intValue() > 1){
				sessionFactory.getCurrentSession().delete(vo);
			}
		}catch(DataIntegrityViolationException e){
			throw new IndustrikaPersistenceException("No puede ser eliminado pues existe informaci&oacute;n que depende de &eacute;sta",e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public E findById(K id) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		try{
			return (E)sessionFactory.getCurrentSession().get(entityClass, 
				(java.io.Serializable)id);
		}
		catch(Exception e){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+
					entityClass.getClass(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<E> findAll() throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		try{
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			return criteria.list();
		}
		catch(Exception e){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+
					entityClass.getClass(), e);
		}
	}

	protected void addOrderByFields(Criteria criteriaQuery, String[] orderFields){
		if (orderFields != null && orderFields.length > 0){
			for (int i=0; i < orderFields.length; i++){
				if (orderFields[i] != null && !orderFields[i].trim().equals("")){
					criteriaQuery.addOrder(Order.asc(orderFields[i].trim()));
				}
			}
		}
	}
}
