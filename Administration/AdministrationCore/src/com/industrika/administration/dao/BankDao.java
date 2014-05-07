package com.industrika.administration.dao;

import java.util.List;

import com.industrika.administration.dto.Bank;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface BankDao {
	
	public Integer add(Bank dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Bank dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Bank dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idPerson) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Bank get(Bank dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Bank get(Integer idBank) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Bank> find(Bank dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
}

