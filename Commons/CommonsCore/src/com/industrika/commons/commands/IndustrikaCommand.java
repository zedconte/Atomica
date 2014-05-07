package com.industrika.commons.commands;

import java.util.HashMap;
import java.util.Map;

public interface IndustrikaCommand {

	/**
	 * Execute the actions of the command 
	 * @param parameters Map with the values, regarding the information values must have a 
	 * key named "returnType" and the value must determine the expected return type, each 
	 * commands need to know how deal with each type
	 * @return The returned HashMap must have the following keys and the corresponding values
	 * 		key = "form"   		value = path and name of the file to be returned
	 * 		key = "message"		value = message for notifications if apply
	 * 		key = "error"		value = message for error notifications if apply
	 * 		key = "dto"			value = corresponding dto to be read on the file to be returned
	 * 		key = "list"		value = list of the results on queries if apply
	 */
	HashMap<String, Object> execute(Map<String, String[]> parameters);
		
}
