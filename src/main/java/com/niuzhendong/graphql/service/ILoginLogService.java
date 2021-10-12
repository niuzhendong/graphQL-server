package com.niuzhendong.graphql.service;

import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.service.IBaseService;
import com.niuzhendong.graphql.dto.LoginLogDTO;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/5/11
 * @describe
 */
public interface ILoginLogService<D extends CommonDomain> extends IBaseService<LoginLogDTO, D> {

}
