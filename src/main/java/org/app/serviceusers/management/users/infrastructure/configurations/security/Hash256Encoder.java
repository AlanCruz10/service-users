package org.app.serviceusers.management.users.infrastructure.configurations.security;

import org.app.serviceusers.management.users.application.ports.inputs.IHash256Encoder;
import org.app.serviceusers.management.users.infrastructure.exceptions.CryptographyUnavailableException;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Hash256Encoder implements IHash256Encoder {

    private static final String ALGORITHM = "SHA-256";

    @Override
    public byte[] encode(String input) {
        try {
            return MessageDigest.getInstance(ALGORITHM).digest(input.getBytes());
        }catch (NoSuchAlgorithmException e) {
            throw new CryptographyUnavailableException("Algorithm " + ALGORITHM + " not found", e);
        }
    }

    @Override
    public String encodeToString(String input) {
        byte[] hashBytes = encode(input);
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @Override
    public boolean matches(String input, String hash) {
        return encodeToString(input).equals(hash);
    }

}