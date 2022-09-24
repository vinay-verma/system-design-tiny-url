package com.zemoso.systemdesignbootcamp.tinyurlapi.repository;

import com.zemoso.systemdesignbootcamp.tinyurlapi.entity.TinyUrls;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TinyUrlsRepository extends MongoRepository<TinyUrls, String> {
}
