package com.mk.intrigue.exception;

import com.mk.intrigue.entity.Entity;

public class ComponentMissingException extends RuntimeException {
	public ComponentMissingException(String classname, String systemname, int guid) {
        super("Error Game Object guid =  " + guid + ", has no " + classname 
        		+ " required by " + systemname );
    }

}
