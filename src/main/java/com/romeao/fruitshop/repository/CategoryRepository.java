package com.romeao.fruitshop.repository;

import com.romeao.fruitshop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}