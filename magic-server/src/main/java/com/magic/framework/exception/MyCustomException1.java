package com.magic.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class MyCustomException1 extends RuntimeException{
    private String msg;
    public MyCustomException1() {}
    public MyCustomException1(String msg) {
        this.msg = msg;
    }
}