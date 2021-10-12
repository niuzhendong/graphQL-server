package com.niuzhendong.graphql.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niuzhendong.graphql.common.Exception.BaseException;
import com.niuzhendong.graphql.common.dto.ResultDTO;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseDefaultSecurityResponseHandler implements ResponseHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @SneakyThrows
    public void fail(HttpServletRequest request, HttpServletResponse response, Exception e) {
        try {
            this.logger.error(e.getMessage());
            e.printStackTrace();
            ResultDTO<Object> resultDTO = new ResultDTO();
            Integer code = 500;
            if (e instanceof BaseException) {
                code = ((BaseException)e).getCode();
                resultDTO.setMessage(e.getMessage());
            }

            response.setStatus(500);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            resultDTO.setCode(code);
            String resultToken = (new ObjectMapper()).writeValueAsString(resultDTO);
            response.getWriter().write(resultToken);
        } catch (Throwable var7) {
            throw var7;
        }
    }

    @SneakyThrows
    public void success(HttpServletRequest request, HttpServletResponse response, ResultDTO resultDTO) {
        try {
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            String resultToken = (new ObjectMapper()).writeValueAsString(resultDTO);
            response.getWriter().write(resultToken);
        } catch (Throwable var5) {
            throw var5;
        }
    }
}
