package com.zemoso.systemdesignbootcamp.keygenerator.repository;

import com.zemoso.systemdesignbootcamp.keygenerator.entity.Key;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeyRepository extends CrudRepository<Key, String> {

    Optional<Key> findByUsedFalse();

    long countByUsedFalse();
}
