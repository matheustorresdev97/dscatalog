package com.matheustorres.dscatalog.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.matheustorres.dscatalog.entities.User;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        Set<RoleDTO> roles) implements UserView {
    public UserDTO {
        if (roles == null) {
            roles = java.util.Collections.emptySet();
        }
    }

    public UserDTO(User entity) {
        this(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getRoles().stream()
                        .map(RoleDTO::new)
                        .collect(Collectors.toSet()));
    }
}