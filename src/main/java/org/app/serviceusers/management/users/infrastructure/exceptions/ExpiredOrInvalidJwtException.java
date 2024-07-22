package org.app.serviceusers.management.users.infrastructure.exceptions;

public class ExpiredOrInvalidJwtException extends RuntimeException {

    public ExpiredOrInvalidJwtException(final String message) {
        super(message);
    }

}