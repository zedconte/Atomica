package com.industrika.commons.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class TextFormatter {
	public static String formatWeb(String text){
    	if (!StringUtils.isEmpty(text)){
    		return StringEscapeUtils.escapeHtml4(text);
    	} else {
    		return "";
    	}
    }

	/**
     * Format the string to be prepared to show it in a web page from javascript, reemplazing the special characters
     * for the correct code for javascript
     * @param text Text to be formated
     * @return Text formated to be showed in a web page calling from javascript
     */
    public static String formatJS(String text){
    	if (!StringUtils.isEmpty(text)){
	        if (text.indexOf("Á")>-1){
	            text=text.replaceAll("Á", "\\\\xC1");
	        }
	        if (text.indexOf("É")>-1){
	            text=text.replaceAll("É", "\\\\xC9");
	        }
	        if (text.indexOf("Í")>-1){
	            text=text.replaceAll("Í", "\\\\xCD");
	        }
	        if (text.indexOf("Ó")>-1){
	            text=text.replaceAll("Ó", "\\\\xD3");
	        }
	        if (text.indexOf("Ú")>-1){
	            text=text.replaceAll("Ú", "\\\\\\xDA");
	        }
	        if (text.indexOf("À")>-1){
	            text=text.replaceAll("À", "\\\\xC0");
	        }
	        if (text.indexOf("È")>-1){
	            text=text.replaceAll("È", "\\\\xC8");
	        }
	        if (text.indexOf("Ì")>-1){
	            text=text.replaceAll("Ì", "\\\\xCC");
	        }
	        if (text.indexOf("Ò")>-1){
	            text=text.replaceAll("Ò", "\\\\xD2");
	        }
	        if (text.indexOf("Ù")>-1){
	            text=text.replaceAll("Ù", "\\\\xD9");
	        }
	        if (text.indexOf("Â")>-1){
	            text=text.replaceAll("Â", "\\\\xC2");
	        }
	        if (text.indexOf("Ê")>-1){
	            text=text.replaceAll("Ê", "\\\\xCA");
	        }
	        if (text.indexOf("Î")>-1){
	            text=text.replaceAll("Î", "\\\\xCE");
	        }
	        if (text.indexOf("Ô")>-1){
	            text=text.replaceAll("Ô", "\\\\xD4");
	        }
	        if (text.indexOf("Û")>-1){
	            text=text.replaceAll("Û", "\\\\xDB");
	        }
	        if (text.indexOf("Ä")>-1){
	            text=text.replaceAll("Ä", "\\\\xC4");
	        }
	        if (text.indexOf("Ë")>-1){
	            text=text.replaceAll("Ë", "\\\\xCB");
	        }
	        if (text.indexOf("Ï")>-1){
	            text=text.replaceAll("Ï", "\\\\xCF");
	        }
	        if (text.indexOf("Ö")>-1){
	            text=text.replaceAll("Ö", "\\\\xD6");
	        }
	        if (text.indexOf("Ü")>-1){
	            text=text.replaceAll("Ü", "\\\\xDC");
	        }
	        if (text.indexOf("Ñ")>-1){
	            text=text.replaceAll("Ñ", "\\\\xD1");
	        }
	        if (text.indexOf("á")>-1){
	            text=text.replaceAll("á", "\\\\xE1");
	        }
	        if (text.indexOf("é")>-1){
	            text=text.replaceAll("é", "\\\\xE9");
	        }
	        if (text.indexOf("í")>-1){
	            text=text.replaceAll("í", "\\\\xED");
	        }
	        if (text.indexOf("ó")>-1){
	            text=text.replaceAll("ó", "\\\\xF3");
	        }
	        if (text.indexOf("ú")>-1){
	            text=text.replaceAll("ú", "\\\\xFA");
	        }
	        if (text.indexOf("à")>-1){
	            text=text.replaceAll("à", "\\\\xE0");
	        }
	        if (text.indexOf("è")>-1){
	            text=text.replaceAll("è", "\\\\xE8");
	        }
	        if (text.indexOf("ì")>-1){
	            text=text.replaceAll("ì", "\\\\xEC");
	        }
	        if (text.indexOf("ò")>-1){
	            text=text.replaceAll("ò", "\\\\xF2");
	        }
	        if (text.indexOf("ù")>-1){
	            text=text.replaceAll("ù", "\\\\xF9");
	        }
	        if (text.indexOf("â")>-1){
	            text=text.replaceAll("â", "\\\\xE2");
	        }
	        if (text.indexOf("ê")>-1){
	            text=text.replaceAll("ê", "\\\\xEA");
	        }
	        if (text.indexOf("î")>-1){
	            text=text.replaceAll("î", "\\\\xEE");
	        }
	        if (text.indexOf("ô")>-1){
	            text=text.replaceAll("ô", "\\\\xF4");
	        }
	        if (text.indexOf("û")>-1){
	            text=text.replaceAll("û", "\\\\xFB");
	        }
	        if (text.indexOf("ä")>-1){
	            text=text.replaceAll("ä", "\\\\xE3");
	        }
	        if (text.indexOf("ë")>-1){
	            text=text.replaceAll("ë", "\\\\xEB");
	        }
	        if (text.indexOf("ï")>-1){
	            text=text.replaceAll("ï", "\\\\xEF");
	        }
	        if (text.indexOf("ö")>-1){
	            text=text.replaceAll("ö", "\\\\xF6");
	        }
	        if (text.indexOf("ü")>-1){
	            text=text.replaceAll("ü", "\\\\xFC");
	        }
	        if (text.indexOf("ñ")>-1){
	            text=text.replaceAll("ñ", "\\\\xF1");
	        }
	        if (text.indexOf("ã")>-1){
	            text=text.replaceAll("ã", "\\\\xE3");
	        }
	        if (text.indexOf("õ")>-1){
	            text=text.replaceAll("õ", "\\\\xD5");
	        }
	        if (text.indexOf("¡")>-1){
	            text=text.replaceAll("¡", "\\\\xA1");
	        }
	        if (text.indexOf("¿")>-1){
	            text=text.replaceAll("¿", "\\\\xBF");
	        }
	        if (text.indexOf("Ç")>-1){
	            text=text.replaceAll("Ç", "\\\\xC7");
	        }
	        if (text.indexOf("ç")>-1){
	            text=text.replaceAll("ç", "\\\\xE7");
	        }
    	} else {
    		text = "";
    	}
        return text;
    }

    public static String formatFromJS(String text){
    	if (!StringUtils.isEmpty(text)){
	        if (text.indexOf("\\xC1")>-1){
	            text=text.replaceAll("\\xC1", "Á");
	        }
	        if (text.indexOf("\\xC9")>-1){
	            text=text.replaceAll("\\xC9", "É");
	        }
	        if (text.indexOf("\\xCD")>-1){
	            text=text.replaceAll("\\xCD", "Í");
	        }
	        if (text.indexOf("\\xD3")>-1){
	            text=text.replaceAll("\\xD3", "Ó");
	        }
	        if (text.indexOf("\\xDA")>-1){
	            text=text.replaceAll("\\xDA", "Ú");
	        }
	        if (text.indexOf("\\xC0")>-1){
	            text=text.replaceAll("\\xC0", "À");
	        }
	        if (text.indexOf("\\xC8")>-1){
	            text=text.replaceAll("\\xC8", "È");
	        }
	        if (text.indexOf("\\xCC")>-1){
	            text=text.replaceAll("\\xCC", "Ì");
	        }
	        if (text.indexOf("\\xD2")>-1){
	            text=text.replaceAll("\\xD2", "Ò");
	        }
	        if (text.indexOf("\\xD9")>-1){
	            text=text.replaceAll("\\xD9", "Ù");
	        }
	        if (text.indexOf("\\xD1")>-1){
	            text=text.replaceAll("\\xD1", "Ñ");
	        }
	        if (text.indexOf("\\xE1")>-1){
	            text=text.replaceAll("\\xE1", "á");
	        }
	        if (text.indexOf("\\xE9")>-1){
	            text=text.replaceAll("\\xE9", "é");
	        }
	        if (text.indexOf("\\xED")>-1){
	            text=text.replaceAll("\\xED", "í");
	        }
	        if (text.indexOf("\\xF3")>-1){
	            text=text.replaceAll("\\xF3", "ó");
	        }
	        if (text.indexOf("\\xFA")>-1){
	            text=text.replaceAll("\\xFA", "ú");
	        }
	        if (text.indexOf("\\xF1")>-1){
	            text=text.replaceAll("\\xF1", "ñ");
	        }
    	} else {
    		text = "";
    	}
        return text;
    }

    private static NumberFormat getCurrencyFormat(String language, String country){
    	if (StringUtils.isEmpty(language)){
    		language = "es";
    	}
    	if (StringUtils.isEmpty(country)){
    		country = "MX";
    	}
    	Locale locale = new Locale(language, country);
		NumberFormat myCurrency = NumberFormat.getCurrencyInstance(locale);
		myCurrency.setMaximumFractionDigits(2);
		myCurrency.setMinimumFractionDigits(2);
		return myCurrency;
    }

    private static NumberFormat getNumberFormat(String language, String country){
    	if (StringUtils.isEmpty(language)){
    		language = "es";
    	}
    	if (StringUtils.isEmpty(country)){
    		country = "MX";
    	}
    	Locale locale = new Locale(language, country);
		NumberFormat myCurrency = NumberFormat.getNumberInstance(locale);
		myCurrency.setMaximumFractionDigits(2);
		myCurrency.setMinimumFractionDigits(2);
		return myCurrency;
    }
    
    public static String getPercentage(Double amount, Boolean divide){
    	Double number=amount;
    	if(divide){
    		number=amount/100;
    	}
		NumberFormat formatter = NumberFormat.getPercentInstance();
		formatter.setMinimumFractionDigits(1);
		formatter.setMaximumFractionDigits(2);
		
		return formatter.format(number);
    }

    public static String getCurrencyValue(String language, String country, Double value){
		return getCurrencyFormat(language,country).format(value.doubleValue());
    }

    public static String getCurrencyValue(Double value){
    	if (value != null)
    		return getCurrencyFormat(null,null).format(value.doubleValue());
    	else return "";
    }

    public static Number getCurrency(String language, String country, String value) throws ParseException{
		return getCurrencyFormat(language,country).parse(value);
    }

    public static Number getCurrency(String value) throws ParseException{
		return getCurrencyFormat(null,null).parse(value);
    }

    public static Number getNumber(String value) throws ParseException{
		return getNumberFormat(null,null).parse(value);
    }

    public static String getNumberValue(Double value){
    	if (value != null)
    		return getNumberFormat(null,null).format(value.doubleValue());
    	else return "";
    }
    
    public static String getFormattedDate(Calendar calendar,  String format){
    	String myFormat=format;
    	if (format==null || StringUtils.isEmpty(format)){
    		myFormat="dd/MM/yyyy";
    	}
    	Calendar myCalendar= Calendar.getInstance();
    	if(calendar!=null){
    		myCalendar=calendar;
    	}
    	Date date = myCalendar.getTime();             
    	SimpleDateFormat formatter = new SimpleDateFormat(myFormat);
    	return formatter.format(date);            
 
    }

}
