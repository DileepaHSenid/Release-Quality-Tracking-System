package com.hsenid.releasetracker.repository;

import com.hsenid.releasetracker.model.Release;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReleaseRepository extends MongoRepository <Release, String> {
    Optional <Release> findByVersion(String id);
}
