package com.industrika.administration.dao.hibernate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.administration.dao.AccountDao;
import com.industrika.administration.dto.Account;
import com.industrika.administration.i18n.AdministrationMessages;
import com.industrika.administration.validation.AccountValidator;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;

@Repository("accountDao")
public class AccountDaoHibernate extends RootDao implements AccountDao {

	@Autowired
	private AccountValidator validator;

	private static final String TITLE = AdministrationMessages.getMessage("account.Title");

	@Override
	@Transactional
	public String add(Account dto) throws IndustrikaValidationException,
			IndustrikaPersistenceException {
		validator.validate(dto);
		if(dto.getLevel()>1){
			Account ac = validator.validateParents(dto);
			if(ac!=null){
				try {
					List<Account> list=find(ac,null);
					if(list==null||list.isEmpty()){
						throw new IndustrikaValidationException(
								AdministrationMessages.getMessage("account.create.warning"));
					}
					else{
						try {
							Account ex= new Account();
							ex.setRefNumber(dto.getRefNumber());
							List<Account> exist=find(ex,null);
							if(exist==null||exist.isEmpty()){
								sessionFactory.getCurrentSession().saveOrUpdate(dto);
								/*if(dto.getLevel()==3){
									updateParents(dto);
								}*/
							}
							else{
								throw new IndustrikaValidationException(
										AdministrationMessages.getMessage("account.create.warning2"));
							}
							
							/*Update parents*/
						} catch (Exception ex) {
							throw new IndustrikaPersistenceException(
									CommonsMessages.getMessage("error_persistence_add") + TITLE,
									ex);
						}
					}
				} catch (IndustrikaObjectNotFoundException e) {
					throw new IndustrikaPersistenceException(
							CommonsMessages.getMessage("error_persistence_add") + TITLE,
							e);
				}
			}
		}
		else{
			Account ex= new Account();
			ex.setRefNumber(dto.getRefNumber());
			List<Account> exist;
			try {
				exist = find(ex,null);
				if(exist==null||exist.isEmpty()){
					sessionFactory.getCurrentSession().saveOrUpdate(dto);
				}
				else{
					throw new IndustrikaValidationException(AdministrationMessages.getMessage("account.create.warning2"));
				}
				
			} catch (IndustrikaObjectNotFoundException excp) {
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_add") + TITLE,
						excp);
			   }
			}
		
		
		return dto.getRefNumber();
	}

	@Override
	@Transactional
	public void update(Account dto, boolean updateAccountAmt) throws IndustrikaValidationException,
			IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		try {
			if (dto.getRefNumber() == null || dto.getRefNumber().isEmpty() ){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages
								.getMessage("error_persistence_objectnotfound"));
			} else {
				/*search for the object*/
				Account ac= new Account();
				ac.setRefNumber(dto.getRefNumber());
				List<Account> list=find(ac,null);
				
				if (list != null && list.size() == 1){
					ac = list.get(0);
					if(!updateAccountAmt){
						ac.setAccountName(dto.getAccountName());
						sessionFactory.getCurrentSession().saveOrUpdate(ac);
					}
					else{
						double newBalance = ac.getBalance()+dto.getBalance();
						ac.setBalance(newBalance);
						sessionFactory.getCurrentSession().saveOrUpdate(ac);
						List<Account> parents = getParentKeys(ac);
						for(Account pa: parents){
							List<Account> accounts=find(pa,null);
							if (accounts != null && accounts.size() == 1){
								Account myRealParent = accounts.get(0);
								double newBa=myRealParent.getBalance()+dto.getBalance();
								myRealParent.setBalance(newBa);
								sessionFactory.getCurrentSession().saveOrUpdate(myRealParent);
							}
						}
					}
				}
			}
		} catch (IndustrikaObjectNotFoundException onfe) {
			throw onfe;
		} catch (Exception ex) {
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_update")
							+ TITLE, ex);
		}
	}

	@Override
	@Transactional
	public void remove(Account dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		/*if (dto.getIdBank() != null && dto.getIdBank().intValue() > 0) {
			remove(dto.getIdBank());
		} else {
			Bank temp = get(dto);
			try {
				sessionFactory.getCurrentSession().delete(temp);
			} catch (Exception ex) {
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_delete")
								+ TITLE, ex);
			}
		}*/
	}

	@Override
	@Transactional
	public void remove(String refNumber) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		/*if (idBank != null && idBank.intValue() > 0) {
			Bank temp = (Bank) sessionFactory.getCurrentSession().get(
					Bank.class, idBank);
			try {
				sessionFactory.getCurrentSession().delete(temp);
			} catch (Exception ex) {
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_delete")
								+ TITLE, ex);
			}
		} else {
			throw new IndustrikaPersistenceException(
					CommonsMessages
							.getMessage("error_persistence_objectnotfound"));
		}*/
	}

	@Override
	@Transactional
	public Account get(Account dto) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		/*if (dto.getIdBank() != null && dto.getIdBank().intValue() > 0) {
			return get(dto.getIdBank());
		} else {
			List<Bank> results = find(dto, null);
			if (results.size() > 1) {
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages
								.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}*/
		return null;
	}

	@Override
	@Transactional
	public Account get(String refNumber) throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		/*Bank dto = null;
		try {
			dto = (Bank) sessionFactory.getCurrentSession().get(Bank.class,
					idBank);
			if (dto == null) {
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages
								.getMessage("error_persistence_objectnotfound"));
			}
		} catch (IndustrikaObjectNotFoundException onfe) {
			throw onfe;
		} catch (Exception ex) {
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_executequery")
							+ TITLE, ex);
		}
		return dto;*/
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Account> find(Account dto, String[] orderFields)
			throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Account.class);
		try {
			if (dto != null) {
				if (dto.getRefNumber() != null
						&& !dto.getRefNumber().toString().trim().equalsIgnoreCase("")) {
					criteria.add(Restrictions.idEq(dto.getRefNumber()));
				}
				if (dto.getLevel() != null
						&& (!(dto.getLevel()>3)||!(dto.getLevel()<0))) {
					criteria.add(Restrictions.eq("level", dto.getLevel()));
				}
				if (dto.getAccountName() != null
						&& !dto.getAccountName().trim().equalsIgnoreCase("")) {
					criteria.add(Restrictions.like("accountName",
							"%" + dto.getAccountName() + "%"));
				}
			}
			if (orderFields != null && orderFields.length > 0) {
				for (int a = 0; a < orderFields.length; a++) {
					if (orderFields[a] != null
							&& !orderFields[a].trim().equalsIgnoreCase("")) {
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
		} catch (Exception ex) {
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages.getMessage("error_persistence_executequery")
							+ TITLE, ex);
		}
		List<Account> results = Collections.checkedList(criteria.list(),
				Account.class);
		
		return results;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Account> findbyLevel(Integer level, String[] orderFields, boolean greaterThanE)
			throws IndustrikaPersistenceException,
			IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Account.class);
		try {
			
			if(greaterThanE){
				criteria.add(Restrictions.ge("level", level));
			}
			else{
				criteria.add(Restrictions.le("level", level));
			}
			
			if (orderFields != null && orderFields.length > 0) {
				for (int a = 0; a < orderFields.length; a++) {
					if (orderFields[a] != null
							&& !orderFields[a].trim().equalsIgnoreCase("")) {
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
		} catch (Exception ex) {
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages.getMessage("error_persistence_executequery")
							+ TITLE, ex);
		}
		List<Account> results = Collections.checkedList(criteria.list(),
				Account.class);
		
		return results;
	}
	
	private List<Account> getParentKeys(Account dto){
		int level = dto.getLevel();
		String refParent= dto.getRefNumber();
		List<Account> results =new ArrayList<Account>();
		
		for(int i=1; i<level; i++){
			String val="";
			Account ac= new Account();
			for(int a=1; a<=i; a++){
				val=val +(refParent.split("-"))[a-1];
				int temp=a+1;
				if(temp<=i){
					val=val+"-";
				}
			}
			ac.setRefNumber(val.trim());
			results.add(ac);
		}
		
		return results;
	}
}
