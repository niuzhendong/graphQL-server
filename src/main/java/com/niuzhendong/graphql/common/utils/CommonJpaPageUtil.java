package com.niuzhendong.graphql.common.utils;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.niuzhendong.graphql.common.annotation.CommonJpaQueryWord;
import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.CommonPageDTO;
import com.niuzhendong.graphql.common.dto.SortDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CommonJpaPageUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonJpaPageUtil.class);

    public CommonJpaPageUtil() {
    }

    public static CommonJpaPageUtil getInstance() {
        return CommonJpaPageUtil.CommonJpaPageUtilHolder.instance;
    }

    public PageRequest toPageRequest(final CommonPageDTO pageDTO) {
        return this.toPageRequest(pageDTO, "id");
    }

    public PageRequest toPageRequest(final CommonPageDTO pageDTO, String defaultSortField) {
        int pageIndex = pageDTO.getPage() - 1;
        int pageSize = pageDTO.getPageSize();
        PageRequest pageable = null;
        List<SortDTO> sortDTOS = pageDTO.getSorts();
        ArrayList orders;
        if (CollectionUtils.isEmpty(sortDTOS)) {
            orders = new ArrayList();
            orders.add(new Sort.Order(Sort.Direction.valueOf("ASC"), defaultSortField));
            pageable = PageRequest.of(pageIndex, pageSize, Sort.by(orders));
        } else {
            orders = new ArrayList();
            sortDTOS.forEach((sortDTO) -> {
                String field = sortDTO.getFieldName();
                String direction = sortDTO.getDirection();
                if (StrUtil.isNotBlank(field) && StrUtil.isNotBlank(direction)) {
                    orders.add(new Sort.Order(Sort.Direction.valueOf(direction.toUpperCase()), sortDTO.getFieldName()));
                }

            });
            pageable = PageRequest.of(pageIndex, pageSize, Sort.by(orders));
        }

        return pageable;
    }

    public Specification objToSpecWithLogicType(Object domain, String logicType) {
        return (root, criteriaQuery, cb) -> {
            List<Field> fields = Arrays.asList(ReflectUtil.getFields(domain.getClass()));
            List<Predicate> predicates = new ArrayList(fields.size());
            Iterator var7 = fields.iterator();

            while(true) {
                Field field;
                CommonJpaQueryWord qw;
                do {
                    if (!var7.hasNext()) {
                        Predicate p = null;
                        if (logicType != null && !logicType.equals("") && !logicType.equals("and")) {
                            if (logicType.equals("or")) {
                                p = cb.or((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
                            }
                        } else {
                            p = cb.and((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
                        }

                        return p;
                    }

                    field = (Field)var7.next();
                    qw = (CommonJpaQueryWord)field.getAnnotation(CommonJpaQueryWord.class);
                } while(qw == null);

                String column = qw.column();
                if (column.equals("")) {
                    column = field.getName();
                }

                field.setAccessible(true);

                try {
                    Object value = field.get(domain);
                    if (value != null || qw.nullble()) {
                        if (value != null && String.class.isAssignableFrom(value.getClass())) {
                            String s = (String)value;
                            if (s.equals("") && !qw.emptyAble()) {
                                continue;
                            }
                        }

                        Path path = root.get(column);
                        switch(qw.func()) {
                            case equal:
                                predicates.add(cb.equal(path, value));
                                break;
                            case like:
                                predicates.add(cb.like(path, "%" + value + "%"));
                                break;
                            case gt:
                                predicates.add(cb.gt(path, (Number)value));
                                break;
                            case lt:
                                predicates.add(cb.lt(path, (Number)value));
                                break;
                            case ge:
                                predicates.add(cb.ge(path, (Number)value));
                                break;
                            case le:
                                predicates.add(cb.le(path, (Number)value));
                                break;
                            case notEqual:
                                predicates.add(cb.notEqual(path, value));
                                break;
                            case notLike:
                                predicates.add(cb.notLike(path, "%" + value + "%"));
                                break;
                            case greaterThan:
                                predicates.add(cb.greaterThan(path, (Comparable)value));
                                break;
                            case greaterThanOrEqualTo:
                                predicates.add(cb.greaterThanOrEqualTo(path, (Comparable)value));
                                break;
                            case lessThan:
                                predicates.add(cb.lessThan(path, (Comparable)value));
                                break;
                            case lessThanOrEqualTo:
                                predicates.add(cb.lessThanOrEqualTo(path, (Comparable)value));
                        }
                    }
                } catch (Exception var13) {
                    var13.printStackTrace();
                    logger.error(var13.getMessage());
                }
            }
        };
    }

    public <D extends CommonDomain> Specification<D> toSpecWithLogicType(D main, String logicType) {
        return this.objToSpecWithLogicType(main, logicType);
    }

    private static class CommonJpaPageUtilHolder {
        private static CommonJpaPageUtil instance = new CommonJpaPageUtil();

        private CommonJpaPageUtilHolder() {
        }
    }
}