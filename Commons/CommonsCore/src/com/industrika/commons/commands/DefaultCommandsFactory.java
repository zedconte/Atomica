package com.industrika.commons.commands;

import org.apache.commons.lang.StringUtils;


public class DefaultCommandsFactory extends AbstractCommandFactory {

	@Override
	public IndustrikaCommand getCommand(String formName) {
		
		if (StringUtils.isBlank(formName)) throw new IllegalArgumentException("El nombre de la forma no puede estar vacÃ­o");
		
		return getcommand(formName.concat("Command"));
	}

}
