package com.industrika.commons.businesslogic;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.industrika.commons.dao.SystemModuleDao;
import com.industrika.commons.dao.SystemOptionDao;
import com.industrika.commons.dto.Module;
import com.industrika.commons.dto.Option;


public abstract class MenuOptionsBase implements MenuOptions {
	
	private Module module = null;	
	@Autowired
	protected SystemModuleDao dao;
	@Autowired
	protected SystemOptionDao optionDao;
	@Autowired
	@Qualifier("commonsOptions")
	private MenuOptions commons;	
	
	@Override
	public Module getModule() throws Exception {
		if (module==null){
			module = findModule();
			// If the module doesn't exist then add it
			if (module == null || module.getId() == null){
				module = addNewModule();
				
			}
		}
		loadMenuStructure();
		return module;
	}
	
	protected abstract String getFactoryClass();
	protected abstract String getModuleName();
	protected abstract String getModuleDescription();
	protected abstract void loadMenuStructure();
	
	protected void addMenu(String resourceName, String message) {
		Option op = createOption(resourceName, message);
		addOption(op);
	}

	private Option createOption(String resourceName, String message) {
		Option op = new Option();
		op.setResourceName(resourceName);
		op.setCreationDate(Calendar.getInstance());
		op.setText(message);
		op.setVisible(1);
		return op;
	}
	
	private Module addOption(Option option){
		
		if (module != null){
			option.setModule(module);
			boolean add = true;
			if (module.getOptions() != null){
				for (Option op: module.getOptions()){
					if (op.getResourceName() != null && 
							op.getResourceName().equalsIgnoreCase(option.getResourceName())){
						add = false;
					}
				}
			}
			if (add){
				try{
					option = optionDao.save(option);
					module = dao.findById(module.getId());
					((CommonsMenuOptions)commons).addPrivilegesToAdmin(option);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		return module;
	}
	
	private Module findModule() throws Exception {
		Module toFind = new Module();
		toFind.setName(getModuleName());
		return findModule(toFind);
	}

	private Module addNewModule() throws Exception {
		Module module;
		module = new Module();
		module.setDescription(getModuleDescription());
		module.setName(getModuleName());
		module.setFactory(getFactoryClass());
		return saveModule(module);
		
	}

	private Module findModule(Module toFind) throws Exception {
		
		try{
			List<Module> modules = dao.find(toFind, null);
			if (modules.size()>0)
				return modules.get(0);
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	
	private Module saveModule(Module module) throws Exception {
		try{
			module = dao.save(module);
			return module;
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}

}
