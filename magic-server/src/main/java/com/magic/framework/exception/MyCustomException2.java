package com.magic.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class MyCustomException2 extends RuntimeException{
    private String msg = "";
    public MyCustomException2() {}
    public MyCustomException2(String msg) {
        this.msg = msg;
    }
}