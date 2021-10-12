package com.niuzhendong.graphql.service.impl;


import com.niuzhendong.graphql.common.base.BaseServiceImpl;
import com.niuzhendong.graphql.domain.LoginLog;
import com.niuzhendong.graphql.domain.LoginLogRepository;
import com.niuzhendong.graphql.dto.LoginLogDTO;
import com.niuzhendong.graphql.service.ILoginLogService;
import org.springframework.stereotype.Service;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/5/12
 * @describe
 */
@Service
public class LoginLoginServiceImpl extends BaseServiceImpl<LoginLogRepository, LoginLog, LoginLogDTO> implements ILoginLogService<LoginLog> {


}
