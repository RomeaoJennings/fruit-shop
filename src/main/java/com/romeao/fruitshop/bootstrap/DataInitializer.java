package com.romeao.fruitshop.bootstrap;

import com.romeao.fruitshop.domain.Category;
import com.romeao.fruitshop.domain.Customer;
import com.romeao.fruitshop.domain.Product;
import com.romeao.fruitshop.domain.Vendor;
import com.romeao.fruitshop.repositories.CategoryRepository;
import com.romeao.fruitshop.repositories.CustomerRepository;
import com.romeao.fruitshop.repositories.ProductRepository;
import com.romeao.fruitshop.repositories.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;


    public DataInitializer(CategoryRepository categoryRepository,
                           CustomerRepository customerRepository,
                           VendorRepository vendorRepository,
                           ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        addCategories();
        addCustomers();
        addVendorsAndProducts();
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

    private void addVendorsAndProducts() {
        Vendor flowerShop = vendorRepository.save(Vendor.of("Village Flower Shop"));
        Vendor toyShop = vendorRepository.save(Vendor.of("Pete's Toys"));
        Vendor hobbyShop = vendorRepository.save(Vendor.of("City Hobby Shop"));

        buildAndAttachProduct("Red Rose", 3.95, flowerShop);
        buildAndAttachProduct("Pink Carnation", 2.50, flowerShop);

        buildAndAttachProduct("Model airplane", 29.99, toyShop, hobbyShop);
        buildAndAttachProduct("Rubik's Cube", 7.99, toyShop);

        buildAndAttachProduct("Drone", 199.99, hobbyShop);

        buildAndAttachProduct("Cinderella Doll", 5.95);

        log.info("Added vendors: {} records", vendorRepository.count());
        log.info("Added products: {} records", productRepository.count());
    }

    private void buildAndAttachProduct(String name, double price, Vendor... vendors) {
        Product product = Product.of(name, BigDecimal.valueOf(price));
        product.getVendors().addAll(Arrays.asList(vendors));
        productRepository.save(product);
    }

}
