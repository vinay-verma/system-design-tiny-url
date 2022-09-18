package com.zemoso.systemdesignbootcamp.tinyurlapi.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class TinyUrlRequest {
    @NonNull
    private String url;
}
