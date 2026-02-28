package com.matheustorres.dscatalog.dto;

import java.util.Set;

public interface UserView {
    Long id();

    String firstName();

    String lastName();

    String email();

    Set<RoleDTO> roles();
}
