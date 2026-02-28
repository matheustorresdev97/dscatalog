package com.matheustorres.dscatalog.services;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.matheustorres.dscatalog.dto.ProductDTO;
import com.matheustorres.dscatalog.entities.Product;
import com.matheustorres.dscatalog.repositories.ProductRepository;
import com.matheustorres.dscatalog.services.exceptions.DatabaseException;
import com.matheustorres.dscatalog.services.exceptions.ResourceNotFoundException;
import com.matheustorres.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product;

    @BeforeEach
    void setup() {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));

        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        when(repository.save(any())).thenReturn(product);
        when(repository.findById(existingId)).thenReturn(Optional.of(product));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        when(repository.existsById(existingId)).thenReturn(true);
        when(repository.existsById(nonExistingId)).thenReturn(false);
        when(repository.existsById(dependentId)).thenReturn(true);

        doNothing().when(repository).deleteById(existingId);
        doNothing().when(repository).deleteById(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    @DisplayName("delete não deve lançar exceção quando id existe")
    void deleteShouldDoNothingWhenIdExists() {
        assertThatCode(() -> service.delete(existingId))
                .doesNotThrowAnyException();

        verify(repository, times(1)).deleteById(existingId);
    }

    @Test
    @DisplayName("delete deve lançar DatabaseException quando id tem dependência")
    void deleteShouldThrowDatabaseExceptionWhenIdIsDependent() {
        assertThatThrownBy(() -> service.delete(dependentId))
                .isInstanceOf(DatabaseException.class);

        verify(repository, times(1)).deleteById(dependentId);
    }

    @Test
    @DisplayName("findAllPaged deve retornar página de produtos")
    void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(pageable);

        assertThat(result).isNotNull();
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("findById deve retornar ProductDTO quando id existe")
    void findByIdShouldReturnProductDTOWhenIdExists() {
        ProductDTO result = service.findById(existingId);

        assertThat(result).isNotNull();
        verify(repository).findById(existingId);
    }

    @Test
    @DisplayName("findById deve lançar ResourceNotFoundException quando id não existe")
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThatThrownBy(() -> service.findById(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository).findById(nonExistingId);
    }
}
