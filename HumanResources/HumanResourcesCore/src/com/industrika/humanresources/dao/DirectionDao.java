package com.industrika.humanresources.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Direction;

public interface DirectionDao {
	public Integer add(Direction dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Direction dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Direction dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idDirection) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Direction get(Direction dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Direction get(Integer idDirection) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Direction> find(Direction dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
