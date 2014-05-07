package com.industrika.humanresources.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Employee;

public interface EmployeeDao {
	public Integer add(Employee dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Employee dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Employee dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idEmployee) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Employee get(Employee dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Employee get(Integer idEmployee) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Employee> find(Employee dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
