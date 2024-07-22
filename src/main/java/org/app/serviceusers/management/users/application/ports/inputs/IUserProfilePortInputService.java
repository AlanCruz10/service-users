package org.app.serviceusers.management.users.application.ports.inputs;

import org.app.serviceusers.management.users.domain.models.UserProfile;
import org.app.serviceusers.management.users.domain.repositories.IUserProfileRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

public interface IUserProfilePortInputService {

    String uploadProfilePicture(UserProfile email, MultipartFile profilePicture);

}