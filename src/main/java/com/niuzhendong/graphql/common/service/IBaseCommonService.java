package com.niuzhendong.graphql.common.service;

import cn.hutool.core.util.StrUtil;
import com.niuzhendong.graphql.common.Exception.CommonException;
import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.BaseCommonDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface IBaseCommonService<DTO extends BaseCommonDTO, D extends CommonDomain> extends ICommonService<DTO, D> {
    @Cacheable(
            value = {"CommonDTOCache"},
            keyGenerator = "baseCacheKeyGenerator",
            unless = "#result==null"
    )
    DTO findById(String id);

    List<DTO> findByIds(List<String> ids);

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default DTO beforeCreate(DTO dto) {
        if (dto == null) {
            throw new CommonException("参数不能为null", new Object[0]);
        } else if (StrUtil.isNotBlank(dto.getId())) {
            throw new CommonException("新增时候Id属性不能有值,此方法只能用于新增操作，更新请调用update方法", new Object[0]);
        } else {
            return dto;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Boolean beforeBatchCreate(List<DTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            throw new CommonException("要新增的集合不能为空", new Object[0]);
        } else {
            Iterator var2 = dtoList.iterator();

            while(var2.hasNext()) {
                DTO dto = (DTO) var2.next();
                this.beforeCreate(dto);
            }

            return true;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default DTO create(DTO dto) {
        return this.beforeCreate(dto);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Boolean batchCreate(List<DTO> dtoList) {
        return this.beforeBatchCreate(dtoList);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default DTO afterCreate(DTO dto) {
        return dto;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Boolean afterBatchCreate(List<DTO> dtoList) {
        return true;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Boolean beforeRemove(String id) {
        if (StrUtil.isBlank(id)) {
            throw new CommonException("ID不能为空", new Object[0]);
        } else {
            return true;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Boolean beforeBatchRemove(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new CommonException("要删除的ID集合不能为空", new Object[0]);
        } else {
            Iterator var2 = ids.iterator();

            while(var2.hasNext()) {
                String id = (String)var2.next();
                this.beforeRemove(id);
            }

            return true;
        }
    }

    @CacheEvict(
            value = {"CommonDTOCache"},
            keyGenerator = "baseCacheKeyGenerator"
    )
    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Boolean removeById(String id) {
        return this.beforeRemove(id);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Boolean removeByIds(List<String> ids) {
        return this.beforeBatchRemove(ids);
    }

    @CacheEvict(
            value = {"CommonDTOCache"},
            keyGenerator = "baseCacheKeyGenerator"
    )
    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Boolean remove(DTO dto) {
        return this.removeById(dto.getId());
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Boolean batchRemove(List<DTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            throw new CommonException("要删除的对象集合不能为空", new Object[0]);
        } else {
            List<String> idList = new ArrayList();
            dtoList.forEach((t) -> {
                idList.add(t.getId());
            });
            return this.removeByIds(idList);
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Boolean afterRemove(String id) {
        return true;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Boolean afterBatchRemove(List<String> ids) {
        return true;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default DTO beforeUpdate(DTO dto) {
        if (dto != null && StrUtil.isBlank(dto.getId())) {
            throw new CommonException("ID不能为空", new Object[0]);
        } else {
            return dto;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    @CachePut(
            value = {"CommonDTOCache"},
            keyGenerator = "baseCacheKeyGenerator"
    )
    default DTO update(DTO dto) {
        return this.beforeUpdate(dto);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default DTO afterUpdate(DTO dto) {
        return dto;
    }
}
