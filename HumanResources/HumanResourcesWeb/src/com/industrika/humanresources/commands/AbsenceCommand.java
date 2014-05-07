package com.industrika.humanresources.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.humanresources.dao.AbsenceDao;
import com.industrika.humanresources.dao.EmployeeDao;
import com.industrika.humanresources.dto.Absence;

@Component ("absenceCommand")
public class AbsenceCommand implements IndustrikaCommand {
	@Autowired
	private AbsenceDao dao;

	@Autowired
	private EmployeeDao daoEmployee;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/humanresources/absence.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Absence dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Absence>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdAbsence(dao.add(dto));
					dto = new Absence();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Absence();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Absence();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"date","employee.firstName","employee.lastName"};
					Vector<Absence> lista = new Vector<Absence>(dao.find(dto, order));
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

	private Absence makeDtoForWeb(Map<String, String[]> parameters){
		Absence dto = new Absence();
		if (parameters.get("idAbsence") != null){
			try{
				dto.setIdAbsence(new Integer(((String[])parameters.get("idAbsence"))[0]));
			}catch(Exception ex){
				dto.setIdAbsence(null);
			}
		}
		if (parameters.get("justified") != null){
			try{
				dto.setJustified(new Integer(((String[])parameters.get("justified"))[0]));
			}catch(Exception ex){
				dto.setJustified(null);
			}
		}
		if (parameters.get("applyDiscount") != null){
			try{
				dto.setApplyDiscount(new Integer(((String[])parameters.get("applyDiscount"))[0]));
			}catch(Exception ex){
				dto.setApplyDiscount(null);
			}
		}
		if (parameters.get("reason") != null){
			dto.setReason(((String[])parameters.get("reason"))[0]);
		}
		if (parameters.get("date") != null){
			try{
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = formatter.parse(((String[])parameters.get("date"))[0]);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				dto.setDate(calendar);
			}
			catch(Exception e){
				dto.setDate(null);
			}
		}
		if (parameters.get("idEmployee") != null){
			String employee=((String[])parameters.get("idEmployee"))[0];
			if (employee != null && !employee.equalsIgnoreCase("")){
				try {
					dto.setEmployee(daoEmployee.get(new Integer(((String[])parameters.get("idEmployee"))[0])));
				} catch (NumberFormatException | IndustrikaPersistenceException
						| IndustrikaObjectNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		return dto;
	}
}
