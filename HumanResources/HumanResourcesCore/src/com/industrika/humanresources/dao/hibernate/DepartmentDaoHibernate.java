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
import com.industrika.humanresources.dao.DepartmentDao;
import com.industrika.humanresources.dto.Department;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.DepartmentValidator;

@Repository("departmentDao")
public class DepartmentDaoHibernate extends RootDao implements DepartmentDao {
	
	@Autowired
	private DepartmentValidator validator;
	
	private static final String TITLE = HRMessages.getMessage("department.Title");
	
	@Override
	@Transactional
	public Integer add(Department dto) throws IndustrikaValidationException,
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
	public void update(Department dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(dto);
		try{
			if (dto.getIdDepartment() == null || dto.getIdDepartment().intValue() <= 0){
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
	public void remove(Department dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdDepartment() != null && dto.getIdDepartment().intValue() > 0){
			remove(dto.getIdDepartment());
		} else {
			Department temp = get(dto);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_delete")+TITLE,ex);
			}
		}
	}

	@Override
	@Transactional
	public void remove(Integer idDepartment) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (idDepartment != null && idDepartment.intValue() > 0){
			Department temp = (Department) sessionFactory.getCurrentSession().get(Department.class, idDepartment);
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
	public Department get(Department dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdDepartment() != null && dto.getIdDepartment().intValue() > 0){
			return get(dto.getIdDepartment());
		} else {
			List<Department> results=find(dto,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public Department get(Integer idDepartment) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Department dto = null;
		try{
			dto = (Department) sessionFactory.getCurrentSession().get(Department.class, idDepartment);
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
	public List<Department> find(Department dto, String[] orderFields) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Department.class);
		try{
			if (dto != null){
				if (dto.getIdDepartment() != null && dto.getIdDepartment().intValue() > 0){
					criteria.add(Restrictions.eq("idDepartment", dto.getIdDepartment().intValue()));
				} else {
					if (dto.getName() != null && !dto.getName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("name", "%"+dto.getName()+"%"));
					}
					if (dto.getManagement() != null && dto.getManagement().getIdManagement() != null && dto.getManagement().getIdManagement().intValue() > 0){
						criteria.add(Restrictions.eq("management.idManagement", dto.getManagement().getIdManagement()));
					}
				}
			}
			if (orderFields != null && orderFields.length > 0){
				for (int a=0;a<orderFields.length;a++){
					if (orderFields[a] != null && !orderFields[a].trim().equalsIgnoreCase("")){
						if (orderFields[a].indexOf("management") > -1){
							criteria.createAlias("management", "management");
						}
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<Department> results = Collections.checkedList(criteria.list(),Department.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
