package com.mk.intrigue.exception;


public class ComponentMissingException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComponentMissingException(String classname, String systemname, int guid) {
        super("Error Game Object guid =  " + guid + ", has no " + classname 
        		+ " required by " + systemname );
    }

}
