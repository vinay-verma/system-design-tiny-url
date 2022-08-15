package com.zemoso.systemdesignbootcamp.keygenerator.controller;

import com.zemoso.systemdesignbootcamp.keygenerator.service.KeyGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/key")
public class KeyController {

    @Autowired
    private KeyGeneratorService keyGeneratorService;

    @GetMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String generate() {
        return keyGeneratorService.next();
    }
}
