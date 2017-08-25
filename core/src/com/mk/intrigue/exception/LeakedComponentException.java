package com.mk.intrigue.exception;

public class LeakedComponentException extends RuntimeException {
	public LeakedComponentException(String err) {
        super("Component Leaked: " + err);
    }
}
