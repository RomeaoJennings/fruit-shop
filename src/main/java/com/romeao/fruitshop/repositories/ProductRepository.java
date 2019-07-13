package com.romeao.fruitshop.repositories;

import com.romeao.fruitshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
