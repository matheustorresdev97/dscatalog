package com.matheustorres.dscatalog.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.matheustorres.dscatalog.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        Long id,

        @NotBlank(message = "Campo obrigatório!") String firstName,
        String lastName,
        @Email(message = "Favor entrar com um email válido!") String email,
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