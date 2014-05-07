package com.industrika.commons.commands;

import org.springframework.context.ApplicationContext;

import com.industrika.commons.businesslogic.ApplicationContextProvider;

public abstract class AbstractCommandFactory implements
		IndustrikaCommandsFactory {

    protected static final ApplicationContext appCtx = ApplicationContextProvider.getCtx();
	
	protected static IndustrikaCommand getcommand(String commandBean){
			return (IndustrikaCommand) appCtx.getBean(commandBean);
	}
	
	

}
