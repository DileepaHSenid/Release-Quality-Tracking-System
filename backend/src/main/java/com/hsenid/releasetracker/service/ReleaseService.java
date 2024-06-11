package com.hsenid.releasetracker.service;

import com.hsenid.releasetracker.exception.ReleaseNotFoundException;
import com.hsenid.releasetracker.exception.ProductNotFoundException;
import com.hsenid.releasetracker.model.Release;
import com.hsenid.releasetracker.model.Product;
import com.hsenid.releasetracker.repository.ReleaseRepository;
import com.hsenid.releasetracker.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseService {

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Release> fetchAllReleases() {
        List<Release> releases = releaseRepository.findAll();
        if (releases.isEmpty()) {
            throw new RuntimeException("No releases found");
        }
        return releases;
    }

    public Release addReleaseDetails(Release release, String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("There is no product with id: " + productId));
        release.setProduct(product);
        return releaseRepository.save(release);
    }

    public Release fetchUsingId(String id) {
        return releaseRepository.findById(id)
                .orElseThrow(() -> new ReleaseNotFoundException("There is no release with id: " + id));
    }

    public void deleteById(String id) {
        Release release = releaseRepository.findById(id)
                .orElseThrow(() -> new ReleaseNotFoundException("There is no release with id: " + id));
        releaseRepository.deleteById(id);
    }
}
