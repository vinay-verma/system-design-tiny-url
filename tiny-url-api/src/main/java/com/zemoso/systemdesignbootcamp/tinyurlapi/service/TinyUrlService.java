package com.zemoso.systemdesignbootcamp.tinyurlapi.service;

import com.zemoso.systemdesignbootcamp.tinyurlapi.dto.TinyUrlRequest;
import com.zemoso.systemdesignbootcamp.tinyurlapi.dto.TinyUrlResponse;
import com.zemoso.systemdesignbootcamp.tinyurlapi.entity.TinyUrls;

import java.util.Optional;

public interface TinyUrlService {
    TinyUrlResponse createTinyUrl(TinyUrlRequest tinyUrlRequest);

    Optional<TinyUrls> getTinyUrl(String id);
}
