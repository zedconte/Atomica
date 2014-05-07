package com.industrika.humanresources.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class HRMessages {
	private static ResourceBundle messages;
	
	public static final String ERROR_NOT_EMPTY = "error_not_empty"; 
	
	public static String getMessage(String key){
		if (messages == null){
			messages = ResourceBundle.getBundle("com/industrika/humanresources/i18n/humanresourcesmessages", Locale.getDefault());
		}
		return messages.getString(key);
	}
}
