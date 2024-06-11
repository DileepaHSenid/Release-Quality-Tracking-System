package com.hsenid.releasetracker.service;

import com.hsenid.releasetracker.exception.AlreadyExistsException;
import com.hsenid.releasetracker.exception.ProductNotFoundException;
import com.hsenid.releasetracker.model.Product;
import com.hsenid.releasetracker.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
    }

    @Test
    public void testFetchAllProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.fetchAllProducts();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getProductName());
    }

    @Test
    public void testFetchAllProductsNoProductsFound() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.fetchAllProducts();
        });

        assertEquals("No products found", exception.getMessage());
    }

    @Test
    public void testAddProductDetails() {
        when(productRepository.findByProductName(product.getProductName())).thenReturn(Optional.empty());
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.addProductDetails(product);
        assertNotNull(result);
        assertEquals("Test Product", result.getProductName());
    }

    @Test
    public void testAddProductDetailsAlreadyExists() {
        when(productRepository.findByProductName(product.getProductName())).thenReturn(Optional.of(product));

        Exception exception = assertThrows(AlreadyExistsException.class, () -> {
            productService.addProductDetails(product);
        });

        assertEquals("Product with that name already exists with this name" + product.getProductName(), exception.getMessage());
    }

    @Test
    public void testFetchUsingId() {
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));

        Product result = productService.fetchUsingId("1");
        assertNotNull(result);
        assertEquals("Test Product", result.getProductName());
    }

    @Test
    public void testFetchUsingIdNotFound() {
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.fetchUsingId("1");
        });

        assertEquals("There is no product with id: 1", exception.getMessage());
    }

    @Test
    public void testDeleteById() {
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));

        productService.deleteById("1");

        verify(productRepository, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteById("1");
        });

        assertEquals("There is no product with id: 1", exception.getMessage());
    }
}
