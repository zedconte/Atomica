package com.industrika.commons.exceptions;

public class IndustrikaObjectNotFoundException extends Exception {

	private static final long serialVersionUID = 1655018998450050463L;

	public IndustrikaObjectNotFoundException(){
		super();
	}

	public IndustrikaObjectNotFoundException(String message){
		super(message);
	}

	public IndustrikaObjectNotFoundException(Throwable thr){
		super(thr);
	}

	public IndustrikaObjectNotFoundException(String message, Throwable thr){
		super(message, thr);
	}

	public IndustrikaObjectNotFoundException(String message, Throwable thr, boolean enableSuppresion, boolean writableStack){
		super(message, thr/*, enableSuppresion, writableStack*/);
	}

}
