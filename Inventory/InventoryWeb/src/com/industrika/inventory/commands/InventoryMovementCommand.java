package com.industrika.inventory.commands;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.dao.BranchDao;
import com.industrika.commons.dao.MovementConteptDao;
import com.industrika.commons.dao.WarehouseDao;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.utils.TextFormatter;
import com.industrika.inventory.dao.InventoryMovementDao;
import com.industrika.inventory.dao.ItemDao;
import com.industrika.inventory.dto.InventoryMovement;

@Component("inventorymovementCommand")
public class InventoryMovementCommand implements IndustrikaCommand {
	
	@Autowired
	private InventoryMovementDao dao;

	@Autowired
	private ItemDao daoItem;

	@Autowired
	private BranchDao daoBranch;

	@Autowired
	private WarehouseDao daoWarehouse;

	@Autowired
	private MovementConteptDao daoConcept;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/inventory/inventorymovement.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			InventoryMovement dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<InventoryMovement>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdMovement(dao.add(dto));
					dto = new InventoryMovement();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"date","branch.name","warehouse.name","item.name"};
					Vector<InventoryMovement> lista = new Vector<InventoryMovement>(dao.find(dto, order));
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

	private InventoryMovement makeDtoForWeb(Map<String, String[]> parameters){
		InventoryMovement dto = new InventoryMovement();
		if (parameters.get("idMovement") != null){
			try{
				dto.setIdMovement(new Integer(((String[])parameters.get("idMovement"))[0]));				
			}catch(Exception ex){
				dto.setIdMovement(null);
			}
		}
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
		if (parameters.get("idConcept") != null){
			try{
				dto.setConcept(daoConcept.findById(new Integer(((String[])parameters.get("idConcept"))[0])));
			}catch(Exception ex){
				dto.setItem(null);
			}
		}
		if (parameters.get("date") != null){
			SimpleDateFormat sdf=new SimpleDateFormat("mm/dd/yy");
			try{
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sdf.parse(((String[])parameters.get("date"))[0]));
				dto.setDate(calendar);
			}catch(Exception ex){
				ex.getMessage();
			}
		}
		if (parameters.get("quantity") != null){
			try{
				dto.setQuantity((TextFormatter.getNumber(((String[])parameters.get("quantity"))[0])).doubleValue());				
			}catch(Exception ex){
				ex.printStackTrace();
				dto.setQuantity(null);
			}
		}
		if (parameters.get("reference") != null){
			dto.setReference(((String[])parameters.get("reference"))[0]);				
		}
		if (parameters.get("serial") != null){
			dto.setReference(((String[])parameters.get("serial"))[0]);				
		}
		if (parameters.get("type") != null){
			try{
				dto.setType(new Integer(((String[])parameters.get("type"))[0]));				
			}catch(Exception ex){
				dto.setType(null);
			}
		}

		return dto;
	}
}
