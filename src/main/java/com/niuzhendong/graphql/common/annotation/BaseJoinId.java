package com.niuzhendong.graphql.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BaseJoinId {
    BaseJoinId.Index index() default BaseJoinId.Index.first;

    enum Index {
        first,
        second,
        third,
        fourth,
        Fifth;
    }
}
