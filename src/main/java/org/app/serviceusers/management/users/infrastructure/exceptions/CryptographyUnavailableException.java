package org.app.serviceusers.management.users.infrastructure.exceptions;

public class CryptographyUnavailableException extends RuntimeException {

    public CryptographyUnavailableException(String message) {
        super(message);
    }

    public CryptographyUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

}