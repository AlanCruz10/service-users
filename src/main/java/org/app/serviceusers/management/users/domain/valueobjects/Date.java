package org.app.serviceusers.management.users.domain.valueobjects;

import lombok.Getter;
import lombok.Setter;
import org.app.serviceusers.management.users.domain.models.UserProfile;

import java.time.LocalDateTime;

@Getter
@Setter
public class Date {

    private String uuid;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private UserProfile userProfile;

}