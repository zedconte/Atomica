package com.industrika.inventory.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.BuyOrder;

public interface BuyOrderDao {
	public Integer add(BuyOrder dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException;
	public void update(BuyOrder dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException,IndustrikaObjectNotFoundException;
	public void delete(BuyOrder dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public void delete(Integer idBuyOrder) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public BuyOrder get(BuyOrder dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public BuyOrder get(Integer idBuyOrder) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public List<BuyOrder> find(BuyOrder dto,String[] order) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
}
