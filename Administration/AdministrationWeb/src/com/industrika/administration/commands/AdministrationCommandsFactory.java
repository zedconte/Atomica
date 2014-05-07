package com.industrika.administration.commands;

import org.apache.commons.lang.StringUtils;
import com.industrika.commons.businesslogic.ApplicationContextProvider;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.commands.IndustrikaCommandsFactory;

public class AdministrationCommandsFactory implements IndustrikaCommandsFactory {

	private static IndustrikaCommand getcommand(String commandBean){
		return (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean(commandBean);
    
	}
	@Override
	public IndustrikaCommand getCommand(String formName) {
		IndustrikaCommand command = null;
		if (formName != null){
			if (StringUtils.isBlank(formName)){
				throw new IllegalArgumentException("El nombre de la forma no puede estar vacío");
			}
			
			command=getcommand(formName.concat("Command"));
		
			if(command==null){
				throw new RuntimeException("form name not defined");
			}
		}
		return command;
	}
}
