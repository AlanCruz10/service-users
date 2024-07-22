package org.app.serviceusers.management.users.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Access {

    private String uuid;

    private LocalDateTime lastLogIn;

    private LocalDateTime lastLogOut;

    private User user;

}