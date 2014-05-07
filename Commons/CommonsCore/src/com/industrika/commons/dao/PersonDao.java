package com.industrika.commons.dao;

import java.util.List;

import com.industrika.commons.dto.Person;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

public interface PersonDao {
	
	public Integer add(Person dto) throws IndustrikaValidationException, IndustrikaPersistenceException;
	
	public void update(Person dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Person dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public void remove(Integer idPerson) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Person get(Person dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public Person get(Integer idPerson) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public List<Person> find(Person dto, String[] orderFields) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
}
