package com.industrika.inventory.dao;

import java.util.List;

import com.industrika.commons.dto.Branch;
import com.industrika.commons.dto.Warehouse;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.inventory.dto.Inventory;
import com.industrika.inventory.dto.Item;

public interface InventoryDao {
	public void updateInventory(Item item, Branch branch, Warehouse warehouse, Double quantity, Integer type) throws Exception;
	public List<Inventory> get(Item item, Branch branch, Warehouse warehouse, String[] order) throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException;
}
