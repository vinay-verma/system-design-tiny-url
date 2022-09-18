package com.zemoso.systemdesignbootcamp.tinyurlapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends Exception {
    final private HttpStatus status;

    public ApiException(HttpStatus status, String error) {
        super(error);
        this.status = status;
    }
}
