package com.matheustorres.dscatalog.dto;

import java.util.Set;

public record CreateUserDTO(Long id,
        String firstName,
        String lastName,
        String email,
        Set<RoleDTO> roles,
        String password) implements UserView {
}
