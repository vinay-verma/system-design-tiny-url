package com.zemoso.systemdesignbootcamp.tinyurlapi.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String error, Throwable e) {
        super(error, e);
    }
    public EntityNotFoundException(String error) {
        super(error);
    }
}
