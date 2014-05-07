package com.industrika.commons.exceptions;

public class IndustrikaAlreadyExistException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8300065975776051922L;

	public IndustrikaAlreadyExistException(){
		super();
	}

	public IndustrikaAlreadyExistException(String message){
		super(message);
	}

	public IndustrikaAlreadyExistException(Throwable thr){
		super(thr);
	}

	public IndustrikaAlreadyExistException(String message, Throwable thr){
		super(message, thr);
	}

	public IndustrikaAlreadyExistException(String message, Throwable thr, boolean enableSuppresion, boolean writableStack){
		super(message, thr, enableSuppresion, writableStack);
	}
}
