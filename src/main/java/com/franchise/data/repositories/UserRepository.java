package com.franchise.data.repositories;

import com.franchise.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmailAddress(String emailAddress);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
