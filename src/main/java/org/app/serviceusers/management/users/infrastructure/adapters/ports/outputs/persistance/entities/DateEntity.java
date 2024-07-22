package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.app.serviceusers.management.users.domain.models.UserProfile;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "dates")
public class DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "date", fetch = FetchType.LAZY)
    private UserProfileEntity userProfile;

}