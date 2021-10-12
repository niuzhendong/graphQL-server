package com.niuzhendong.graphql.common.base;

import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.CommonDTO;
import com.niuzhendong.graphql.common.dto.PageDTO;
import com.niuzhendong.graphql.common.service.ICommonService;
import com.niuzhendong.graphql.common.utils.CommonJpaPageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

public abstract class CommonServiceImpl<I extends ICommonDaoRepository<D>, D extends CommonDomain, DTO extends CommonDTO> extends AbstractCommonServiceImpl<I, D, DTO> implements ICommonService<DTO, D> {

    public PageDTO<DTO> getPage(PageDTO<DTO> pageDTO) {
        PageRequest pageable = CommonJpaPageUtil.getInstance().toPageRequest(pageDTO);
        D domain = this.getDomainFilterFromPageDTO(pageDTO);
        Specification<D> specification = this.toSpecWithLogicType(domain, "and");
        Page<D> pageList = ((ICommonDaoRepository)this.iBaseRepository).findAll(specification, pageable);
        pageDTO.setTotal(pageList.getTotalElements());
        pageDTO.setList(this.domainListToDTOList(pageList.getContent()));
        return pageDTO;
    }

    protected Specification<D> toSpecWithLogicType(D main, String logicType) {
        return CommonJpaPageUtil.getInstance().toSpecWithLogicType(main, logicType);
    }
}
