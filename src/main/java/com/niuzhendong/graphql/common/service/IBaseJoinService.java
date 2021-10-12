package com.niuzhendong.graphql.common.service;

import com.niuzhendong.graphql.common.annotation.BaseJoinId.Index;
import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.BaseJoinDTO;
import com.niuzhendong.graphql.common.utils.JoinIDUtil;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public interface IBaseJoinService<DTO extends BaseJoinDTO, D extends CommonDomain> extends IBaseCommonService<DTO, D> {
    default DTO beforeCreate(DTO dto) {
        super.beforeCreate(dto);
        D domain = this.dtoToDomain(dto, true);
        dto.setId(JoinIDUtil.generateJoinId(domain, this.getDomainIdMethodGetMap()));
        return dto;
    }

    List<String> getDomainIdFieldNames();

    Map<String, Method> getDomainIdMethodGetMap();

    List<String> findFirstIdsBySecondIds(List<String> secondIds);

    List<String> findSecondIdsByFirstIds(List<String> firstIds);

    @Cacheable(
            value = {"CommonDTOCache"},
            keyGenerator = "baseCacheKeyGenerator",
            unless = "#result==null"
    )
    List<String> findFirstIdsBySecondId(String secondId);

    @Cacheable(
            value = {"CommonDTOCache"},
            keyGenerator = "baseCacheKeyGenerator",
            unless = "#result==null"
    )
    List<String> findSecondIdsByFirstId(String firstId);

    @Cacheable(
            value = {"CommonDTOCache"},
            keyGenerator = "baseCacheKeyGenerator",
            unless = "#result==null"
    )
    List<String> findIdsByJoinIds(List<String> joinIds, Index joinIndex);

    @Cacheable(
            value = {"CommonDTOCache"},
            keyGenerator = "baseCacheKeyGenerator",
            unless = "#result==null"
    )
    List<String> findIdsByJoinId(String joinId, Index joinIndex);
}
