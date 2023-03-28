package com.franchise.data.repositories;

import com.franchise.data.models.Votes;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VotesRepository extends MongoRepository<Votes, String> {

}
