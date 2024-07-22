package org.app.serviceusers.management.users.application.mappers;

import org.app.serviceusers.management.users.application.dtos.requests.CreateUserRequest;
import org.app.serviceusers.management.users.application.dtos.responses.UserResponse;
import org.app.serviceusers.management.users.domain.models.Access;
import org.app.serviceusers.management.users.domain.models.Credential;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    private final CredentialMapper credentialMapper;
    private final UserProfileMapper userProfileMapper;

    public UserMapper(CredentialMapper credentialMapper, UserProfileMapper userProfileMapper) {
        this.credentialMapper = credentialMapper;
        this.userProfileMapper = userProfileMapper;
    }

    public UserResponse toDto(User user) {
        return UserResponse.builder()
                .uuid(user.getUserProfile().getUuid())
                .city(user.getUserProfile().getCity())
                .email(user.getCredential().getEmail())
                .username(user.getUserProfile().getUsername())
                .profilePicture(user.getUserProfile().getProfilePicture())
                .build();
    }

    public User toDomain(CreateUserRequest request) {
        User user = new User();
        user.setFirstSignIn(null);

        Credential credential = credentialMapper.toDomain(credentialMapper.toDto(request.getEmail(), request.getPassword()));
        UserProfile userProfile = userProfileMapper.toDomain(userProfileMapper.toDto(request.getUsername(), request.getCity(), null, null));

//        List<Access> accesses = new ArrayList<>();
        user.setCredential(credential);
        user.setUserProfile(userProfile);
//        user.setAccesses(accesses);
        userProfile.setUser(user);
        credential.setUser(user);
        return user;
    }

}