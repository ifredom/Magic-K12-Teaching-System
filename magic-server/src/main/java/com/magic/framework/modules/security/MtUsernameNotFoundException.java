package com.magic.framework.modules.security;

public class MtUsernameNotFoundException extends MtAuthenticationException{
    public MtUsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MtUsernameNotFoundException(String message) {
        super(message);
    }
}
