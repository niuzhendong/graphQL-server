package com.niuzhendong.graphql.common.base;

import com.niuzhendong.graphql.common.domain.BaseDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepository<D extends BaseDomain> extends IBaseCommonRepository<D>, JpaRepository<D, String>, JpaSpecificationExecutor<D> {
}
