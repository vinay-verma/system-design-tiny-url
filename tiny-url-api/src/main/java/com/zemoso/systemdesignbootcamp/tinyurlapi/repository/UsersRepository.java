package com.zemoso.systemdesignbootcamp.tinyurlapi.repository;

import com.zemoso.systemdesignbootcamp.tinyurlapi.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users, String> {
}
