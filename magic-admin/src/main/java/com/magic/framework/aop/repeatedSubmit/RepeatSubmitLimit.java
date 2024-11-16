package com.magic.framework.aop.repeatedSubmit;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface RepeatSubmitLimit {
    /**
     * 业务名称key，例如下单业务 order
     */
    String businessKey() default "";

    /**
     * 业务参数，例如下单业务 orderId
     */
    String businessParam() default "";

    /**
     * 是否隔离用户，默认不启动
     */
    boolean userLimit() default false;

    /**
     * 锁时间 默认2s
     */
    int limitTime() default 2;
}
