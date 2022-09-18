package com.zemoso.systemdesignbootcamp.tinyurlapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Configuration
@Slf4j
public class AppConfig {
    @Value("${client.connectTimeout:30000}")
    private Integer connectTimeout;

    @Value("${http-request-buffer-size:4096}")
    private Integer httpRequestBufferSize;

    @Bean
    public WebClient strateosWebClient(
            OAuth2AuthorizedClientManager authorizedClientManager, HttpClient httpClient) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);

        oauth.setDefaultClientRegistrationId("tiny-url");
        return WebClient.builder()
                .defaultHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .apply(oauth.oauth2Configuration())
                .clientConnector(new JettyClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public HttpClient httpClient(ObjectMapper objectMapper) {
        SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
        HttpClient httpClient =
                new HttpClient(sslContextFactory) {
                    @Override
                    public Request newRequest(URI uri) {
                        Request request = super.newRequest(uri);
                        StringBuilder group = new StringBuilder();
                        request.onRequestBegin(
                                theRequest ->
                                        group
                                                .append("\n")
                                                .append("****** API Request ******")
                                                .append("\n")
                                                .append("Method: ")
                                                .append(theRequest.getMethod())
                                                .append("\n")
                                                .append("URI: ")
                                                .append(theRequest.getURI().toString())
                                                .append("\n"));

                        request.onRequestContent(
                                (theRequest, content) ->
                                        group.append("Body: \n").append(new String(content.array())).append("\n"));

                        request.onRequestSuccess(
                                theRequest -> {
                                    log.info(group.toString());
                                    group.delete(0, group.length());
                                });
                        group.append("\n");
                        return request;
                    }
                };
        httpClient.setConnectTimeout(connectTimeout);
        httpClient.setRequestBufferSize(httpRequestBufferSize);
        return httpClient;
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService clientService
    ) {
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, clientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }
}
