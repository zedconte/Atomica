package com.industrika.commons.exceptions;

public class IndustrikaPersistenceException extends Exception {

	private static final long serialVersionUID = -6628553392342479553L;

	public IndustrikaPersistenceException(){
		super();
	}

	public IndustrikaPersistenceException(String message){
		super(message);
	}

	public IndustrikaPersistenceException(Throwable thr){
		super(thr);
	}

	public IndustrikaPersistenceException(String message, Throwable thr){
		super(message, thr);
	}

	public IndustrikaPersistenceException(String message, Throwable thr, boolean enableSuppresion, boolean writableStack){
		super(message, thr/*, enableSuppresion, writableStack*/);
	}

}
