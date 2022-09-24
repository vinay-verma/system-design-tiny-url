package com.zemoso.systemdesignbootcamp.tinyurlapi.controller;

import com.zemoso.systemdesignbootcamp.tinyurlapi.dto.TinyUrlRequest;
import com.zemoso.systemdesignbootcamp.tinyurlapi.dto.TinyUrlResponse;
import com.zemoso.systemdesignbootcamp.tinyurlapi.exception.EntityNotFoundException;
import com.zemoso.systemdesignbootcamp.tinyurlapi.service.TinyUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

@RestController
@RequestMapping("/api/v1/tiny-url")
public class TinyUrlApiController {

    @Autowired
    private TinyUrlService tinyUrlService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TinyUrlResponse createTinyUrl(@RequestBody TinyUrlRequest request) {
        return tinyUrlService.createTinyUrl(request);
    }
}
