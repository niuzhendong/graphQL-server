package com.niuzhendong.graphql.common.base;

import cn.hutool.core.collection.CollectionUtil;
import com.niuzhendong.graphql.common.Exception.BaseException;
import com.niuzhendong.graphql.common.Exception.CommonException;
import com.niuzhendong.graphql.common.domain.BaseCommonDomain;
import com.niuzhendong.graphql.common.domain.BaseDomain;
import com.niuzhendong.graphql.common.domain.BaseJoinDomain;
import com.niuzhendong.graphql.common.dto.BaseCommonDTO;
import com.niuzhendong.graphql.common.dto.BaseJoinDTO;
import com.niuzhendong.graphql.common.service.CommonSecurityService;
import com.niuzhendong.graphql.common.service.IBaseCommonService;
import com.niuzhendong.graphql.common.utils.CommonBeanUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class BaseCommonServiceImpl<I extends IBaseCommonRepository<D>, D extends BaseCommonDomain, DTO extends BaseCommonDTO> extends CommonServiceImpl<I, D, DTO> implements IBaseCommonService<DTO, D> {

    public DTO findById(String id) throws BaseException {
        Optional<D> t = ((IBaseCommonRepository)this.iBaseRepository).findById(id);
        return !t.isPresent() ? null : (DTO) this.domainToDTO((D)t.get(), true);
    }

    public List<DTO> findByIds(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return new ArrayList();
        } else {
            List<D> ts = ((IBaseCommonRepository)this.iBaseRepository).findAllById(ids);
            List<DTO> dtos = this.domainListToDTOList(ts);
            return dtos;
        }
    }

    public DTO create(DTO dto) throws BaseException {
        dto = IBaseCommonService.super.create(dto);
        D t = (D)this.dtoToDomain(dto, true);
        t = (D)((IBaseCommonRepository)this.iBaseRepository).saveAndFlush(t);
        if (dto instanceof BaseJoinDTO) {
            this.deleteCache();
        }

        this.afterCreate(dto);
        return (DTO)this.domainToDTO(t);
    }

    public Boolean batchCreate(List<DTO> dtoList) throws BaseException {
        IBaseCommonService.super.batchCreate(dtoList);
        List<D> ts = new ArrayList();
        dtoList.forEach((dto) -> {
            D t = (D)this.dtoToDomain(dto, true);
            ts.add(t);
        });
        ((IBaseCommonRepository)this.iBaseRepository).saveAll(ts);
        ((IBaseCommonRepository)this.iBaseRepository).flush();
        if (CollectionUtil.isNotEmpty(dtoList) && dtoList.get(0) instanceof BaseJoinDTO) {
            this.deleteCache();
        }

        this.afterBatchCreate(dtoList);
        return true;
    }

    public DTO update(DTO dto) throws BaseException {
        dto = IBaseCommonService.super.update(dto);
        dto = this.commonUpdate(dto);
        return this.afterUpdate(dto);
    }

    protected final DTO commonUpdate(DTO dto) {
        BaseCommonDomain t = (BaseCommonDomain)this.dtoToDomain(dto, false);
        String id = t.getId();
        Optional<D> old = ((IBaseCommonRepository)this.iBaseRepository).findById(id);
        if (old.isPresent()) {
            BaseCommonDomain oldDomain = (BaseCommonDomain)old.get();
            if (oldDomain instanceof BaseDomain) {
                ((BaseDomain)oldDomain).setModifiedBy(CommonSecurityService.instance.getCurrentLoginName());
                ((BaseDomain)oldDomain).setModifiedDate(LocalDateTime.now());
            } else if (oldDomain instanceof BaseJoinDomain) {
                this.deleteCache();
            }

            CommonBeanUtil.updateCopy(t, oldDomain);
            D rd = (D)((IBaseCommonRepository)this.iBaseRepository).saveAndFlush(oldDomain);
            return (DTO)this.domainToDTO(rd);
        } else {
            throw new CommonException("没有找到此对象，无法更新", new Object[0]);
        }
    }
}