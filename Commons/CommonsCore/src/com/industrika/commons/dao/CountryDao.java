package com.industrika.commons.dao;

import java.util.List;

import com.industrika.commons.dto.Country;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface CountryDao {
	
	public Integer add(Country dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Country dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Country dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idCountry) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Country get(Country dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Country get(Integer idCountry) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Country> find(Country dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
}
