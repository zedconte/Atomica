package com.industrika.commons.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.PersonDao;
import com.industrika.commons.dto.Person;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.PersonValidator;

@Repository("personDao")
public class PersonDaoHibernate extends RootDao implements PersonDao {
	
	@Autowired
	@Qualifier("personValidator")
	private PersonValidator validator;
	
	private static final String TITLE = CommonsMessages.getMessage("person");
	
	@Override
	@Transactional
	public Integer add(Person dto) throws IndustrikaValidationException,
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
	public void update(Person dto) throws IndustrikaValidationException,
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
	public void remove(Person dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdPerson() != null && dto.getIdPerson().intValue() > 0){
			remove(dto.getIdPerson());
		} else {
			Person temp = get(dto);
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
			Person temp = (Person) sessionFactory.getCurrentSession().get(Person.class, idPerson);
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
	public Person get(Person dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		if (dto.getIdPerson() != null && dto.getIdPerson().intValue() > 0){
			return get(dto.getIdPerson());
		} else {
			List<Person> results=find(dto,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public Person get(Integer idPerson) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Person dto = null;
		try{
			dto = (Person) sessionFactory.getCurrentSession().get(Person.class, idPerson);
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
	public List<Person> find(Person dto, String[] orderFields) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class);
		try{
			if (dto != null){
				if (dto.getIdPerson() != null && dto.getIdPerson().intValue() > 0){
					criteria.add(Restrictions.eq("idPerson", dto.getIdPerson()));
				} else {
					if (dto.getBirthday() != null){
						criteria.add(Restrictions.eq("birthday", dto.getBirthday()));
					}
					if (dto.getEmail() != null && !dto.getEmail().trim().equalsIgnoreCase("")){
						criteria.add(Restrictions.like("email", "%"+dto.getEmail()+"%"));
					}
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
						criteria.add(Restrictions.eq("gender", dto.getGender()));
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
		List<Person> results = Collections.checkedList(criteria.list(),Person.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
