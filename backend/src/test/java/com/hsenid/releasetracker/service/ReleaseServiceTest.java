package com.hsenid.releasetracker.service;

import com.hsenid.releasetracker.exception.ReleaseNotFoundException;
import com.hsenid.releasetracker.exception.ProductNotFoundException;
import com.hsenid.releasetracker.model.Release;
import com.hsenid.releasetracker.model.Product;
import com.hsenid.releasetracker.repository.ReleaseRepository;
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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReleaseServiceTest {

    @Mock
    private ReleaseRepository releaseRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ReleaseService releaseService;

    private Release release;
    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");

        release = new Release();
        release.setReleaseId("1");
        release.setVersion(1.0);
        release.setProduct(product);
    }

    @Test
    public void testFetchAllReleases() {
        when(releaseRepository.findAll()).thenReturn(Arrays.asList(release));

        List <Release> releases = releaseService.fetchAllReleases();

        assertNotNull(releases);
        assertEquals(1, releases.size());
        assertEquals("1", releases.get(0).getReleaseId());
        verify(releaseRepository, times(1)).findAll();
    }

    @Test
    public void testFetchAllReleasesNoReleasesFound() {
        when(releaseRepository.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            releaseService.fetchAllReleases();
        });

        assertEquals("No releases found", exception.getMessage());
    }

    @Test
    public void testAddReleaseDetails() {
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(releaseRepository.save(release)).thenReturn(release);

        Release savedRelease = releaseService.addReleaseDetails(release, "1");

        assertNotNull(savedRelease);
        assertEquals("1", savedRelease.getReleaseId());
        verify(productRepository, times(1)).findById("1");
        verify(releaseRepository, times(1)).save(release);
    }

    @Test
    public void testAddReleaseDetailsProductNotFound() {
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            releaseService.addReleaseDetails(release, "1");
        });

        assertEquals("There is no product with id: 1", exception.getMessage());
        verify(productRepository, times(1)).findById("1");
        verify(releaseRepository, never()).save(release);
    }

    @Test
    public void testFetchUsingId() {
        when(releaseRepository.findById("1")).thenReturn(Optional.of(release));

        Release foundRelease = releaseService.fetchUsingId("1");

        assertNotNull(foundRelease);
        assertEquals("1", foundRelease.getReleaseId());
        verify(releaseRepository, times(1)).findById("1");
    }

    @Test
    public void testFetchUsingIdNotFound() {
        when(releaseRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ReleaseNotFoundException.class, () -> {
            releaseService.fetchUsingId("1");
        });

        assertEquals("There is no release with id: 1", exception.getMessage());
        verify(releaseRepository, times(1)).findById("1");
    }

    @Test
    public void testDeleteById() {
        when(releaseRepository.findById("1")).thenReturn(Optional.of(release));

        releaseService.deleteById("1");

        verify(releaseRepository, times(1)).findById("1");
        verify(releaseRepository, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(releaseRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ReleaseNotFoundException.class, () -> {
            releaseService.deleteById("1");
        });

        assertEquals("There is no release with id: 1", exception.getMessage());
        verify(releaseRepository, times(1)).findById("1");
        verify(releaseRepository, never()).deleteById("1");
    }
}
