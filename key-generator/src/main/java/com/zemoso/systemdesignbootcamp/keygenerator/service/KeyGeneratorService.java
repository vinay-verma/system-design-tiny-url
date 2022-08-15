package com.zemoso.systemdesignbootcamp.keygenerator.service;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface KeyGeneratorService {

    String next();

    Collection<String> generateBulk(int noOfKeys);

    String generate();

    CompletableFuture<Collection<String>> replenishMoreIfRequired();
}
