package org.app.serviceusers.management.users.application.ports.inputs;

public interface IHash256Encoder {

    byte[] encode(String input);

    String encodeToString(String input);

    boolean matches(String input, String hash);

}