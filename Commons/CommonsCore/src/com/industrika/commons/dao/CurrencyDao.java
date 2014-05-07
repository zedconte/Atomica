package com.industrika.commons.dao;

import java.util.List;

import com.industrika.commons.dto.Currency;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface CurrencyDao {
	
	public Integer add(Currency dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Currency dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Currency dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idCurrency) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Currency get(Currency dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Currency get(Integer idCurrency) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Currency> find(Currency dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
}
