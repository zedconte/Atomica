package com.industrika.sales.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.sales.dto.Customer;

public interface CustomerDao {
	public Integer add(Customer customer) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException;
	public void update(Customer customer) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException,IndustrikaObjectNotFoundException;
	public void delete(Customer customer) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public void delete(Integer idCustomer) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Customer get(Customer customer) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Customer get(Integer idCustomer) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public List<Customer> find(Customer customer,String[] order) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public void updateBalance(Integer idCustomer, Double amount);
	public void updateAcumulated(Integer idCustomer, Double amount);
}
