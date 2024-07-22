package org.app.serviceusers.management.users.infrastructure.configurations.security.user;

import lombok.Data;

@Data
public class UserAuth {

    private String email;
    private String password;

}