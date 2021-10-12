package com.niuzhendong.graphql.common.service;

import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.CommonDTO;
import com.niuzhendong.graphql.common.dto.PageDTO;
import com.niuzhendong.graphql.common.utils.CommonBeanUtil;

import java.util.ArrayList;
import java.util.List;

public interface ICommonService<DTO extends CommonDTO, D extends CommonDomain> {
    String CacheKey_dto = "CommonDTOCache";

    Class<D> getDomainClass();

    Class<DTO> getDTOClass();

    default CommonDomain createDomain() {
        try {
            CommonDomain domain = (CommonDomain)this.getDomainClass().newInstance();
            return domain;
        } catch (IllegalAccessException | InstantiationException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    default DTO createDTO() {
        try {
            DTO dto = (DTO)this.getDTOClass().newInstance();
            return dto;
        } catch (IllegalAccessException | InstantiationException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    default DTO domainToDTO(D domain, Boolean isCopyEmptyField) {
        if (domain == null) {
            return null;
        } else {
            DTO dto = this.createDTO();
            if (isCopyEmptyField) {
                CommonBeanUtil.saveCopy(domain, dto);
            } else {
                CommonBeanUtil.updateCopy(domain, dto);
            }

            return dto;
        }
    }

    default D dtoToDomain(DTO dto, Boolean isCopyEmptyField) {
        if (dto == null) {
            return null;
        } else {
            D t = (D) this.createDomain();
            if (isCopyEmptyField) {
                CommonBeanUtil.saveCopy(dto, t);
            } else {
                CommonBeanUtil.updateCopy(dto, t);
            }

            return t;
        }
    }

    default List<DTO> domainListToDTOList(List<D> dList, Boolean isCopyEmptyField) {
        List<DTO> dtoList = new ArrayList();
        dList.forEach((d) -> {
            dtoList.add(this.domainToDTO(d, isCopyEmptyField));
        });
        return dtoList;
    }

    default List<D> dtoListToDomainList(List<DTO> dtoList, Boolean isCopyEmptyField) {
        List<D> dList = new ArrayList();
        dtoList.forEach((dto) -> {
            dList.add(this.dtoToDomain(dto, isCopyEmptyField));
        });
        return dList;
    }

    PageDTO<DTO> getPage(final PageDTO<DTO> pageDTO);
}
