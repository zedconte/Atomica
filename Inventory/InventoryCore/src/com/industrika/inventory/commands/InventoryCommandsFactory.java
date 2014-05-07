package com.industrika.inventory.commands;

import com.industrika.commons.businesslogic.ApplicationContextProvider;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.commands.IndustrikaCommandsFactory;

public class InventoryCommandsFactory implements IndustrikaCommandsFactory {

	@Override
	public IndustrikaCommand getCommand(String formName) {
		IndustrikaCommand command = null;
		if (formName != null){
			if (formName.equalsIgnoreCase("provider")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("providerCommand");
			}
			else if (formName.equalsIgnoreCase("item")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("itemCommand");
			}
			else if (formName.equalsIgnoreCase("inventorymovement")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("inventorymovementCommand");
			}
			else if (formName.equalsIgnoreCase("inventory")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("inventoryCommand");
			}
			else if (formName.equalsIgnoreCase("buyorder")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("buyorderCommand");
			}
			else if (formName.equalsIgnoreCase("purchase")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("purchaseCommand");
			}
			else{
				throw new RuntimeException("form name not defined");
			}
		}
		return command;
	}	

}
