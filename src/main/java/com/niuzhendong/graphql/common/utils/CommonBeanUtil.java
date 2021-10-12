package com.niuzhendong.graphql.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CommonBeanUtil {

    public static CommonBeanUtil getInstance() {
        return CommonBeanUtil.CommonBeanUtilHolder.instance;
    }

    public static void saveCopy(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static void updateCopy(Object source, Object target) {
        beanCopyWithoutNull(source, target);
    }

    public static String generateBeanId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static <T> void beanCopyWithoutNull(T source, T target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static <T> void beanCopyWithIngore(T source, T target, String... ignoreProperties) {
        String[] pns = getNullAndIgnorePropertyNames(source, ignoreProperties);
        BeanUtils.copyProperties(source, target, pns);
    }

    public static String[] getNullAndIgnorePropertyNames(Object source, String... ignoreProperties) {
        Set<String> emptyNames = getNullPropertyNameSet(source);
        emptyNames.addAll(Arrays.asList(ignoreProperties));
        String[] result = new String[emptyNames.size()];
        return (String[])emptyNames.toArray(result);
    }

    public static String[] getNullPropertyNames(Object source) {
        Set<String> emptyNames = getNullPropertyNameSet(source);
        String[] result = new String[emptyNames.size()];
        return (String[])emptyNames.toArray(result);
    }

    public static Set<String> getNullPropertyNameSet(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet();
        PropertyDescriptor[] var4 = pds;
        int var5 = pds.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor pd = var4[var6];
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        return emptyNames;
    }

    private static class CommonBeanUtilHolder {
        private static CommonBeanUtil instance = new CommonBeanUtil();

        private CommonBeanUtilHolder() {
        }
    }
}
