package com.romeao.fruitshop.bootstrap;

import com.romeao.fruitshop.domain.Category;
import com.romeao.fruitshop.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final CategoryRepository categoryRepository;


    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        addCategories();
    }

    private void addCategories() {
        categoryRepository.save(Category.of("Fruits"));
        categoryRepository.save(Category.of("Dried"));
        categoryRepository.save(Category.of("Fresh"));
        categoryRepository.save(Category.of("Exotic"));
        categoryRepository.save(Category.of("Nuts"));
        log.info("Added categories: {} records", categoryRepository.count());
    }
}
