package org.app.serviceusers.management.users.application.mappers;

import org.app.serviceusers.management.users.application.dtos.requests.CreateUserProfileRequest;
import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.domain.valueobjects.Date;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class UserProfileMapper {

    private final DateMapper dateMapper;

    public UserProfileMapper(DateMapper dateMapper) {
        this.dateMapper = dateMapper;
    }

    public UserProfile toDomain(CreateUserProfileRequest request) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(request.getUsername());
        userProfile.setCity(request.getCity());
        userProfile.setProfilePicture(request.getProfilePicture());
        userProfile.setBirthDate(request.getBirthDate());

        Date date = dateMapper.toDomain(dateMapper.toDto(LocalDateTime.now(), null, null));
        userProfile.setDate(date);
        date.setUserProfile(userProfile);

        return userProfile;
    }

    public CreateUserProfileRequest toDto(String username, String city, String profilePicture, LocalDate birthDate) {
        CreateUserProfileRequest request = new CreateUserProfileRequest();
        request.setUsername(username);
        request.setCity(city);
        request.setProfilePicture(profilePicture);
        request.setBirthDate(birthDate);
        return request;
    }

}