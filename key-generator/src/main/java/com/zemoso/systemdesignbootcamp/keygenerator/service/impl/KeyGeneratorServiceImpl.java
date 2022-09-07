package com.zemoso.systemdesignbootcamp.keygenerator.service.impl;

import com.zemoso.systemdesignbootcamp.keygenerator.entity.Key;
import com.zemoso.systemdesignbootcamp.keygenerator.generators.KeyGenerator;
import com.zemoso.systemdesignbootcamp.keygenerator.repository.KeyRepository;
import com.zemoso.systemdesignbootcamp.keygenerator.service.KeyGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class KeyGeneratorServiceImpl implements KeyGeneratorService {

    @Value("${key-generator.replenish-threshold}")
    private Integer keyReplenishThreshold;

    private final KeyGenerator keyGenerator;
    private final KeyRepository keyRepository;

    private final ExecutorService executorService;

    @Autowired
    public KeyGeneratorServiceImpl(KeyGenerator keyGenerator, KeyRepository keyRepository, ExecutorService executorService) {
        this.keyGenerator = keyGenerator;
        this.keyRepository = keyRepository;
        this.executorService = executorService;
    }

    @Override
    public String next() {
        try {
            Optional<Key> nextKeyFromCache = keyRepository.findByUsedFalse();
            if (nextKeyFromCache.isPresent()) {
                return nextKeyFromCache.get().getId();
            }
            // generate one real quick
            log.info("Key pool is empty !! generating one on fly...");
            return generate();
        } finally {
            replenishMoreIfRequired();
        }
    }


    @Override
    public String generate() {
        long nextId = keyGenerator.next();
        String hash = Long.toString(nextId, 31);
        keyRepository.save(new Key(hash));
        return hash;
    }

    @Override
    public Collection<String> generateBulk(int noOfKeys) {
        return IntStream.range(0, noOfKeys)
                .parallel()
                .mapToObj(i -> generate())
                .collect(Collectors.toSet());
    }

    @Override
    public CompletableFuture<Collection<String>> replenishMoreIfRequired() {
        return CompletableFuture.supplyAsync(() -> {
            long noOfUnusedKeys = keyRepository.countByUsedFalse();
            if (noOfUnusedKeys < keyReplenishThreshold) {
                log.info("No of available pre generated key threshold reached {}, replenishing more...", noOfUnusedKeys);
                return this.generateBulk(keyReplenishThreshold);
            }
            return Collections.emptySet();
        }, executorService);
    }
}
