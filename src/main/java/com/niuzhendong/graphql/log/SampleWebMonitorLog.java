package com.niuzhendong.graphql.log;

import cn.com.taiji.log.service.IWebLogsService;
import cn.hutool.core.map.MapUtil;
import com.niuzhendong.graphql.common.service.CommonSecurityService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//自定义日志切面。模仿 tdf-log模块中的 cn.com.taiji.log.monitor.WebMonitorLog，扩展或者修改tdf-log中默认的日志切面
//注意自定义切面不要与tdf-log默认的切面有重叠，否则会重复记录日志
@Aspect
@Component
public class SampleWebMonitorLog {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //TODO zhaozhao这样定义变量不支持多线程，需要用Local符号修饰
    private LocalDateTime startTime;

    @Autowired
    private IWebLogsService iWebLogsService;

    /**
     * 前置通知，在目标方法完成之后调用通知，此时不会关 心方法的输出是什么 方法调用前触发 -记录开始时间
     */
    //@Before("bean(*Controller)")
    //@Before("within(cn.com.taiji.common.api.BaseController+)")
    @Before("within(com.sample.tdf.*.api..*)")
    public void beforeAdvice(JoinPoint joinPoint) {
        startTime = LocalDateTime.now();
    }

    /**
     * 后置通知，在目标方法完成之后调用通知，此时不会关 心方法的输出是什么
     */
    //@After("bean(*Controller)")
    //@After("within(cn.com.taiji.common.api.BaseController+)")
    @After("within(com.sample.tdf.*.api..*)")
    public void afterAdvice(JoinPoint joinPoint) throws Exception {
        //TODO 此切面与tdf-log默认的WebMonitorLog中的切面有重叠，会导致重复记录日志。可以将base.log.lock设置为off以关闭tdf-log的默认日志
        iWebLogsService.saveLog(joinPoint, MapUtil.builder().put("startTime", startTime)
                .put("currentLoginName", CommonSecurityService.instance.getCurrentLoginName()).build());
    }
}
