package com.niuzhendong.graphql.common.base;

import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.domain.ICommonRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICommonDaoRepository<D extends CommonDomain> extends ICommonRepository<D>, JpaRepository<D, String>, JpaSpecificationExecutor<D> {
}
