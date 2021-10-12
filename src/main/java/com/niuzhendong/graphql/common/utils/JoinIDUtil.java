package com.niuzhendong.graphql.common.utils;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.niuzhendong.graphql.common.Exception.CommonException;
import com.niuzhendong.graphql.common.annotation.BaseJoinId;
import com.niuzhendong.graphql.common.annotation.BaseJoinId.Index;
import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.BaseJoinDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JoinIDUtil {
    private static final Logger logger = LoggerFactory.getLogger(JoinIDUtil.class);

    public JoinIDUtil() {
    }

    public static JoinIDUtil getInstance() {
        return JoinIDUtil.JoinIDUtilHolder.instance;
    }

    public static <D extends CommonDomain> String callIdFieldGetMethod(D domain, Map<String, Method> methodMap, String idFieldName) {
        try {
            Method method = getIdFieldGetMethod(idFieldName, methodMap, domain.getClass());
            Object value = method.invoke(domain);
            return (String)value;
        } catch (InvocationTargetException | IllegalAccessException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static <DTO extends BaseJoinDTO, D extends CommonDomain> String generateJoinId(D domain, Map<String, Method> methodMap) {
        Class domainClass = domain.getClass();
        List<String> indexIds = Arrays.asList("", "", "", "", "");
        List<Field> fields = Arrays.asList(ReflectUtil.getFields(domainClass));
        Iterator var5 = fields.iterator();

        while(var5.hasNext()) {
            Field field = (Field)var5.next();
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (!isStatic) {
                BaseJoinId qw = (BaseJoinId)field.getAnnotation(BaseJoinId.class);
                if (qw != null) {
                    if (qw.index().ordinal() >= CommonStatic.MAX_JOIN_TABLE_NUM) {
                        throw new CommonException("BaseJoinId的index属性大于等于{}关联表不支持{}以上表关联，建议您重新设计Domain。同时注意BaseJoinId的index属性是否从0开始，依次累加", new Object[]{CommonStatic.MAX_JOIN_TABLE_NUM, CommonStatic.MAX_JOIN_TABLE_NUM});
                    }

                    String value = callIdFieldGetMethod(domain, methodMap, field.getName());
                    indexIds.set(qw.index().ordinal(), value);
                }
            }
        }

        if (CollectionUtils.isEmpty(indexIds)) {
            return null;
        } else {
            StringBuilder result = new StringBuilder();
            indexIds.forEach((id) -> {
                result.append(id);
            });
            return result.toString();
        }
    }

    public static <D extends CommonDomain> Method getIdFieldGetMethod(String idFieldName, Map<String, Method> methodMap, Class<D> domainClass) {
        Method method = (Method)methodMap.get(idFieldName);
        if (method == null) {
            try {
                String firstLetter = idFieldName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + idFieldName.substring(1);
                method = domainClass.getMethod(getter);
                methodMap.put(idFieldName, method);
            } catch (NoSuchMethodException var6) {
                var6.printStackTrace();
            }
        }

        return method;
    }

    public static <D extends CommonDomain> String getIdFieldNameByIndex(Index index, List<String> idFieldNames, Class<D> domainClass) {
        String idFieldName = (String)idFieldNames.get(index.ordinal());
        if (StrUtil.isBlank(idFieldName)) {
            List<Field> fields = Arrays.asList(ReflectUtil.getFields(domainClass));
            Iterator var5 = fields.iterator();

            while(var5.hasNext()) {
                Field field = (Field)var5.next();
                BaseJoinId qw = (BaseJoinId)field.getAnnotation(BaseJoinId.class);
                if (qw != null && qw.index() == index) {
                    idFieldNames.set(index.ordinal(), field.getName());
                    return field.getName();
                }
            }
        }

        return idFieldName;
    }

    private static class JoinIDUtilHolder {
        private static JoinIDUtil instance = new JoinIDUtil();

        private JoinIDUtilHolder() {
        }
    }
}
