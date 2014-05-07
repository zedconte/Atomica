package com.industrika.maintenance.i18n;

import java.util.ResourceBundle;

public class MaintenanceMessages {
	
		private static ResourceBundle messages;
	
		static {
			messages = ResourceBundle.getBundle("com/industrika/maintenance/i18n/maintenance_messages");
		}
				
		public static String getMessage(String key){
			return messages.getString(key);
		}
	
}
