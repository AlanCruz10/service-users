package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    private String uuid;

    @Column
    private LocalDateTime firstSignIn;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AccessEntity> accesses = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_uuid", nullable = false, referencedColumnName = "uuid")
    private UserProfileEntity userProfile;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "credential_uuid", nullable = false, unique = true, referencedColumnName = "uuid")
    private CredentialEntity credential;

}