package com.industrika.business.commands;

import com.industrika.commons.businesslogic.ApplicationContextProvider;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.commands.IndustrikaCommandsFactory;

public class BusinessCommandsFactory implements IndustrikaCommandsFactory {

	@Override
	public IndustrikaCommand getCommand(String formName) {
		IndustrikaCommand command = null;
		if (formName != null){
			if (formName.equalsIgnoreCase("reportescontables")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("reportescontablesCommand");
			}
			else if (formName.equalsIgnoreCase("inventory")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("inventoryCommand");
			}
			else if (formName.equalsIgnoreCase("salesreport")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("salesreportCommand");
			}
			else if (formName.equalsIgnoreCase("purchasereport")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("purchasereportCommand");
			}
			else{
				throw new RuntimeException("form name not defined");
			}
		}
		return command;
	}	

}
