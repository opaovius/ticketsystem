package com.chris.ticket.Exceptions;

public class ChangePasswordMismatchException extends RuntimeException{

	public ChangePasswordMismatchException(String message) {
		super(message);
	}
}
