package com.hsenid.releasetracker.repository;

import com.hsenid.releasetracker.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository <Product, String> {
    Optional<Object> findByProductName(String productName);
}
