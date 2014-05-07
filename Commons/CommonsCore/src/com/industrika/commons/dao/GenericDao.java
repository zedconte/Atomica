package com.industrika.commons.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface GenericDao<K,E> {

	E save(E vo) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	void update(E vo) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	void remove(E vo) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	E findById(K id) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	List<E> findAll() throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	List<E> find(E dto, String[] sortFields) throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException;
}
