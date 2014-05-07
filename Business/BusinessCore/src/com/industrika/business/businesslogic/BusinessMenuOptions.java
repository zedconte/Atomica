package com.industrika.business.businesslogic;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.industrika.commons.businesslogic.CommonsMenuOptions;
import com.industrika.commons.businesslogic.MenuOptions;
import com.industrika.commons.dao.SystemModuleDao;
import com.industrika.commons.dao.SystemOptionDao;
import com.industrika.commons.dto.Module;
import com.industrika.commons.dto.Option;

@Component("businessOptions")
public class BusinessMenuOptions implements MenuOptions{
	
	@Autowired
	private SystemModuleDao dao;
	@Autowired
	private SystemOptionDao optionDao;
	@Autowired
	@Qualifier("commonsOptions")
	private MenuOptions commons;

	private final String FACTORY="com.industrika.business.commands.BusinessCommandsFactory";
	
	@Override
	public Module getModule() {
		return preloadOptions();
	}
	/**
	 * This method will preload the module options, for that reason is very important  
	 * than when a new options is added to the module, that new options MUST be added 
	 * in this method too.
	 */
	private Module preloadOptions(){
		Module toFind = new Module();
		toFind.setName("Negocios");
		Module module = null;
		try{
			module = dao.find(toFind, null).get(0);
		}catch(Exception ex){
			module = null;
			ex.getMessage();
		}
		// If the module doesn't exist then add it
		if (module == null || module.getId() == null){
			module = new Module();
			module.setDescription("Actividades de Negocios");
			module.setName("Negocios");
			module.setFactory(FACTORY);
			try{
				dao.save(module);
				module = dao.find(module, null).get(0);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		// Verify the option, if the option doesn't exist then add it
		Option op = new Option();
		op.setResourceName("reportescontables");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Reportes Contables");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);
		
		op = new Option();
		op.setResourceName("inventory");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Consulta del Inventario");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);


		// Verify the bank option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("salesreport");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Reporte de ventas");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);
		
		
		op = new Option();
		op.setResourceName("purchasereport");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Reporte de Compras");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);
		
		return module;
	}
}
