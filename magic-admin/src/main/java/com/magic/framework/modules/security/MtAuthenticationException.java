package com.magic.framework.modules.security;

public abstract class MtAuthenticationException extends RuntimeException {

    public MtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MtAuthenticationException(String message) {
        super(message);
    }
}

