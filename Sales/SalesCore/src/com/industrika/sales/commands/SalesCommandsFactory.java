package com.industrika.sales.commands;

import com.industrika.commons.businesslogic.ApplicationContextProvider;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.commands.IndustrikaCommandsFactory;

public class SalesCommandsFactory implements IndustrikaCommandsFactory {

	@Override
	public IndustrikaCommand getCommand(String formName) {
		IndustrikaCommand command = null;
		if (formName != null){
			if (formName.equalsIgnoreCase("customer")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("customerCommand");
			}
			else if (formName.equalsIgnoreCase("quotation")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("quotationCommand");
			}
			else if (formName.equalsIgnoreCase("sale")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("saleCommand");
			}
			else{
				throw new RuntimeException("form name not defined");
			}
		}
		return command;
	}	

}
