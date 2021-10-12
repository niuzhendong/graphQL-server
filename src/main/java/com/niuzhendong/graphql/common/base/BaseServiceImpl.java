package com.niuzhendong.graphql.common.base;

import com.niuzhendong.graphql.common.Exception.BaseException;
import com.niuzhendong.graphql.common.domain.BaseDomain;
import com.niuzhendong.graphql.common.dto.BaseDTO;
import com.niuzhendong.graphql.common.service.CommonSecurityService;
import com.niuzhendong.graphql.common.service.IBaseService;
import com.niuzhendong.graphql.common.utils.CommonCacheUtil;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class BaseServiceImpl<I extends IBaseRepository<D>, D extends BaseDomain, DTO extends BaseDTO> extends BaseCommonServiceImpl<I, D, DTO> implements IBaseService<DTO, D> {


    public Boolean removeById(String id) throws BaseException {
        super.removeById(id);
        Optional<D> old = ((IBaseRepository)this.iBaseRepository).findById(id);
        if (old.isPresent()) {
            BaseDomain oldDomain = (BaseDomain)old.get();
            oldDomain.setFlag((Integer)null);
            oldDomain.setModifiedBy(CommonSecurityService.instance.getCurrentLoginName());
            oldDomain.setModifiedDate(LocalDateTime.now());
            BaseDomain var10000 = (BaseDomain)((IBaseRepository)this.iBaseRepository).saveAndFlush(oldDomain);
        }

        this.afterRemove(id);
        return true;
    }

    public Boolean removeByIds(List<String> ids) throws BaseException {
        super.removeByIds(ids);
        Set<String> cacheKeys = new HashSet();
        List<D> allById = ((IBaseRepository)this.iBaseRepository).findAllById(ids);
        if (!CollectionUtils.isEmpty(allById)) {
            allById.forEach((entity) -> {
                entity.setFlag((Integer)null);
                cacheKeys.add("CommonDTOCache::" + CommonCacheUtil.getCacheKey(this.getDTOClass().getSimpleName(), entity.getId()));
            });
            ((IBaseRepository)this.iBaseRepository).saveAll(allById);
            this.commonCacheUtil.mremove(cacheKeys);
        }

        this.afterBatchRemove(ids);
        return true;
    }
}
