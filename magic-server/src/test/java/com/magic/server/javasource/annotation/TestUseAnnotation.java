package com.magic.server.javasource.annotation;


import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.Annotation;

@SpringBootTest
public class TestUseAnnotation {

    @Test
    public void testAnnotation() {
        boolean hasAnnotation  = OneAnnotationClass.class.isAnnotationPresent(MyNobugAnnotation.class);
        System.out.println("hasAnnotation:"+hasAnnotation);

        Annotation[] annotations = OneAnnotationClass.class.getAnnotations();

        System.out.println("打印所有注解的名称");
        for (Annotation item:annotations){
            System.out.println(item.annotationType().getName());
        }
    }


}

@Data
@MyNobugAnnotation(username = "测试注解",staticConstructor = "of")
@MyNobugAnnotation2(username = "测试注解2",staticConstructor = "of2")
class OneAnnotationClass{

    private String name;

    public OneAnnotationClass(String name) {
        this.name = name;
    }
}