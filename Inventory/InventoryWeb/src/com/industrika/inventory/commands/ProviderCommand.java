package com.industrika.inventory.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.dao.CityDao;
import com.industrika.commons.dto.Address;
import com.industrika.commons.dto.Phone;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.utils.TextFormatter;
import com.industrika.inventory.dao.ProviderDao;
import com.industrika.inventory.dto.Provider;

@Component("providerCommand")
public class ProviderCommand implements IndustrikaCommand {
	
	@Autowired
	private ProviderDao dao;

	@Autowired
	private CityDao daoCity;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/inventory/provider.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Provider dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Provider>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdPerson(dao.add(dto));
					dto = new Provider();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Provider();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.delete(dto);
					dto = new Provider();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"businessName"};
					Vector<Provider> lista = new Vector<Provider>(dao.find(dto, order));
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

	private Provider makeDtoForWeb(Map<String, String[]> parameters){
		Provider dto = new Provider();
		if (parameters.get("idPerson") != null){
			try{
				dto.setIdPerson(new Integer(((String[])parameters.get("idPerson"))[0]));				
			}catch(Exception ex){
				dto.setIdPerson(null);
			}
		}
		if (parameters.get("businessName") != null){
			dto.setBusinessName(((String[])parameters.get("businessName"))[0]);
		}
		if (parameters.get("email") != null){
			dto.setEmail(((String[])parameters.get("email"))[0]);
		}
		if (parameters.get("rfc") != null){
			dto.setRfc(((String[])parameters.get("rfc"))[0]);
		}
		if (parameters.get("purchasesContactName") != null){
			dto.setPurchasesContactName(((String[])parameters.get("purchasesContactName"))[0]);
		}
		if (parameters.get("acumulated") != null){
			try{
				dto.setAcumulated((TextFormatter.getCurrency(((String[])parameters.get("acumulated"))[0])).doubleValue());				
			}catch(Exception ex){
				dto.setAcumulated(null);
			}
		}
		if (parameters.get("balance") != null){
			try{
				dto.setBalance((TextFormatter.getCurrency(((String[])parameters.get("balance"))[0])).doubleValue());				
			}catch(Exception ex){
				dto.setBalance(null);
			}
		}
		List<Phone> phones = new Vector<Phone>();
		if (parameters.get("phoneNumber") != null){
			String[] ids = ((String[])parameters.get("idPhone"));
			String[] numbers = ((String[])parameters.get("phoneNumber"));
			String[] areas = ((String[])parameters.get("areaCode"));
			String[] types = ((String[])parameters.get("phoneType"));
			for (int a=0;a<numbers.length;a++){
				Phone phone = new Phone();
				try{
					phone.setIdPhone(new Integer(ids[a]));
				}catch(Exception ex){
					ex.getMessage();
				}
				phone.setNumber(numbers[a]);
				phone.setAreaCode(areas[a]);
				phone.setType(types[a]);
				phones.add(phone);
			}
		}
		if (phones.size() > 0){
			dto.setPhones(phones);
		}
		List<Address> addresses = new Vector<Address>();
		if (parameters.get("street") != null){
			String[] ids = ((String[])parameters.get("idAddress"));
			String[] streets = ((String[])parameters.get("street"));
			String[] externalNumbers = ((String[])parameters.get("extNumber"));
			String[] internalNumbers = ((String[])parameters.get("intNumber"));
			String[] suburbs = ((String[])parameters.get("suburb"));
			String[] cities = ((String[])parameters.get("idCity"));
			String[] zipCodes = ((String[])parameters.get("zipcode"));
			for (int a=0;a<streets.length;a++){
				Address address = new Address();
				try{
					address.setIdAddress(new Integer(ids[a]));
				}catch(Exception ex){
					ex.getMessage();
				}
				address.setExtNumber(externalNumbers[a]);
				address.setIntNumber(internalNumbers[a]);
				address.setStreet(streets[a]);
				address.setSuburb(suburbs[a]);
				address.setZipCode(zipCodes[a]);
				try{
					address.setCity(daoCity.get(new Integer(cities[a])));
				}catch(Exception ex){
					ex.getMessage();
				}
				addresses.add(address);
			}
		}
		if (addresses.size() > 0){
			dto.setAddresses(addresses);
		}

		return dto;
	}
}
