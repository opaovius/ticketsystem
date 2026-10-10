package com.chris.ticket.Exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException{
	
	public EmailAlreadyRegisteredException(String message) {
        super(message);
    }

}
