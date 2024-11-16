package com.magic.framework.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Integer code = 500;
    private String msg;

    public CustomException(String msg){
        super(msg);
        this.msg = msg;
    }
    public CustomException(String msg, Throwable e){
        super(msg, e);
        this.msg = msg;
    }

    public CustomException(String msg, Integer code){
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CustomException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
