package com.franchise.data.repositories;

import com.franchise.data.models.Election;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ElectionRepository extends MongoRepository<Election, String> {
}
