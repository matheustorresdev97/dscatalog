package com.matheustorres.dscatalog.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.matheustorres.dscatalog.entities.Category;
import com.matheustorres.dscatalog.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductDTO(
        Long id,

        @Size(min = 5, max = 60, message = "Deve ter entre 5 e 60 caracteres") @NotBlank(message = "Campo obrigatório!") String name,

        @NotBlank(message = "Campo obrigatório!") String description,

        @Positive(message = "O valor precisa ser positivo") Double price,
        String imgUrl,

        @PastOrPresent(message = "A data do produto não pode ser futura!") Instant date,
        List<CategoryDTO> categories) {

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
                new ArrayList<>());
    }

    public ProductDTO(Product entity, Set<Category> categoriesList) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImgUrl(),
                entity.getDate(),
                categoriesList.stream().map(cat -> new CategoryDTO(cat)).toList());
    }
}