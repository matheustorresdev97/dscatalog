package com.matheustorres.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matheustorres.dscatalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
