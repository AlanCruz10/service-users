package org.app.serviceusers.management.users.application.mappers;

import org.app.serviceusers.management.users.application.dtos.requests.CreateCredentialRequest;
import org.app.serviceusers.management.users.domain.models.Credential;
import org.springframework.stereotype.Component;

@Component
public class CredentialMapper {

    public Credential toDomain(CreateCredentialRequest request) {
        Credential credential = new Credential();
        credential.setEmail(request.getEmail());
        credential.setPassword(request.getPassword());
        return credential;
    }

    public CreateCredentialRequest toDto(String email, String password) {
        CreateCredentialRequest request = new CreateCredentialRequest();
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }

}