package com.niuzhendong.graphql.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class CommonReflectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonReflectionUtil.class);

    public CommonReflectionUtil() {
    }

    public static CommonReflectionUtil getInstance() {
        return CommonReflectionUtil.CommonReflectionUtilHolder.instance;
    }

    public static Class<?> getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            logger.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
            return Object.class;
        } else {
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            if (index < params.length && index >= 0) {
                if (!(params[index] instanceof Class)) {
                    logger.warn(String.format("Warn: %s not set the actual class on superclass generic parameter", clazz.getSimpleName()));
                    return Object.class;
                } else {
                    return (Class)params[index];
                }
            } else {
                logger.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index, clazz.getSimpleName(), params.length));
                return Object.class;
            }
        }
    }

    private static class CommonReflectionUtilHolder {
        private static CommonReflectionUtil instance = new CommonReflectionUtil();

        private CommonReflectionUtilHolder() {
        }
    }
}