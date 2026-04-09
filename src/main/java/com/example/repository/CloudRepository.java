package com.example.repository;

import com.example.model.Cloud;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudRepository extends MongoRepository<Cloud, String> {
}
