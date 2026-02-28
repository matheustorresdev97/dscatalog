package com.matheustorres.dscatalog.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.matheustorres.dscatalog.entities.Product;
import com.matheustorres.dscatalog.tests.Factory;

@DataJpaTest
class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;

    @BeforeEach
    void setup() {
        existingId = 1L;
        nonExistingId = 1000L;
    }

    @Test
    @DisplayName("delete deve remover objeto quando id existe")
    void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingId);

        assertThat(repository.findById(existingId)).isEmpty();
    }

    @Test
    @DisplayName("save deve persistir com id gerado quando id é nulo")
    void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Product product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);

        assertThat(product.getId()).isNotNull();
        assertThat(product.getId()).isPositive();
    }

    @Test
    @DisplayName("findById deve retornar Optional não vazio quando id existe")
    void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        assertThat(repository.findById(existingId)).isPresent();
    }

    @Test
    @DisplayName("findById deve retornar Optional vazio quando id não existe")
    void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        assertThat(repository.findById(nonExistingId)).isEmpty();
    }
}