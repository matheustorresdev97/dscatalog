package com.matheustorres.dscatalog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.matheustorres.dscatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT DISTINCT p.* FROM tb_product p "
            + "INNER JOIN tb_product_category pc ON p.id = pc.product_id "
            + "INNER JOIN tb_category c ON c.id = pc.category_id "
            + "WHERE (:categoryId IS NULL OR c.id = :categoryId) "
            + "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))", countQuery = "SELECT COUNT(DISTINCT p.id) FROM tb_product p "
                    + "INNER JOIN tb_product_category pc ON p.id = pc.product_id "
                    + "INNER JOIN tb_category c ON c.id = pc.category_id "
                    + "WHERE (:categoryId IS NULL OR c.id = :categoryId) "
                    + "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))", nativeQuery = true)
    Page<Product> find(@Param("categoryId") Long categoryId,
            @Param("name") String name,
            Pageable pageable);

    @Query(value = "SELECT p.*, c.id as category_id, c.name as category_name "
            + "FROM tb_product p "
            + "INNER JOIN tb_product_category pc ON p.id = pc.product_id "
            + "INNER JOIN tb_category c ON c.id = pc.category_id "
            + "WHERE p.id IN (:productIds)", nativeQuery = true)
    List<Product> productWithCategories(@Param("productIds") List<Long> productIds);
}