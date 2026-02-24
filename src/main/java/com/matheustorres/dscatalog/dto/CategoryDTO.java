package com.matheustorres.dscatalog.dto;

import com.matheustorres.dscatalog.entities.Category;

public record CategoryDTO(Long id, String name) {
    public CategoryDTO(Category entity) {
        this(entity.getId(), entity.getName());
    }
}
