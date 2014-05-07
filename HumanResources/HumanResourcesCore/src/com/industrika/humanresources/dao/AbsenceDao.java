package com.industrika.humanresources.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.humanresources.dto.Absence;
import com.industrika.humanresources.dto.Employee;
import com.industrika.humanresources.dto.Payroll;

public interface AbsenceDao {
	public Integer add(Absence dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Absence dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Absence dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idAbsence) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Absence get(Absence dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Absence get(Integer idAbsence) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Absence> find(Absence dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Absence> getByPayrol(Payroll payroll, Employee employee) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
