package com.industrika.administration.dao;

import java.util.List;

import com.industrika.administration.dto.Account;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface AccountDao {
	
	public String add(Account dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Account dto, boolean updateAccountAmt) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Account dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(String refNumber) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Account get(Account dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Account get(String refNumber) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Account> findbyLevel(Integer level, String[] orderFields, boolean greaterThanE)  throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Account> find(Account dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
}

