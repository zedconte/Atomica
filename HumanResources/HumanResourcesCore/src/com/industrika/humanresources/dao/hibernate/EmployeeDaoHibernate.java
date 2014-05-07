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
import com.industrika.humanresources.dao.EmployeeDao;
import com.industrika.humanresources.dto.Employee;
import com.industrika.humanresources.i18n.HRMessages;
import com.industrika.humanresources.validation.EmployeeValidator;

@Repository("employeeDao")
public class EmployeeDaoHibernate extends RootDao implements EmployeeDao {
	
	@Autowired
	private EmployeeValidator validator;
	
	private static final String TITLE = HRMessages.getMessage("employee.Title");
	
	@Override
	@Transactional
	public Integer add(Employee dto) throws IndustrikaValidationException,
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
	public void update(Employee dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		validator.validate(dto);
		try{
			if (dto.getIdPerson() == null || dto.getIdPerson().intValue() <= 0){
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
	public void remove(Employee dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdPerson() != null && dto.getIdPerson().intValue() > 0){
			remove(dto.getIdPerson());
		} else {
			Employee temp = get(dto);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(CommonsMessages.getMessage("error_persistence_delete")+TITLE,ex);
			}
		}
	}

	@Override
	@Transactional
	public void remove(Integer idPerson) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (idPerson != null && idPerson.intValue() > 0){
			Employee temp = (Employee) sessionFactory.getCurrentSession().get(Employee.class, idPerson);
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
	public Employee get(Employee dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdPerson() != null && dto.getIdPerson().intValue() > 0){
			return get(dto.getIdPerson());
		} else {
			List<Employee> results=find(dto,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public Employee get(Integer idPerson) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Employee dto = null;
		try{
			dto = (Employee) sessionFactory.getCurrentSession().get(Employee.class, idPerson);
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
	public List<Employee> find(Employee dto, String[] orderFields) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
		try{
			if (dto != null){
				if (dto.getIdPerson() != null && dto.getIdPerson().intValue() > 0){
					criteria.add(Restrictions.eq("idPerson", dto.getIdPerson().intValue()));
				} else {
					if (dto.getFirstName() != null && !dto.getFirstName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("firstName", "%"+dto.getFirstName()+"%"));
					}
					if (dto.getLastName() != null && !dto.getLastName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("lastName", "%"+dto.getLastName()+"%"));
					}
					if (dto.getMiddleName() != null && !dto.getMiddleName().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("middleName", "%"+dto.getMiddleName()+"%"));
					}
					if (dto.getGender() != null && !dto.getGender().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("gender", "%"+dto.getGender()+"%"));
					}
					if (dto.getRfc() != null && !dto.getRfc().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("rfc", "%"+dto.getRfc()+"%"));
					}
					if (dto.getNss() != null && !dto.getNss().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("nss", "%"+dto.getNss()+"%"));
					}
					if (dto.getSalary() != null && dto.getSalary().doubleValue() <= 0){
						criteria.add(Restrictions.eq("salary", dto.getSalary()));
					}
					if (dto.getDepartment() != null && dto.getDepartment().getIdDepartment() != null && dto.getDepartment().getIdDepartment().intValue() > 0){
						criteria.add(Restrictions.eq("department.idDepartment", dto.getDepartment().getIdDepartment()));
					}
					if (dto.getPosition() != null && dto.getPosition().getIdPosition() != null && dto.getPosition().getIdPosition().intValue() > 0){
						criteria.add(Restrictions.eq("position.idPosition", dto.getPosition().getIdPosition()));
					}
					if (dto.getShift() != null && dto.getShift().getIdShift() != null && dto.getShift().getIdShift().intValue() > 0){
						criteria.add(Restrictions.eq("shift.idShift", dto.getShift().getIdShift()));
					}
				}
			}
			if (orderFields != null && orderFields.length > 0){
				for (int a=0;a<orderFields.length;a++){
					if (orderFields[a] != null && !orderFields[a].trim().equalsIgnoreCase("")){
						if (orderFields[a].indexOf("department") > -1){
							criteria.createAlias("department", "department");
						}
						if (orderFields[a].indexOf("position") > -1){
							criteria.createAlias("position", "position");
						}
						if (orderFields[a].indexOf("shift") > -1){
							criteria.createAlias("shift", "shift");
						}
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<Employee> results = Collections.checkedList(criteria.list(),Employee.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
