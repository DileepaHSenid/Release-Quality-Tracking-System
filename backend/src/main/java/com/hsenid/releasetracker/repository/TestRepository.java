package com.hsenid.releasetracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.hsenid.releasetracker.model.Test;

public interface TestRepository extends MongoRepository<Test, String> {

}
