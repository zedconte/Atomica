package com.industrika.sales.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.sales.dao.CustomerDao;
import com.industrika.sales.dto.Customer;
import com.industrika.sales.i18n.SalesMessages;
import com.industrika.sales.validation.CustomerValidator;

@Repository("customerDao")
public class CustomerDaoHibernate extends RootDao implements CustomerDao {

	private static final String TITLE = SalesMessages.getMessage("customer.Title");
	private CustomerValidator validator;

	@Autowired
	@Qualifier("customerValidator")
	public void setValidator(CustomerValidator validator) {
		this.validator = validator;
	}
	
	@Transactional
	private void alreadyExist(Customer customer) 
			throws 	IndustrikaAlreadyExistException {
		Customer toFind = new Customer();
		Customer found = new Customer();
		try{
			toFind = new Customer();
			toFind.setRfc(customer.getRfc());
			found = get(toFind);
		}catch(Exception ex){
			if (!(ex instanceof IndustrikaObjectNotFoundException)){
				ex.printStackTrace();
			}
			found = null;
		}		
		if (found != null){
			if (customer.getIdPerson() == null || 
					customer.getIdPerson().intValue() != found.getIdPerson().intValue()){
				found = null;
				throw new IndustrikaAlreadyExistException(
						"Ya existe un cliente con "+SalesMessages.getMessage("customer.Rfc")+ " igual.");
			}
		}
		toFind = new Customer();
		toFind.setBusinessName(customer.getBusinessName());
		found = new Customer();
		try{
			found = get(toFind);
		}catch(Exception ex){
			if (!(ex instanceof IndustrikaObjectNotFoundException)){
				ex.printStackTrace();
			}
			found = null;
		}		
		if (found != null){
			if (customer.getBusinessName().equalsIgnoreCase(found.getBusinessName())){
				if (customer.getIdPerson() == null || 
						customer.getIdPerson().intValue() != found.getIdPerson().intValue()){
					found = null;
					throw new IndustrikaAlreadyExistException(
							"Ya existe un cliente con "+SalesMessages.getMessage("customer.BusinessName")+ " igual.");
				}
			}
		}
		found = null;
	}
	
	@Override
	@Transactional
	public Integer add(Customer customer) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException {
		validator.validate(customer);
		Integer id = null;
		alreadyExist(customer);
		try{
			id = (Integer) sessionFactory.getCurrentSession().save(customer);
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_add")+TITLE,
					ex);
		}
		return id;
	}

	@Override
	@Transactional
	public void update(Customer customer) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException, 
					IndustrikaObjectNotFoundException {
		validator.validate(customer);
		alreadyExist(customer);
		try{
			if (customer.getIdPerson() == null || customer.getIdPerson().intValue() <= 0){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages.getMessage("error_persistence_objectnotfound"));
			} else {
				try{
					sessionFactory.getCurrentSession().saveOrUpdate(customer);
				}catch(NonUniqueObjectException nu){
					sessionFactory.getCurrentSession().merge(customer);
				}
			}
		}catch(IndustrikaObjectNotFoundException onfe){
			throw onfe;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_update")+TITLE,
					ex);
		}

	}

	@Override
	@Transactional
	public void delete(Customer customer)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (customer.getIdPerson() != null && customer.getIdPerson().intValue() > 0){
			delete(customer.getIdPerson());
		} else {
			Customer temp = get(customer);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_delete")+TITLE,
						ex);
			}
		}
	}

	@Override
	@Transactional
	public void delete(Integer idCustomer)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (idCustomer != null && idCustomer.intValue() > 0){
			Customer temp = (Customer) sessionFactory.getCurrentSession().get(Customer.class, idCustomer);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_delete")+TITLE,
						ex);
			}
		} else {
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
	}

	@Override
	@Transactional
	public Customer get(Customer customer)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (customer.getIdPerson() != null && customer.getIdPerson().intValue() > 0){
			return get(customer.getIdPerson());
		} else {
			List<Customer> results=find(customer,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public Customer get(Integer idCustomer)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Customer dto = null;
		try{
			dto = (Customer) sessionFactory.getCurrentSession().get(Customer.class, idCustomer);
			if (dto == null){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages.getMessage("error_persistence_objectnotfound"));
			}
		}catch(IndustrikaObjectNotFoundException onfe){
			throw onfe;
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_executequery")+TITLE,
					ex);
		}		
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Customer> find(Customer customer, String[] order)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
		try{
			if (customer != null){
				if (customer.getIdPerson() != null && customer.getIdPerson().intValue() > 0){
					criteria.add(Restrictions.eq("idPerson", customer.getIdPerson().intValue()));
				} else {
					if (!StringUtils.isEmpty(customer.getBusinessName())){
						criteria.add(Restrictions.like("businessName", "%"+customer.getBusinessName()+"%"));
					}
					if (!StringUtils.isEmpty(customer.getFirstName())){
						criteria.add(Restrictions.like("firstName", "%"+customer.getFirstName()+"%"));
					}		
					if (!StringUtils.isEmpty(customer.getEmail())){
						criteria.add(Restrictions.like("email", "%"+customer.getEmail()+"%"));
					}		
					if (!StringUtils.isEmpty(customer.getRfc())){
						criteria.add(Restrictions.like("rfc", "%"+customer.getRfc()+"%"));
					}		
					if (!StringUtils.isEmpty(customer.getSalesContactName())){
						criteria.add(Restrictions.like("salesContactName", "%"+customer.getSalesContactName()+"%"));
					}		
				}
			}
			if (order != null && order.length > 0){
				for (int a=0;a<order.length;a++){
					if (order[a] != null && !order[a].trim().equalsIgnoreCase("")){
						criteria.addOrder(Order.asc(order[a]));
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<Customer> results = Collections.checkedList(criteria.list(),Customer.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

	@Override
	public void updateBalance(Integer idCustomer, Double amount) {
		try{
			Customer customer = get(idCustomer);
			customer.setBalance(customer.getBalance().doubleValue()+amount.doubleValue());
			update(customer);
		}catch(Exception ex){
			ex.getMessage();
		}
	}

	@Override
	public void updateAcumulated(Integer idCustomer, Double amount) {
		try{
			Customer customer = get(idCustomer);
			customer.setAcumulated(customer.getAcumulated().doubleValue()+amount.doubleValue());
			update(customer);
		}catch(Exception ex){
			ex.getMessage();
		}
	}

}
