package com.industrika.inventory.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.Purchase;

public interface PurchaseDao {
	public Integer add(Purchase dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException;
	public void update(Purchase dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException,IndustrikaObjectNotFoundException;
	public void delete(Purchase dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public void delete(Integer idPurchase) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Purchase get(Purchase dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Purchase get(Integer idPurchase) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public List<Purchase> find(Purchase dto,String[] order) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
