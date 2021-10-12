package com.niuzhendong.graphql.common.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface OperationLogAnno {
    String module() default "";

    String logInfo() default "";

    String logClassName() default "";

    String logOperationType() default "";

    String desc() default "";
}
