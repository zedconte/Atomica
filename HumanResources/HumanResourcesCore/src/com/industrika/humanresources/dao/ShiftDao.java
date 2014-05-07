package com.industrika.humanresources.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Shift;

public interface ShiftDao {
	public Integer add(Shift dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Shift dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Shift dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idShift) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Shift get(Shift dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Shift get(Integer idShift) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Shift> find(Shift dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
