package com.industrika.humanresources.commands;

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
import com.industrika.humanresources.dao.DepartmentDao;
import com.industrika.humanresources.dao.EmployeeDao;
import com.industrika.humanresources.dao.PositionDao;
import com.industrika.humanresources.dao.ShiftDao;
import com.industrika.humanresources.dto.Employee;

@Component("employeeCommand")
public class EmployeeCommand implements IndustrikaCommand {
	
	@Autowired
	private EmployeeDao dao;

	@Autowired
	private CityDao daoCity;

	@Autowired
	private DepartmentDao daoDepartment;

	@Autowired
	private PositionDao daoPosition;

	@Autowired
	private ShiftDao daoShift;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/humanresources/employee.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Employee dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Employee>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdPerson(dao.add(dto));
					dto = new Employee();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Employee();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Employee();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"firstName","lastName","middleName"};
					Vector<Employee> lista = new Vector<Employee>(dao.find(dto, order));
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

	private Employee makeDtoForWeb(Map<String, String[]> parameters){
		Employee dto = new Employee();
		if (parameters.get("idPerson") != null){
			try{
				dto.setIdPerson(new Integer(((String[])parameters.get("idPerson"))[0]));				
			}catch(Exception ex){
				dto.setIdPerson(null);
			}
		}
		if (parameters.get("firstName") != null){
			dto.setFirstName(((String[])parameters.get("firstName"))[0]);
		}
		if (parameters.get("lastName") != null){
			dto.setLastName(((String[])parameters.get("lastName"))[0]);
		}
		if (parameters.get("middleName") != null){
			dto.setMiddleName(((String[])parameters.get("middleName"))[0]);
		}
		if (parameters.get("gender") != null){
			dto.setGender(((String[])parameters.get("gender"))[0]);
		}
		if (parameters.get("rfc") != null){
			dto.setRfc(((String[])parameters.get("rfc"))[0]);
		}
		if (parameters.get("nss") != null){
			dto.setNss(((String[])parameters.get("nss"))[0]);
		}
		if (parameters.get("salary") != null){
			try{
				dto.setSalary((TextFormatter.getCurrency(((String[])parameters.get("salary"))[0])).doubleValue());				
			}catch(Exception ex){
				dto.setSalary(null);
			}
		}
		if (parameters.get("department") != null){
			try{
				dto.setDepartment(daoDepartment.get(new Integer(((String[])parameters.get("department"))[0])));
			}catch(Exception ex){
				dto.setDepartment(null);
			}
		}
		if (parameters.get("position") != null){
			try{
				dto.setPosition(daoPosition.get(new Integer(((String[])parameters.get("position"))[0])));
			}catch(Exception ex){
				dto.setPosition(null);
			}
		}
		if (parameters.get("shift") != null){
			try{
				dto.setShift(daoShift.get(new Integer(((String[])parameters.get("shift"))[0])));
			}catch(Exception ex){
				dto.setShift(null);
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
