package com.industrika.maintenance.businesslogic;

import org.springframework.stereotype.Component;

import com.industrika.commons.businesslogic.MenuOptionsBase;
import com.industrika.commons.commands.DefaultCommandsFactory;
import com.industrika.maintenance.i18n.MaintenanceMessages;

@Component("maintenanceOptions")
public class MaintenanceMenuOptions extends MenuOptionsBase{


	@Override
	protected String getModuleName() {
		return MaintenanceMessages.getMessage("module.Name");
	}


	@Override
	protected String getModuleDescription() {
		return MaintenanceMessages.getMessage("module.Description");
	}


	@Override
	protected String getFactoryClass() {
		return DefaultCommandsFactory.class.getName();
	}


	@Override
	protected void loadMenuStructure() {
		addMenu("resourceType", MaintenanceMessages.getMessage("resourceType.Title"));
		addMenu("resource", MaintenanceMessages.getMessage("resource.Title"));
		
	}
	
	
}
