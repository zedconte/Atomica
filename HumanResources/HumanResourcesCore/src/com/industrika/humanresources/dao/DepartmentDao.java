package com.industrika.humanresources.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Department;

public interface DepartmentDao {
	public Integer add(Department dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Department dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Department dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idDepartment) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Department get(Department dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Department get(Integer idDepartment) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Department> find(Department dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
