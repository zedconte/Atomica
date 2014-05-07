package com.industrika.commons.commands;


/**
 * All the modules need to has a commands factory and must implement this interface
 * @author roberto.portilla
 *
 */
public interface IndustrikaCommandsFactory {

	public IndustrikaCommand getCommand(String formName);
	
}
