package org.app.serviceusers.management.users.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {

    private String uuid;

    private Credential credential;

    private UserProfile userProfile;

    private LocalDateTime firstSignIn;

    private List<Access> accesses = new ArrayList<>();

}