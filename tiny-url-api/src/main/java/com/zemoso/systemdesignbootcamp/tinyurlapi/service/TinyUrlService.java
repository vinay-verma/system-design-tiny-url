package com.zemoso.systemdesignbootcamp.tinyurlapi.service;

import com.zemoso.systemdesignbootcamp.tinyurlapi.dto.TinyUrlRequest;
import org.springframework.stereotype.Service;

public interface TinyUrlService {
    String createTinyUrl(TinyUrlRequest tinyUrlRequest);
}
