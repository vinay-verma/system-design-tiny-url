package com.zemoso.systemdesignbootcamp.tinyurlapi.client;

import com.zemoso.systemdesignbootcamp.tinyurlapi.config.CurrentRequestDataHolder;
import com.zemoso.systemdesignbootcamp.tinyurlapi.exception.ApiException;
import com.zemoso.systemdesignbootcamp.tinyurlapi.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@Slf4j
public class ApiClient {

    @Value("${urls.key-generator.next}")
    private String nextHashUrl;

    @Autowired
    private WebClient webClient;

    public String nextHash() throws ApiException {
        return get(nextHashUrl, String.class);
    }

    private  <T> T get(String url, Class<T> responseType) throws ApiException {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                        .encode()
                        .build()
                        .toUri();

        return webClient.get()
                .uri(uri)
                .header(
                        Constants.HEADER_USER_EMAIL_ID,
                        CurrentRequestDataHolder.getUserContext().getEmail()
                )
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        log.debug("Http Request failed with status {}", clientResponse.statusCode());
                        clientResponse.body((r, context) -> r.getBody());
                        return clientResponse
                                .bodyToMono(String.class)
                                .flatMap(
                                        body ->
                                                Mono.error(
                                                        new ApiException(clientResponse.statusCode(), body)));
                    }
                    return clientResponse.bodyToMono(responseType);
                })
                .block();
    }
}
