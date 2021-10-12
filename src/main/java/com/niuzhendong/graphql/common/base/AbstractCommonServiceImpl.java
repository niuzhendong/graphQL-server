package com.niuzhendong.graphql.common.base;

import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.domain.ICommonRepository;
import com.niuzhendong.graphql.common.dto.BaseDTO;
import com.niuzhendong.graphql.common.dto.CommonDTO;
import com.niuzhendong.graphql.common.dto.PageDTO;
import com.niuzhendong.graphql.common.service.ICommonService;
import com.niuzhendong.graphql.common.utils.CommonCacheUtil;
import com.niuzhendong.graphql.common.utils.CommonReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.List;

public abstract class AbstractCommonServiceImpl<I extends ICommonRepository<D>, D extends CommonDomain, DTO extends CommonDTO> implements ICommonService<DTO, D> {

    protected Logger log = LoggerFactory.getLogger(this.getClass());
    protected Class<D> domainClass = (Class<D>) CommonReflectionUtil.getSuperClassGenericType(this.getClass(), 1);
    protected Class<I> plusMapperClass = (Class<I>) CommonReflectionUtil.getSuperClassGenericType(this.getClass(), 0);
    protected Class<DTO> dtoClass = (Class<DTO>) CommonReflectionUtil.getSuperClassGenericType(this.getClass(), 2);
    @Autowired
    protected CommonCacheUtil commonCacheUtil;
    @Autowired
    protected I iBaseRepository;
    @Autowired
    protected MessageSource messageSource;

    public final Class<D> getDomainClass() {
        return this.domainClass;
    }

    public final Class<DTO> getDTOClass() {
        return this.dtoClass;
    }

    public final DTO domainToDTO(D domain) {
        return this.domainToDTO(domain, true);
    }

    public final D dtoToDomain(DTO dto) {
        return this.dtoToDomain(dto, true);
    }

    public final List<DTO> domainListToDTOList(List<D> dList) {
        return this.domainListToDTOList(dList, true);
    }

    public final List<D> dtoListToDomainList(List<DTO> dtoList) {
        return this.dtoListToDomainList(dtoList, true);
    }

    protected D getDomainFilterFromPageDTO(final PageDTO pageDTO) {
        if (pageDTO.getFilters() == null) {
            pageDTO.setFilters(this.createDTO());
        }

        DTO filters = (DTO)pageDTO.getFilters();
        if (filters instanceof BaseDTO) {
            ((BaseDTO)filters).setFlag(1);
        }

        D main = this.dtoToDomain(filters);
        return main;
    }

    protected final void deleteCache() {
        this.commonCacheUtil.removeByPattern("CommonDTOCache::" + this.getDTOClass().getSimpleName() + "*");
    }
}
