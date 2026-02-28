package com.matheustorres.dscatalog.dto;

import com.matheustorres.dscatalog.entities.Role;

public record RoleDTO(Long id, String authority) {

    public RoleDTO(Role entity) {
        this(entity.getId(), entity.getAuthority());
    }
}
