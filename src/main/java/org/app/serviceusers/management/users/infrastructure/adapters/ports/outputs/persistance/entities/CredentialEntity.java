package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "credentials")
public class CredentialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "credential", fetch = FetchType.LAZY)
    private UserEntity user;

}