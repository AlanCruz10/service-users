package org.app.serviceusers.management.users.application.mappers;

import org.app.serviceusers.management.users.application.dtos.requests.CreateDateRequest;
import org.app.serviceusers.management.users.domain.valueobjects.Date;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateMapper {

    public Date toDomain(CreateDateRequest request) {
        Date date = new Date();
        date.setCreatedAt(request.getCreatedAt());
        date.setUpdatedAt(request.getUpdatedAt());
        date.setDeletedAt(request.getDeletedAt());
        return date;
    }

    public CreateDateRequest toDto(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        CreateDateRequest date = new CreateDateRequest();
        date.setCreatedAt(createdAt);
        date.setUpdatedAt(updatedAt);
        date.setDeletedAt(deletedAt);
        return date;
    }

}