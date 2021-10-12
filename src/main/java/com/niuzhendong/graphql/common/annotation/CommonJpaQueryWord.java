package com.niuzhendong.graphql.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CommonJpaQueryWord {
    String column() default "";

    CommonJpaQueryWord.MatchType func() default CommonJpaQueryWord.MatchType.equal;

    boolean nullble() default false;

    boolean emptyAble() default false;

    enum MatchType {
        equal,
        gt,
        ge,
        lt,
        le,
        notEqual,
        like,
        notLike,
        greaterThan,
        greaterThanOrEqualTo,
        lessThan,
        lessThanOrEqualTo,
        leftJoin;
    }
}
