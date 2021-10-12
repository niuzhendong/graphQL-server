package com.niuzhendong.graphql.common.base;

import com.niuzhendong.graphql.common.domain.BaseCommonDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
interface IBaseCommonRepository<D extends BaseCommonDomain> extends ICommonDaoRepository<D>, JpaRepository<D, String>, JpaSpecificationExecutor<D> {
}

