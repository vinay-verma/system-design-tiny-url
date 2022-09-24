package com.zemoso.systemdesignbootcamp.tinyurlapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TinyUrlResponse {
    private String tinyUrl;
    private String url;
}
