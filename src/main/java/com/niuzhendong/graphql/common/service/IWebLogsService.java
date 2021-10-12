package com.niuzhendong.graphql.common.service;

import com.niuzhendong.graphql.common.annotation.OperationLogAnno;
import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.WebLogsDTO;
import com.niuzhendong.graphql.common.utils.WebSiteUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Map;

public interface IWebLogsService<D extends CommonDomain> extends IBaseService<WebLogsDTO, D> {
    default WebLogsDTO beforeSaveLog(JoinPoint joinPoint, Map<String, Object> param) throws ClassNotFoundException, SocketException, UnknownHostException {
        LocalDateTime startTime = (LocalDateTime)param.get("startTime");
        String currentLoginName = (String)param.get("currentLoginName");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        } else {
            HttpServletRequest request = attributes.getRequest();
            String remoteClientIp = WebSiteUtil.getIpAddress(request);
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            WebLogsDTO webLogsDTO = new WebLogsDTO();
            webLogsDTO.setStartTime(startTime);
            webLogsDTO.setEndTime(LocalDateTime.now());
            webLogsDTO.setLogUserName(currentLoginName);
            webLogsDTO.setLogMethodName(methodName);
            webLogsDTO.setLogClientIp(remoteClientIp);
            webLogsDTO.setLogServerIp(WebSiteUtil.getLocalIP());
            Method[] var14 = methods;
            int var15 = methods.length;

            for(int var16 = 0; var16 < var15; ++var16) {
                Method method = var14[var16];
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        if (method.getAnnotation(OperationLogAnno.class) != null) {
                            webLogsDTO.setModule(((OperationLogAnno)method.getAnnotation(OperationLogAnno.class)).module());
                            webLogsDTO.setLogInfo(((OperationLogAnno)method.getAnnotation(OperationLogAnno.class)).logInfo());
                            webLogsDTO.setLogClassName(((OperationLogAnno)method.getAnnotation(OperationLogAnno.class)).logClassName());
                            webLogsDTO.setLogOperationType(((OperationLogAnno)method.getAnnotation(OperationLogAnno.class)).logOperationType());
                            webLogsDTO.setRemark(((OperationLogAnno)method.getAnnotation(OperationLogAnno.class)).desc());
                        } else {
                            webLogsDTO.setModule(targetName);
                            webLogsDTO.setLogInfo("执行了" + targetName + "的'" + methodName + "'操作");
                            webLogsDTO.setLogClassName(targetName);
                            if (!methodName.toLowerCase().contains("list") && !methodName.toLowerCase().contains("page") && !methodName.toLowerCase().contains("tree")) {
                                if (!methodName.toLowerCase().contains("save") && !methodName.toLowerCase().contains("edit") && !methodName.toLowerCase().contains("add")) {
                                    if (methodName.toLowerCase().contains("delete")) {
                                        webLogsDTO.setLogOperationType("update");
                                    }
                                } else {
                                    webLogsDTO.setLogOperationType("save");
                                }
                            } else {
                                webLogsDTO.setLogOperationType("query");
                            }

                            webLogsDTO.setRemark("qwewqe");
                        }
                    }
                }
            }

            return webLogsDTO;
        }
    }

    default WebLogsDTO saveLog(JoinPoint joinPoint, Map<String, Object> param) throws SocketException, UnknownHostException, ClassNotFoundException {
        WebLogsDTO webLogsDTO = this.beforeSaveLog(joinPoint, param);
        return webLogsDTO != null ? (WebLogsDTO)this.create(webLogsDTO) : null;
    }
}
