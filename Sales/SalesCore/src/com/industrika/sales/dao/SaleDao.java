package com.industrika.sales.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.sales.dto.Sale;

public interface SaleDao {
	public Integer add(Sale dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException;
	public void update(Sale dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException,IndustrikaObjectNotFoundException;
	public void delete(Sale dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public void delete(Integer idSale) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Sale get(Sale dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Sale get(Integer idSale) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public List<Sale> find(Sale dto,String[] order) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
