package com.niuzhendong.graphql.log;

import cn.com.taiji.log.domain.WebLogs;
import cn.com.taiji.log.dto.WebLogsDTO;
import cn.com.taiji.log.service.impl.WebLogsServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

@Service
@Primary
public class SampleWebLogsServiceImpl extends WebLogsServiceImpl {
    @Override
    public WebLogs createDomain() {
        return new WebLogs();
    }

    @Override
    public WebLogsDTO createDTO() {
        return new WebLogsDTO();
    }

    @Override
    public WebLogsDTO beforeSaveLog(JoinPoint joinPoint, Map<String,Object> param) throws SocketException, UnknownHostException, ClassNotFoundException {
        WebLogsDTO webLogsDTO = super.beforeSaveLog(joinPoint, param);
        // 可以在这里修改tdf-log的默认记录日志逻辑
        // WebLogsDTO中的r1、r2...r5为扩展字段，用户可以自定义扩展字段的业务逻辑，保存到数据库中
        if (webLogsDTO != null) {
            webLogsDTO.setR1("您的自定义字段属性");
        }
        return webLogsDTO;
    }
}
