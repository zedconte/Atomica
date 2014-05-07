package com.industrika.inventory.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.InventoryMovement;

public interface InventoryMovementDao {
	public Integer add(InventoryMovement dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException;
	public void update(InventoryMovement dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException,IndustrikaObjectNotFoundException;
	public void delete(InventoryMovement dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public void delete(Integer idInventoryMovement) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public InventoryMovement get(InventoryMovement dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public InventoryMovement get(Integer idInventoryMovement) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public List<InventoryMovement> find(InventoryMovement dto,String[] order) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
