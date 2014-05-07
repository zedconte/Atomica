package com.industrika.commons.commands;

import com.industrika.commons.businesslogic.ApplicationContextProvider;

public class CommonsCommandsFactory implements IndustrikaCommandsFactory {

	@Override
	public IndustrikaCommand getCommand(String formName) {
		IndustrikaCommand command = null;
		if (formName != null){
			if (formName.equalsIgnoreCase("person")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("personCommand");
			} 
			else if (formName.equalsIgnoreCase("country")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("countryCommand");
			} 
			else if (formName.equalsIgnoreCase("state")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("stateCommand");
			}
			else if (formName.equalsIgnoreCase("city")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("cityCommand");
			}
			else if (formName.equalsIgnoreCase("currency")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("currencyCommand");
			}
			else if (formName.equalsIgnoreCase("module")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("moduleCommand");
			}
			else if (formName.equalsIgnoreCase("action")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("systemActionCommand");
			}
			else if (formName.equalsIgnoreCase("systemoption")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("systemOptionCommand");
			}
			else if(formName.equalsIgnoreCase("systemprivilege")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("systemPrivilegeCommand");
			}
			else if(formName.equalsIgnoreCase("role")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("roleCommand");
			}
			else if(formName.equalsIgnoreCase("roleprivilege")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("rolePrivilegeCommand");
			}
			else if(formName.equalsIgnoreCase("user")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("userCommand");
			}
			else if(formName.equalsIgnoreCase("userrole")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("userRoleCommand");
			}
			else if(formName.equalsIgnoreCase("trademark")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("trademarkCommand");
			}
			else if(formName.equalsIgnoreCase("branch")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("branchCommand");
			}
			else if(formName.equalsIgnoreCase("movementConcept")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("movementConceptCommand");
			}
			else if(formName.equalsIgnoreCase("warehouse")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("warehouseCommand");
			}
			else{
				throw new RuntimeException("form name not defined for: '"+formName+"'");
			}
		}
		return command;
	}	

}
