package org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "accesses")
public class AccessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    private String uuid;

    @Column
    private LocalDateTime lastLogIn;

    @Column
    private LocalDateTime lastLogOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", nullable = false, referencedColumnName = "uuid")
    private UserEntity user;

}