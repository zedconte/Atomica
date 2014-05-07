package com.industrika.productplanning.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class ProductPlanningMessages {

	private static ResourceBundle messages;
	
	public static final String ERROR_NOT_EMPTY = "error_not_empty"; 
	
	public static String getMessage(String key){
		if (messages == null){
			messages = ResourceBundle.getBundle("com/industrika/productplanning/i18n/productplanningmessages", Locale.getDefault());
		}
		return messages.getString(key);
	}
}
