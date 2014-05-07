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
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.utils.TextFormatter;
import com.industrika.humanresources.dao.PayrollDao;
import com.industrika.humanresources.dao.EmployeeDao;
import com.industrika.humanresources.dto.Payroll;

@Component ("payrollCommand")
public class PayrollCommand implements IndustrikaCommand {
	@Autowired
	private PayrollDao dao;

	@Autowired
	private EmployeeDao daoEmployee;

	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("form", "/humanresources/payroll.jsp");
		if (parameters.get("returnType") != null && ((String[])parameters.get("returnType"))[0].equalsIgnoreCase("jsp")){
			String action = parameters.get("action") != null ? ((String[])parameters.get("action"))[0] : "";
			Payroll dto = makeDtoForWeb(parameters);
			results.put("list", new Vector<Payroll>());
			if (action != null && action.equalsIgnoreCase("add")){
				try{
					dto.setIdPayroll(dao.add(dto));
					dto = new Payroll();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("update")){
				try{
					dao.update(dto);
					dto = new Payroll();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("remove")){
				try{
					dao.remove(dto);
					dto = new Payroll();
					results.put("message", CommonsMessages.getMessage("sucess_persistence"));
				}catch(DataIntegrityViolationException e){
					results.put("error","No puede ser eliminado pues existe información que depende de ésta");
				}catch(Exception ex){
					results.put("error", ex.getMessage());
				}
			}
			if (action != null && action.equalsIgnoreCase("search")){
				try{
					String[] order={"begin"};
					Vector<Payroll> lista = new Vector<Payroll>(dao.find(dto, order));
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

	private Payroll makeDtoForWeb(Map<String, String[]> parameters){
		Payroll dto = new Payroll();
		if (parameters.get("idPayroll") != null){
			try{
				dto.setIdPayroll(new Integer(((String[])parameters.get("idPayroll"))[0]));
			}catch(Exception ex){
				dto.setIdPayroll(null);
			}
		}
		if (parameters.get("days") != null){
			try{
				dto.setDays(new Integer(((String[])parameters.get("days"))[0]));
			}catch(Exception ex){
				dto.setDays(null);
			}
		}
		if (parameters.get("begin") != null){
			try{
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = formatter.parse(((String[])parameters.get("begin"))[0]);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				dto.setBegin(calendar);
			}
			catch(Exception e){
				dto.setBegin(null);
			}
		}
		if (parameters.get("end") != null){
			try{
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = formatter.parse(((String[])parameters.get("end"))[0]);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				dto.setEnd(calendar);
			}
			catch(Exception e){
				dto.setEnd(null);
			}
		}

		if (parameters.get("discount") != null){
			try{
				dto.setDiscount((TextFormatter.getNumber(((String[])parameters.get("discount"))[0])).doubleValue());
			} catch(Exception e){
				dto.setDiscount(null);
			}
		}

		if (parameters.get("deduction") != null){
			try{
				dto.setDecutions((TextFormatter.getNumber(((String[])parameters.get("deduction"))[0])).doubleValue());
			} catch(Exception e){
				dto.setDecutions(null);
			}
		}

		if (parameters.get("total") != null){
			try{
				dto.setTotal((TextFormatter.getNumber(((String[])parameters.get("total"))[0])).doubleValue());
			} catch(Exception e){
				dto.setTotal(null);
			}
		}

		if (parameters.get("subtotal") != null){
			try{
				dto.setSubtotal((TextFormatter.getNumber(((String[])parameters.get("subtotal"))[0])).doubleValue());
			} catch(Exception e){
				dto.setSubtotal(null);
			}
		}

		return dto;
	}
}
