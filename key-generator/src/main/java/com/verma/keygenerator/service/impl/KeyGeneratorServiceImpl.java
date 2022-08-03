package com.verma.keygenerator.service.impl;

import com.verma.keygenerator.generators.KeyGenerator;
import com.verma.keygenerator.service.KeyGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class KeyGeneratorServiceImpl implements KeyGeneratorService {

    @Autowired
    private KeyGenerator keyGenerator;

    @Override
    public String next() {
        long nextId = keyGenerator.next();
        return Long.toString(nextId, 31);
    }

    @Override
    public Set<String> next(int noOfKeys) {
        return IntStream.range(0, noOfKeys)
                .mapToObj(i -> next())
                .collect(Collectors.toSet());
    }
}
