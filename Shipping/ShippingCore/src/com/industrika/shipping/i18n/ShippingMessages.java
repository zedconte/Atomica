package com.industrika.shipping.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class ShippingMessages {
private static ResourceBundle messages;
	
	public static String getMessage(String key){
		if (messages == null){
			messages = ResourceBundle.getBundle("com/industrika/shipping/i18n/shippingmessages", Locale.getDefault());
		}
		return messages.getString(key);
	}
}
