package com.fate.core.annotation;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * @ProjectName: fate-core
 * @Package: com.fate.core.annotation
 * @ClassName: FateRequestMapping
 * @Author: LJP
 * @Description: 类似于RequestMqpping
 * @Date: 2020/4/2 0:23
 * @Version: 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface FateRequestMapping {

    String name() default "";

    @AliasFor("path")
    String value() default "";

    @AliasFor("value")
    String path() default "";

    RequestMethod[] method() default {};

    String[] params() default {};

    String[] headers() default {};

    String[] consumes() default {};

    String[] produces() default {};
}
