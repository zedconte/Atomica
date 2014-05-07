package com.industrika.humanresources.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Position;

public interface PositionDao {
	public Integer add(Position dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Position dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Position dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idPosition) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Position get(Position dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Position get(Integer idPosition) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Position> find(Position dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
