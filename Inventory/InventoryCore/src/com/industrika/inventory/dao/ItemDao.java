package com.industrika.inventory.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.inventory.dto.Item;

public interface ItemDao {
	public Integer add(Item item) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException;
	public void update(Item item) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException,IndustrikaObjectNotFoundException;
	public void delete(Item item) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public void delete(Integer idItem) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Item get(Item item) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Item get(Integer idItem) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public List<Item> find(Item item,String[] order) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
