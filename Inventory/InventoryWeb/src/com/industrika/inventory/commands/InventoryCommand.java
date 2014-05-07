package com.industrika.inventory.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.dao.BranchDao;
import com.industrika.commons.dao.WarehouseDao;
import com.industrika.inventory.dao.InventoryDao;
import com.industrika.inventory.dao.ItemDao;
import com.industrika.inventory.dto.Inventory;

@Component("inventoryCommand")
public class InventoryCommand implements IndustrikaCommand {
	
	@Autowired
	private InventoryDao dao;

	@Autowired
	private ItemDao daoItem;

	@Autowired
	private BranchDao daoBranch;

	@Autowired
	private WarehouseDao daoWarehouse;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/inventory/inventory.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Inventory dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Inventory>());
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"branch.name","warehouse.name","item.name"};
					Vector<Inventory> lista = new Vector<Inventory>(dao.get(dto.getItem(),dto.getBranch(),dto.getWarehouse(), order));
					if (lista != null && lista.size() == 1){
						dto = lista.get(0);
					}
					results.put("list", lista);
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			results.put("dto", dto);
		}
		return results;
	}

	private Inventory makeDtoForWeb(Map<String, String[]> parameters){
		Inventory dto = new Inventory();
		if (parameters.get("idItem") != null){
			try{
				dto.setItem(daoItem.get(new Integer(((String[])parameters.get("idItem"))[0])));
			}catch(Exception ex){
				dto.setItem(null);
			}
		}
		if (parameters.get("idBranch") != null){
			try{
				dto.setBranch(daoBranch.findById(new Integer(((String[])parameters.get("idBranch"))[0])));
			}catch(Exception ex){
				dto.setBranch(null);
			}
		}
		if (parameters.get("idWarehouse") != null){
			try{
				dto.setWarehouse(daoWarehouse.findById(new Integer(((String[])parameters.get("idWarehouse"))[0])));
			}catch(Exception ex){
				dto.setWarehouse(null);
			}
		}

		return dto;
	}
}
