package com.industrika.inventory.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.Provider;

public interface ProviderDao {
	public Integer add(Provider provider) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException;
	public void update(Provider provider) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException,IndustrikaObjectNotFoundException;
	public void delete(Provider provider) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public void delete(Integer idProvider) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Provider get(Provider provider) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Provider get(Integer idProvider) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public List<Provider> find(Provider provider,String[] order) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public void updateBalance(Integer idProvider, Double amount);
	public void updateAcumulated(Integer idProvider, Double amount);
}
