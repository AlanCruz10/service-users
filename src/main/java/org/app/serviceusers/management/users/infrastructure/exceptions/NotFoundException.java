package org.app.serviceusers.management.users.infrastructure.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String email){super("User " + email + " Not Found");}

}