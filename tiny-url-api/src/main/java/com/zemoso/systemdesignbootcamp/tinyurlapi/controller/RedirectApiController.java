package com.zemoso.systemdesignbootcamp.tinyurlapi.controller;

import com.zemoso.systemdesignbootcamp.tinyurlapi.entity.TinyUrls;
import com.zemoso.systemdesignbootcamp.tinyurlapi.exception.EntityNotFoundException;
import com.zemoso.systemdesignbootcamp.tinyurlapi.service.TinyUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RedirectApiController {

    @Autowired
    private TinyUrlService tinyUrlService;

    @GetMapping("/{id}")
    public void redirect(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        TinyUrls tinyUrls = tinyUrlService.getTinyUrl(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Unable to find requested url %s in our system", request.getRequestURI())));
        response.setHeader("Location", tinyUrls.getLongUrl());
        response.setStatus(HttpStatus.FOUND.value());
    }
}
