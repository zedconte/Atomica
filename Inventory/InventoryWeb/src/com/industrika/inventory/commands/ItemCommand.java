package com.industrika.inventory.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.administration.dao.TaxDao;
import com.industrika.administration.dto.Tax;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.dao.TrademarkDao;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.utils.TextFormatter;
import com.industrika.inventory.dao.ItemDao;
import com.industrika.inventory.dao.ProviderDao;
import com.industrika.inventory.dto.Item;

@Component("itemCommand")
public class ItemCommand implements IndustrikaCommand {
	
	@Autowired
	private ItemDao dao;

	@Autowired
	private ProviderDao daoProvider;

	@Autowired
	private TrademarkDao daoTrademark;

	@Autowired
	private TaxDao daoTax;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/inventory/item.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Item dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Item>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdItem(dao.add(dto));
					dto = new Item();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Item();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.delete(dto);
					dto = new Item();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"name"};
					Vector<Item> lista = new Vector<Item>(dao.find(dto, order));
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

	private Item makeDtoForWeb(Map<String, String[]> parameters){
		Item dto = new Item();
		if (parameters.get("idItem") != null){
			try{
				dto.setIdItem(new Integer(((String[])parameters.get("idItem"))[0]));				
			}catch(Exception ex){
				dto.setIdItem(null);
			}
		}
		if (parameters.get("name") != null){
			dto.setName(((String[])parameters.get("name"))[0]);
		}
		if (parameters.get("code") != null){
			dto.setCode(((String[])parameters.get("code"))[0]);
		}
		if (parameters.get("description") != null){
			dto.setDescription(((String[])parameters.get("description"))[0]);
		}
		if (parameters.get("price") != null){
			try{
				dto.setPrice((TextFormatter.getCurrency(((String[])parameters.get("price"))[0])).doubleValue());				
			}catch(Exception ex){
				ex.printStackTrace();
				dto.setPrice(null);
			}
		}
		if (parameters.get("cost") != null){
			try{
				dto.setCost((TextFormatter.getCurrency(((String[])parameters.get("cost"))[0])).doubleValue());				
			}catch(Exception ex){
				dto.setCost(null);
			}
		}
		List<Tax> taxes = new Vector<Tax>();
		if (parameters.get("idTax") != null){
			String[] ids = ((String[])parameters.get("idTax"));
			for (int a=0;a<ids.length;a++){
				try{
					Tax tx = daoTax.findById(new Integer(ids[a]));
					taxes.add(tx);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		if (taxes.size() > 0){
			dto.setTaxes(taxes);
		}
		if (parameters.get("idProvider") != null){
			try{
				dto.setProvider(daoProvider.get(new Integer(((String[])parameters.get("idProvider"))[0])));
			}catch(Exception ex){
				dto.setProvider(null);
			}
		}
		if (parameters.get("idTrademark") != null){
			try{
				dto.setTrademark(daoTrademark.findById(new Integer(((String[])parameters.get("idTrademark"))[0])));
			}catch(Exception ex){
				dto.setTrademark(null);
			}
		}

		return dto;
	}
}
