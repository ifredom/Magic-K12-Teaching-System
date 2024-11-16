package com.magic.framework.exception;

/**
 * 作用:
 *
 * @author ifredom
 * @version v0.0.1
 * @date 2024/09/24 00:31
 * @email 1950735817@qq.com
 */
public class RateLimitException extends Exception {
    public RateLimitException(String msg) {
        super(msg);
    }
}
