package com.industrika.maintenance.commands;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;




import com.industrika.commons.businesslogic.ApplicationContextProvider;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.commands.IndustrikaCommandsFactory;


public class MaintenanceCommandsFactory implements IndustrikaCommandsFactory {

	private static final ApplicationContext appCtx = ApplicationContextProvider.getCtx();
	
	
	private static IndustrikaCommand getcommand(String commandBean){
			return (IndustrikaCommand) appCtx.getBean(commandBean);
	}
	
	@Override
	public IndustrikaCommand getCommand(String formName) {
		
		if (StringUtils.isBlank(formName)) throw new IllegalArgumentException("El nombre de la forma no puede estar vacÃ­o");
		
		return getcommand(formName.concat("Command"));
	}

}
