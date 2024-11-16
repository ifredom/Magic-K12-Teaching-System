package com.magic.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 作用:
 *
 * @author ifredom
 * @version v0.0.1
 * @date 2024/09/20 11:29
 * @email 1950735817@qq.com
 */
@Data
@AllArgsConstructor
public class ErrorCode {
    /**
     * 错误码
     */
    private int code = 0;
    /**
     * 错误提示
     */
    private String msg;

    public ErrorCode(String msg){
        this.msg = msg;
    }
}
