package com.matheustorres.dscatalog.services;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.matheustorres.dscatalog.dto.ProductDTO;
import com.matheustorres.dscatalog.repositories.ProductRepository;
import com.matheustorres.dscatalog.services.exceptions.ResourceNotFoundException;



@SpringBootTest
@Transactional
class ProductServiceIT {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setup() {
        existingId        = 1L;
        nonExistingId     = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    @DisplayName("delete deve remover recurso quando id existe")
    void deleteShouldDeleteResourceWhenIdExists() {
        service.delete(existingId);

        assertThat(repository.count()).isEqualTo(countTotalProducts - 1);
    }

    @Test
    @DisplayName("delete deve lançar ResourceNotFoundException quando id não existe")
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThatThrownBy(() -> service.delete(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test 
    @DisplayName("findAllPaged deve retornar página quando página 0 tamanho 10")
    void findAllPagedShouldReturnPageWhenPage0Size10() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(pageRequest);

        assertThat(result).isNotEmpty();
        assertThat(result.getNumber()).isZero();
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getTotalElements()).isEqualTo(countTotalProducts);
    }

    @Test
    @DisplayName("findAllPaged deve retornar página vazia quando página não existe")
    void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {
        PageRequest pageRequest = PageRequest.of(50, 10);

        Page<ProductDTO> result = service.findAllPaged(pageRequest);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findAllPaged deve retornar página ordenada quando ordenado por nome")
    void findAllPagedShouldReturnSortedPageWhenSortByName() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

        Page<ProductDTO> result = service.findAllPaged(pageRequest);

        List<String> names = result.getContent()
                .stream()
                .map(ProductDTO::name)
                .toList();

        assertThat(result).isNotEmpty();
        assertThat(names).isSortedAccordingTo(String::compareTo); // ✅ robusto
    }
}
