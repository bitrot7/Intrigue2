package com.mk.intrigue.exception;


public class ComponentMissingException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComponentMissingException(String compname1, String compname2, int EntityId) {
        super("Component " + compname1 +  ", attempted operation on Component " + 
        		compname2 + " but it did not exist in entity: " + EntityId);
    }

}
