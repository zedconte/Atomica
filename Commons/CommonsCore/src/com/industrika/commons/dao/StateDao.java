package com.industrika.commons.dao;

import java.util.List;

import com.industrika.commons.dto.State;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface StateDao {
	public Integer add(State dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(State dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(State dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idState) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public State get(State dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public State get(Integer idState) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<State> find(State dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
