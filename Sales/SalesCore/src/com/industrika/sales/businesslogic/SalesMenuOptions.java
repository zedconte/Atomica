package com.industrika.sales.businesslogic;

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

@Component("salesOptions")
public class SalesMenuOptions implements MenuOptions{
	
	@Autowired
	private SystemModuleDao dao;
	@Autowired
	private SystemOptionDao optionDao;
	@Autowired
	@Qualifier("commonsOptions")
	private MenuOptions commons;
	
	private final String FACTORY="com.industrika.sales.commands.SalesCommandsFactory";
	
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
		toFind.setName("Compras y Ventas");
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
			module.setDescription("Movimientos de ventas");
			module.setName("Compras y Ventas");
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
		op.setResourceName("customer");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Clientes");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);
		
		// Verify the option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("quotation");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Cotizaciones");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		// Verify the option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("sale");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Ventas y Facturación");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		return module;
	}
}
