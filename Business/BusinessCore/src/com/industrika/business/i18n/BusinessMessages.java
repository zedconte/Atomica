package com.industrika.business.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class BusinessMessages {

	private static ResourceBundle messages;
	
	public static String getMessage(String key){
		if (messages == null){
			messages = ResourceBundle.getBundle("com/industrika/business/i18n/businessmessages", Locale.getDefault());
		}
		return messages.getString(key);
	}
}
