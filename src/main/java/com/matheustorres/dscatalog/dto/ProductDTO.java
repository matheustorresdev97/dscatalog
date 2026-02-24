package com.matheustorres.dscatalog.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.matheustorres.dscatalog.entities.Category;
import com.matheustorres.dscatalog.entities.Product;

public record ProductDTO(
    Long id, 
    String name, 
    String description, 
    Double price, 
    String imgUrl, 
    Instant date,
    List<CategoryDTO> categories
) {

    public ProductDTO {
        if (categories == null) {
            categories = new ArrayList<>();
        }
    }

    public ProductDTO(Product entity) {
        this(
            entity.getId(), 
            entity.getName(), 
            entity.getDescription(), 
            entity.getPrice(), 
            entity.getImgUrl(), 
            entity.getDate(),
            new ArrayList<>()
        );
    }

    public ProductDTO(Product entity, Set<Category> categoriesList) {
        this(
            entity.getId(), 
            entity.getName(), 
            entity.getDescription(), 
            entity.getPrice(), 
            entity.getImgUrl(), 
            entity.getDate(),
            categoriesList.stream().map(cat -> new CategoryDTO(cat)).toList()
        );
    }
}