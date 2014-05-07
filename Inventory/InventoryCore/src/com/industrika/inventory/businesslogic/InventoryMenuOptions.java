package com.industrika.inventory.businesslogic;

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

@Component("inventoryOptions")
public class InventoryMenuOptions implements MenuOptions{
	
	@Autowired
	private SystemModuleDao dao;
	@Autowired
	private SystemOptionDao optionDao;
	@Autowired
	@Qualifier("commonsOptions")
	private MenuOptions commons;
	
	private final String FACTORY="com.industrika.inventory.commands.InventoryCommandsFactory";
	
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
		toFind.setName("Inventarios");
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
			module.setDescription("Módulo de Inventarios");
			module.setName("Inventarios");
			module.setFactory(FACTORY);
			try{
				dao.save(module);
				module = dao.find(module, null).get(0);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

		Option op = new Option();
		op.setResourceName("provider");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Proveedores");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		// Verify the option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("item");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Artículos");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		// Verify the option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("inventorymovement");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Movimientos al Inventario");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		// Verify the option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("inventory");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Consulta del Inventario");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		// Verify the option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("buyorder");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Ordenes de Compra");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		// Verify the option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("purchase");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Entradas al almacén");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		return module;
	}
}
