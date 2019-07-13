package com.romeao.fruitshop.bootstrap;

import com.romeao.fruitshop.domain.Category;
import com.romeao.fruitshop.domain.Customer;
import com.romeao.fruitshop.domain.Vendor;
import com.romeao.fruitshop.repositories.CategoryRepository;
import com.romeao.fruitshop.repositories.CustomerRepository;
import com.romeao.fruitshop.repositories.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;


    public DataInitializer(CategoryRepository categoryRepository,
                           CustomerRepository customerRepository,
                           VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {
        addCategories();
        addCustomers();
        addVendors();
    }

    private void addCategories() {
        categoryRepository.save(Category.of("Fruits"));
        categoryRepository.save(Category.of("Dried"));
        categoryRepository.save(Category.of("Fresh"));
        categoryRepository.save(Category.of("Exotic"));
        categoryRepository.save(Category.of("Nuts"));
        log.info("Added categories: {} records", categoryRepository.count());
    }

    private void addCustomers() {
        customerRepository.save(Customer.of("Joe", "Newman"));
        customerRepository.save(Customer.of("Michael", "Lachappele"));
        customerRepository.save(Customer.of("Willi", "Schenker"));
        customerRepository.save(Customer.of("Anne", "Hine"));
        customerRepository.save(Customer.of("Alice", "Eastman"));
        customerRepository.save(Customer.of("Romeao", "Jennings"));
        customerRepository.save(Customer.of("Liam", "Jennings"));
        log.info("Added customers: {} records", customerRepository.count());
    }

    private void addVendors() {
        vendorRepository.save(Vendor.of("Village Flower Shop"));
        vendorRepository.save(Vendor.of("Pete's Dishes"));
        vendorRepository.save(Vendor.of("City Hobby Shop"));
        log.info("Added vendors: {} records", vendorRepository.count());
    }
}
