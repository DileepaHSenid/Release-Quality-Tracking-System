package com.hsenid.releasetracker.service;

import com.hsenid.releasetracker.exception.AlreadyExistsException;
import com.hsenid.releasetracker.exception.ProductNotFoundException;
import com.hsenid.releasetracker.model.Product;
import com.hsenid.releasetracker.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> fetchAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new RuntimeException("No products found");
        }
        return products;
    }

    public Product addProductDetails(Product product) {
        // Check if product with the same name already exists
        productRepository.findByProductName(product.getProductName())
                .ifPresent(existingProduct -> {
                    throw new AlreadyExistsException("Product with that name already exists with this name"+product.getProductName());
                });

        return productRepository.save(product);
    }


    public Product fetchUsingId(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("There is no product with id: " + id));
    }

    public void deleteById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("There is no product with id: " + id));
        productRepository.deleteById(id);
    }
}
