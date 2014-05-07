package com.industrika.humanresources.commands;

import com.industrika.commons.businesslogic.ApplicationContextProvider;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.commands.IndustrikaCommandsFactory;

public class HumaResourcesCommandsFactory implements IndustrikaCommandsFactory {

	@Override
	public IndustrikaCommand getCommand(String formName) {
		IndustrikaCommand command = null;
		if (formName != null){
			if (formName.equalsIgnoreCase("direction")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("directionCommand");
			}
			else if (formName.equalsIgnoreCase("management")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("managementCommand");
			}
			else if (formName.equalsIgnoreCase("department")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("departmentCommand");
			}
			else if (formName.equalsIgnoreCase("position")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("positionCommand");
			}
			else if (formName.equalsIgnoreCase("shift")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("shiftCommand");
			}
			else if (formName.equalsIgnoreCase("employee")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("employeeCommand");
			}
			else if (formName.equalsIgnoreCase("absence")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("absenceCommand");
			}
			else if (formName.equalsIgnoreCase("payroll")){
				command = (IndustrikaCommand) ApplicationContextProvider.getCtx().getBean("payrollCommand");
			}
			else{
				throw new RuntimeException("form name not defined");
			}
		}
		return command;
	}	
}