package com.zemoso.systemdesignbootcamp.tinyurlapi.service.impl;

import com.zemoso.systemdesignbootcamp.tinyurlapi.client.ApiClient;
import com.zemoso.systemdesignbootcamp.tinyurlapi.config.CurrentRequestDataHolder;
import com.zemoso.systemdesignbootcamp.tinyurlapi.dto.TinyUrlRequest;
import com.zemoso.systemdesignbootcamp.tinyurlapi.entity.TinyUrls;
import com.zemoso.systemdesignbootcamp.tinyurlapi.exception.ApiException;
import com.zemoso.systemdesignbootcamp.tinyurlapi.exception.CoreException;
import com.zemoso.systemdesignbootcamp.tinyurlapi.repository.TinyUrlsRepository;
import com.zemoso.systemdesignbootcamp.tinyurlapi.service.TinyUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TinyUrlServiceImpl implements TinyUrlService {

    @Value("${short-url-host}")
    private String shortUrlHost;

    @Autowired
    private TinyUrlsRepository urlsRepository;

    @Autowired
    private ApiClient apiClient;

    @Override
    public String createTinyUrl(TinyUrlRequest tinyUrlRequest) {
        try {
            String longUrl = UriComponentsBuilder.fromHttpUrl(tinyUrlRequest.getUrl()).toUriString();
            String hash = apiClient.nextHash();
            String shortUrl = UriComponentsBuilder.newInstance().host(shortUrlHost).path(hash).toUriString();
            TinyUrls tinyUrl = TinyUrls.builder()
                    .id(hash)
                    .longUrl(longUrl)
                    .tinyUrl(shortUrl)
                    .owner(CurrentRequestDataHolder.getUserContext().getEmail())
                    .build();
            urlsRepository.save(tinyUrl);
            return shortUrl;
        } catch (ApiException e) {
            throw new CoreException("Error while shortening url", e);
        }
    }
}
