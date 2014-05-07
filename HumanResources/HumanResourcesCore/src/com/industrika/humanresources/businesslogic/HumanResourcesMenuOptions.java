package com.industrika.humanresources.businesslogic;

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

@Component("humanresourcesOptions")
public class HumanResourcesMenuOptions implements MenuOptions{
	
	@Autowired
	private SystemModuleDao dao;
	@Autowired
	private SystemOptionDao optionDao;
	@Autowired
	@Qualifier("commonsOptions")
	private MenuOptions commons;
	
	private final String FACTORY="com.industrika.humanresources.commands.HumaResourcesCommandsFactory";
	
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
		toFind.setName("Recursos Humanos");
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
			module.setDescription("Opciones para el módulo de recursos humanos");
			module.setName("Recursos Humanos");
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
		op.setResourceName("direction");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Direcciones");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);
		
		op = new Option();
		op.setResourceName("management");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Gerencias");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		op = new Option();
		op.setResourceName("department");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Departamentos");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		op = new Option();
		op.setResourceName("position");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Puestos");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		op = new Option();
		op.setResourceName("shift");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Turnos");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		op = new Option();
		op.setResourceName("employee");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Empleados");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		op = new Option();
		op.setResourceName("absence");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Registro de Faltas");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		op = new Option();
		op.setResourceName("payroll");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Nómina");
		op.setVisible(1);
		module = ((CommonsMenuOptions)commons).addMenu(module, op);

		return module;
	}
}
