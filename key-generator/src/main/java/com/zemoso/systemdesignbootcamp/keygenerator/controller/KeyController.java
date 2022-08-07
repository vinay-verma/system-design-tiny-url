package com.zemoso.systemdesignbootcamp.keygenerator.controller;

import com.zemoso.systemdesignbootcamp.keygenerator.service.KeyGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/key")
public class KeyController {

    @Autowired
    private KeyGeneratorService keyGeneratorService;

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Set<String> generate(@RequestParam(value = "limit", required = false) Integer limit) {
        if (limit != null && limit > 1) {
            return keyGeneratorService.next(limit);
        }
        return Collections.singleton(keyGeneratorService.next());
    }
}
