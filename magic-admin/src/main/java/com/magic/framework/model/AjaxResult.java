package com.magic.framework.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 这个类是http接口返回的同意数据model，包含code,msg,data三个字段
 */

@Data
@AllArgsConstructor
@ApiModel
public class AjaxResult<T>  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "响应码，非0 即为异常", example = "0")
    private int code;
    @ApiModelProperty(notes = "响应消息", example = "操作成功")
    private String msg;
    @ApiModelProperty(notes = "响应数据")
    private T data;

    // 添加success 方法和 error 方法，方便返回成功和失败的结果

    // 重复提交错误码
    public static final int ERROR_CODE_REPEAT_SUBMIT = 8888;

    public static <T> AjaxResult<T> success() {
        return new AjaxResult<>(200, "操作成功", null);
    }

    public static <T> AjaxResult<T> success(String msg) {
        return new AjaxResult<>(200, msg, null);
    }

    public static <T> AjaxResult<T> success(T data) {
        return new AjaxResult<>(200, "操作成功", data);
    }

    public static <T> AjaxResult<T> success(int code, T data) {
        return new AjaxResult<>(code, "操作成功", data);
    }

    public static <T> AjaxResult<T> error(int code) {
        return new AjaxResult<>(code, null, null);
    }

    public static <T> AjaxResult<T> error(String msg) {
        return new AjaxResult<>(-1, msg, null);
    }

    public static <T> AjaxResult<T> error(int code, String msg) {
        return new AjaxResult<>(code, msg, null);
    }
    public static <T> AjaxResult<T> error(T data) {
        return new AjaxResult<>(400, "", data);
    }

    public static <T> AjaxResult<T> error400(String msg) {
        return new AjaxResult<>(400, msg, null);
    }

    public static <T> AjaxResult<T> error401(String msg) {
        return new AjaxResult<>(401, msg, null);
    }

    public static <T> AjaxResult<T> error403(String msg) {
        return new AjaxResult<>(403, msg, null);
    }

    public static <T> AjaxResult<T> error500(String msg) {
        return new AjaxResult<>(500, msg, null);
    }

}
