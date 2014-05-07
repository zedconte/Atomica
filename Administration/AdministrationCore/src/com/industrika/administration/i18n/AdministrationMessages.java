package com.industrika.administration.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class AdministrationMessages {

	private static ResourceBundle messages;
	
	public static String getMessage(String key){
		if (messages == null){
			messages = ResourceBundle.getBundle("com/industrika/administration/i18n/administrationmessages", Locale.getDefault());
		}
		return messages.getString(key);
	}
}
