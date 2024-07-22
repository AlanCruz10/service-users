package org.app.serviceusers.management.users.domain.models;

import lombok.Getter;
import lombok.Setter;
import org.app.serviceusers.management.users.domain.valueobjects.Date;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserProfile {

    private String uuid;

    private String username;

    private LocalDate birthDate;

    private String city;

    private String profilePicture;

    private Date date;

    private User user;

}