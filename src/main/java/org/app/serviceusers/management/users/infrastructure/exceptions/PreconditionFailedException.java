package org.app.serviceusers.management.users.infrastructure.exceptions;

public class PreconditionFailedException extends RuntimeException {

    public PreconditionFailedException(String message) {
        super(message);
    }

}