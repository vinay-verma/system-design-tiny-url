package com.zemoso.systemdesignbootcamp.tinyurlapi.exception;

import lombok.Getter;

@Getter
public class CoreException extends RuntimeException {
    public CoreException(String error, Throwable e) {
        super(error, e);
    }
}
