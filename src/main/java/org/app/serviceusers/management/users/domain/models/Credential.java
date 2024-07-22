package org.app.serviceusers.management.users.domain.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credential {

    private String uuid;

    private String email;

    private String password;

    private User user;

}