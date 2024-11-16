package com.magic.framework.aop.repeatedSubmit;

import com.magic.framework.model.AjaxResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 重试 切面
 * 1. 首先所有的接口返回值，本项目都进行了统一封装： AjaxResult
 * 2. 现在我将错误码code设置为8888，8888表示当接口出现8888错误码时，这个接口可以进行一次重试
 *
 * @author magic
 */
@Aspect
@Component
public class RetryAspect {
    private static final int DEFAULT_MAX_RETRIES = 2;

    private final int maxRetries = DEFAULT_MAX_RETRIES;

    @Pointcut("execution(* com.magic.module.*.controller.*.testOrderRepeatSubmit(..))")
    public void pointcut() {}

    @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
        int numAttempts = 0;
        Exception lastException = null;
        do {
            numAttempts++;
            try {
                Object result = joinPoint.proceed();

                System.out.println("=========="+result);

                // 自定义错误类型，进行重试
                if(result instanceof AjaxResult && ((AjaxResult) result).getCode() == 8888){
                    //休眠1秒
                    Thread.sleep(1000);
                    System.out.println("第" + numAttempts + "次重试");
                    return joinPoint.proceed();
                }
                return result;
            } catch (Exception ex) {
                System.out.println( "执行");
                lastException = ex;
                // 其他类型错误异常，处理区域.可以在这里添加日志记录或其他逻辑
                throw ex;
            }
        } while (numAttempts<maxRetries); // 控制结构，用于跳出循环

    }
}
