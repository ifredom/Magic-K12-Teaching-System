package com.magic.framework.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import com.magic.framework.model.AjaxResult;
import com.magic.framework.utils.MagicHttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.NoPermissionException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "magic server");
    }

    /**
     * 所有异常都返回统一的json数据
     * @param ex CustomException
     */
    @ExceptionHandler(value = CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AjaxResult<Void> handleCustomException(CustomException ex) {
        log.error("业务异常:{}", ex.getMsg());
        return AjaxResult.error(ex.getCode(), ex.getMsg());
    }

    /**
     * 参数类型不匹配异常
     * @param ex MethodArgumentTypeMismatchException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public AjaxResult<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.info(MagicHttpRequestUtil.getAllRequestInfo());
        log.error("参数类型不匹配异常:{}", ex.getMessage());
        return AjaxResult.error400("参数类型不匹配");
    }

    /**
     * 缺少请求参数
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public AjaxResult<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.info(MagicHttpRequestUtil.getAllRequestInfo());
        log.error("缺少请求参数:{}", ex.getMessage());
        return AjaxResult.error400("缺少请求参数");
    }

    /**
     * 参数解析失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public AjaxResult<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.info(MagicHttpRequestUtil.getAllRequestInfo());
        log.error("参数解析失败:{}", ex.getMessage());
        return AjaxResult.error400("参数解析失败");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public AjaxResult<Void> handleValidationException(ValidationException e) {
        log.info(MagicHttpRequestUtil.getAllRequestInfo());
        log.error("参数验证失败", e);
        return AjaxResult.error400("参数验证失败");
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info(MagicHttpRequestUtil.getAllRequestInfo());
        log.error("不支持当前请求方法", e);
        return AjaxResult.error400("不支持当前请求方法");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public AjaxResult<Void> handleHttpMediaTypeNotSupportedException(Exception e) {
        log.info(MagicHttpRequestUtil.getAllRequestInfo());
        log.error("不支持当前媒体类型", e);
        return AjaxResult.error400("不支持当前媒体类型");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public AjaxResult<Void> handleDuplicateKeyException(DuplicateKeyException e) {
        log.info(MagicHttpRequestUtil.getAllRequestInfo());
        log.error(e.getMessage(), e);
        return AjaxResult.error500("数据库中已存在该记录");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public AjaxResult<Void> handleMaxSizeException(MaxUploadSizeExceededException e) {
        log.info(MagicHttpRequestUtil.getAllRequestInfo());
        log.error(e.getMessage(), e);
        return AjaxResult.error500("File too large!");
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public AjaxResult<Void> handleNotLoginException(NotLoginException e) {
        logErrorMsg(e.getMessage());
        return AjaxResult.error401(e.getMessage());
    }

    @ExceptionHandler(NoPermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AjaxResult<Void> handleMaxSizeException(NoPermissionException e) {
        logErrorMsg(e.getMessage());
        return AjaxResult.error403(e.getMessage());
    }

    @ExceptionHandler(SaTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AjaxResult<Void> handleMaxSizeException(SaTokenException e) {
        logErrorMsg(e.getMessage());
        return AjaxResult.error403(e.getMessage());
    }

    /**
     * 验证bean类型
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult<List<Map<String, Object>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);

        List<String> errors = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            errors.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        });

        String errorMessage = String.join("; ", errors);
        return AjaxResult.error(errorMessage);
    }

    /**
     * 验证参数param类型
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult<List<String>> handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);

        List<String> errors = new ArrayList<>();

        e.getConstraintViolations().forEach((constraintViolation) -> {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.add(propertyPath + ": " + message);
        });

        String errorMessage = String.join("; ", errors);
        return AjaxResult.error(errorMessage);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AjaxResult<Void> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(RateLimitException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public AjaxResult<Void> handleRateLimitException(RateLimitException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(429, "操作过于频繁，请稍后重试");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AjaxResult<Void> handleException(Exception e) {
        log.info(MagicHttpRequestUtil.getAllRequestInfo());
        log.error(e.getMessage(), e);
        return AjaxResult.error400("服务器内部异常");
    }

    private static void logErrorMsg(String e) {
        log.error("uri:{}, httpMethod:{}, errMsg:{}", MagicHttpRequestUtil.getRequestURI(),
                MagicHttpRequestUtil.getRequest().getMethod(), e);
    }


    @ExceptionHandler(value = {MyCustomException1.class})
    public void onMyException1(){
        // 处理 MyCustomException1 这种异常
    }

    @ExceptionHandler(value = {MyCustomException2.class})
    public void onMyException2(){
        // 处理 MyCustomException2 这种异常
    }
}
