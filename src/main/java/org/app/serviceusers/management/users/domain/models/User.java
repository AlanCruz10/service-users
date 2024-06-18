package org.app.serviceusers.management.users.domain.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String uuid;

    private String username;

    private String email;

    private String password;

}