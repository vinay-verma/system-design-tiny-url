package com.verma.keygenerator.service;

import com.verma.keygenerator.generators.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyGeneratorService {

    @Autowired
    private KeyGenerator keyGenerator;
}
