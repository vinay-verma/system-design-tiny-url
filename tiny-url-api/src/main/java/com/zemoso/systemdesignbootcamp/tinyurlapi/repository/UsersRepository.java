package com.zemoso.systemdesignbootcamp.tinyurlapi.repository;

import com.zemoso.systemdesignbootcamp.tinyurlapi.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UsersRepository extends MongoRepository<Users, UUID> {
}
