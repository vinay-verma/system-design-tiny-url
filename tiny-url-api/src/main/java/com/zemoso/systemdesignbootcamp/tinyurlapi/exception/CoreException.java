package com.zemoso.systemdesignbootcamp.tinyurlapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CoreException extends RuntimeException {
    public CoreException(String error, Throwable e) {
        super(error, e);
    }
}
