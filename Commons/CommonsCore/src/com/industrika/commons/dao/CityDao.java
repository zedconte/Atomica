package com.industrika.commons.dao;

import java.util.List;

import com.industrika.commons.dto.City;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface CityDao {
	public Integer add(City dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(City dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(City dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idCity) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public City get(City dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public City get(Integer idCity) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<City> find(City dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
