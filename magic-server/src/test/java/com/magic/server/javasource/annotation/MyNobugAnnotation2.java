package com.magic.server.javasource.annotation;


import java.lang.annotation.*;

/**
 *  测试注解
 * 名称为：没有bug注解
 * 复习 创建一个注解，并通过反射获取到注解信息
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyNobugAnnotation2 {
    String username();
    String staticConstructor();
}
