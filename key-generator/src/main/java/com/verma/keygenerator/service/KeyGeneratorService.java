package com.verma.keygenerator.service;

import java.util.Set;

public interface KeyGeneratorService {

    String next();

    Set<String> next(int noOfKeys);
}
