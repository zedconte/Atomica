package com.industrika.administration.dao;

import java.util.List;

import com.industrika.administration.dto.Deduction;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface DeductionDao {
	public Integer add(Deduction dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Deduction dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Deduction dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idPerson) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Deduction get(Deduction dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Deduction get(Integer idDeduction) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Deduction> find(Deduction dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
