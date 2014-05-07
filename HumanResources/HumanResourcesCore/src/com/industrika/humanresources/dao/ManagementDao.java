package com.industrika.humanresources.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Management;

public interface ManagementDao {
	public Integer add(Management dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Management dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Management dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idManagement) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Management get(Management dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Management get(Integer idManagement) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Management> find(Management dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
