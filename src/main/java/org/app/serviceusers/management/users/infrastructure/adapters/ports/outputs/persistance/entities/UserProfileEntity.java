package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.app.serviceusers.management.users.domain.models.User;
import org.app.serviceusers.management.users.domain.valueobjects.Date;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_profiles")
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false, length = 100)
    private String username;

    @Column
    private LocalDate birthDate;

    @Column(nullable = false)
    private String city;

    @Column(length = 2000)
    private String profilePicture;

    @OneToOne(mappedBy = "userProfile", fetch = FetchType.LAZY)
    private UserEntity user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "date_uuid", nullable = false, referencedColumnName = "uuid")
    private DateEntity date;

}