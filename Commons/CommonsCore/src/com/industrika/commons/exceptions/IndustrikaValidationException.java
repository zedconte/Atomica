package com.industrika.commons.exceptions;

public class IndustrikaValidationException extends Exception {

	private static final long serialVersionUID = -8880469747949516899L;

	public IndustrikaValidationException(){
		super();
	}

	public IndustrikaValidationException(String message){
		super(message);
	}

	public IndustrikaValidationException(Throwable thr){
		super(thr);
	}

	public IndustrikaValidationException(String message, Throwable thr){
		super(message, thr);
	}

	public IndustrikaValidationException(String message, Throwable thr, boolean enableSuppresion, boolean writableStack){
		super(message, thr/*, enableSuppresion, writableStack*/);
	}

}
