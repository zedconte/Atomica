package com.industrika.inventory.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.dto.Branch;
import com.industrika.commons.dto.Warehouse;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.inventory.dao.InventoryDao;
import com.industrika.inventory.dto.Inventory;
import com.industrika.inventory.dto.Item;

@Repository("inventorDao")
public class InventoryDaoHibernate extends RootDao implements InventoryDao {

	@Override
	@Transactional
	public void updateInventory(Item item, Branch branch, Warehouse warehouse,
			Double quantity, Integer type) throws Exception {
		List<Inventory> res = null;
		Inventory inventory = null;
		try{
			res = get(item,branch,warehouse, null);
			if (res != null && res.size() == 1){
				inventory = res.get(0);
			}
		}catch(Exception ex){
			inventory = null;
		}
		double sign = 1;
		if (type == null){
			type = new Integer(0);
		}
		if (type.intValue() == 1){
			sign = -1;
		}
		if (inventory != null){
			inventory.setQuantity(inventory.getQuantity().doubleValue()+(quantity.doubleValue()*sign));
			sessionFactory.getCurrentSession().saveOrUpdate(inventory);
		} else {
			inventory = new Inventory();
			inventory.setBranch(branch);
			inventory.setItem(item);
			inventory.setWarehouse(warehouse);
			inventory.setQuantity(quantity);
			sessionFactory.getCurrentSession().save(inventory);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Inventory> get(Item item, Branch branch, Warehouse warehouse, String[] order)
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		List<Inventory> res = null;
		try{
			Criteria filter = sessionFactory.getCurrentSession().createCriteria(Inventory.class);
			if (branch != null && branch.getIdBranch() != null && branch.getIdBranch().intValue() > 0){
				filter.add(Restrictions.eq("branch.idBranch", branch.getIdBranch().intValue()));
			}
			if (warehouse != null && warehouse.getIdWarehouse() != null && warehouse.getIdWarehouse().intValue() > 0){
				filter.add(Restrictions.eq("warehouse.idWarehouse", warehouse.getIdWarehouse().intValue()));
			}
			if (item != null && item.getIdItem() != null && item.getIdItem().intValue() > 0){
				filter.add(Restrictions.eq("item.idItem", item.getIdItem().intValue()));
			}
			if (order != null && order.length > 0){
				for (int a=0;a<order.length;a++){
					if (order[a] != null && !order[a].trim().equalsIgnoreCase("")){
						if (order[a].indexOf("item") > -1){
							filter.createAlias("item", "item");
						}
						if (order[a].indexOf("warehouse") > -1){
							filter.createAlias("warehouse", "warehouse");
						}
						if (order[a].indexOf("branch") > -1){
							filter.createAlias("branch", "branch");
						}
						filter.addOrder(Order.asc(order[a]));
					}
				}
			}			
			res = filter.list();
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery"), ex);
		}
		if (res == null || res.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return res;
	}

}
