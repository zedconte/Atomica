package com.industrika.humanresources.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Payroll;

public interface PayrollDao {
	public Integer add(Payroll dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Payroll dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Payroll dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idPayroll) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Payroll get(Payroll dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Payroll get(Integer idPayroll) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Payroll> find(Payroll dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
