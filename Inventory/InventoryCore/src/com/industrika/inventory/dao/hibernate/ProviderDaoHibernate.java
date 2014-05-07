package com.industrika.inventory.dao.hibernate;

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
import com.industrika.inventory.dao.ProviderDao;
import com.industrika.inventory.dto.Provider;
import com.industrika.inventory.i18n.InventoryMessages;
import com.industrika.inventory.validation.ProviderValidator;

@Repository("providerDao")
public class ProviderDaoHibernate extends RootDao implements ProviderDao {

	private static final String TITLE = InventoryMessages.getMessage("provider.Title");
	private ProviderValidator validator;

	@Autowired
	@Qualifier("providerValidator")
	public void setValidator(ProviderValidator validator) {
		this.validator = validator;
	}
	
	@Transactional
	private void alreadyExist(Provider provider) 
			throws 	IndustrikaAlreadyExistException {
		Provider toFind = new Provider();
		Provider found = new Provider();
		try{
			toFind = new Provider();
			toFind.setRfc(provider.getRfc());
			found = get(toFind);
		}catch(Exception ex){
			if (!(ex instanceof IndustrikaObjectNotFoundException)){
				ex.printStackTrace();
			}
			found = null;
		}		
		if (found != null){
			if (provider.getIdPerson() == null || 
					provider.getIdPerson().intValue() != found.getIdPerson().intValue()){
				found = null;
				throw new IndustrikaAlreadyExistException(
						"Ya existe un cliente con "+InventoryMessages.getMessage("provider.Rfc")+ " igual.");
			}
		}
		toFind = new Provider();
		toFind.setBusinessName(provider.getBusinessName());
		found = new Provider();
		try{
			found = get(toFind);
		}catch(Exception ex){
			if (!(ex instanceof IndustrikaObjectNotFoundException)){
				ex.printStackTrace();
			}
			found = null;
		}		
		if (found != null){
			if (provider.getBusinessName().equalsIgnoreCase(found.getBusinessName())){
				if (provider.getIdPerson() == null || 
						provider.getIdPerson().intValue() != found.getIdPerson().intValue()){
					found = null;
					throw new IndustrikaAlreadyExistException(
							"Ya existe un cliente con "+InventoryMessages.getMessage("provider.BusinessName")+ " igual.");
				}
			}
		}
		found = null;
	}
	
	@Override
	@Transactional
	public Integer add(Provider provider) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException {
		validator.validate(provider);
		Integer id = null;
		alreadyExist(provider);
		try{
			id = (Integer) sessionFactory.getCurrentSession().save(provider);
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_add")+TITLE,
					ex);
		}
		return id;
	}

	@Override
	@Transactional
	public void update(Provider provider) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException, 
					IndustrikaObjectNotFoundException {
		validator.validate(provider);
		alreadyExist(provider);
		try{
			if (provider.getIdPerson() == null || provider.getIdPerson().intValue() <= 0){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages.getMessage("error_persistence_objectnotfound"));
			} else {
				try{
					sessionFactory.getCurrentSession().saveOrUpdate(provider);
				}catch(NonUniqueObjectException nu){
					sessionFactory.getCurrentSession().merge(provider);
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
	public void delete(Provider provider)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (provider.getIdPerson() != null && provider.getIdPerson().intValue() > 0){
			delete(provider.getIdPerson());
		} else {
			Provider temp = get(provider);
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
	public void delete(Integer idProvider)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (idProvider != null && idProvider.intValue() > 0){
			Provider temp = (Provider) sessionFactory.getCurrentSession().get(Provider.class, idProvider);
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
	public Provider get(Provider provider)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (provider.getIdPerson() != null && provider.getIdPerson().intValue() > 0){
			return get(provider.getIdPerson());
		} else {
			List<Provider> results=find(provider,null);
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
	public Provider get(Integer idProvider)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Provider dto = null;
		try{
			dto = (Provider) sessionFactory.getCurrentSession().get(Provider.class, idProvider);
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
	public List<Provider> find(Provider provider, String[] order)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Provider.class);
		try{
			if (provider != null){
				if (provider.getIdPerson() != null && provider.getIdPerson().intValue() > 0){
					criteria.add(Restrictions.eq("idPerson", provider.getIdPerson().intValue()));
				} else {
					if (!StringUtils.isEmpty(provider.getBusinessName())){
						criteria.add(Restrictions.like("businessName", "%"+provider.getBusinessName()+"%"));
					}
					if (!StringUtils.isEmpty(provider.getFirstName())){
						criteria.add(Restrictions.like("firstName", "%"+provider.getFirstName()+"%"));
					}		
					if (!StringUtils.isEmpty(provider.getEmail())){
						criteria.add(Restrictions.like("email", "%"+provider.getEmail()+"%"));
					}		
					if (!StringUtils.isEmpty(provider.getRfc())){
						criteria.add(Restrictions.like("rfc", "%"+provider.getRfc()+"%"));
					}		
					if (!StringUtils.isEmpty(provider.getPurchasesContactName())){
						criteria.add(Restrictions.like("purschasesContactName", "%"+provider.getPurchasesContactName()+"%"));
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
		List<Provider> results = Collections.checkedList(criteria.list(),Provider.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

	@Override
	public void updateBalance(Integer idProvider, Double amount) {
		try{
			Provider provider = get(idProvider);
			provider.setBalance(provider.getBalance().doubleValue()+amount.doubleValue());
			update(provider);
		}catch(Exception ex){
			ex.getMessage();
		}
	}

	@Override
	public void updateAcumulated(Integer idProvider, Double amount) {
		try{
			Provider provider = get(idProvider);
			provider.setAcumulated(provider.getAcumulated().doubleValue()+amount.doubleValue());
			update(provider);
		}catch(Exception ex){
			ex.getMessage();
		}
	}

}
