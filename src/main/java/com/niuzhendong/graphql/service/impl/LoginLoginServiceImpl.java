package com.niuzhendong.graphql.service.impl;

import cn.com.taiji.common.base.BaseServiceImpl;
import com.sample.tdf.domain.LoginLog;
import com.sample.tdf.domain.LoginLogRepository;
import com.sample.tdf.dto.LoginLogDTO;
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
