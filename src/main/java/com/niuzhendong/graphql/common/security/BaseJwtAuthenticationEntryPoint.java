package com.niuzhendong.graphql.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niuzhendong.graphql.common.dto.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static Logger logger = LoggerFactory.getLogger(BaseJwtAuthenticationEntryPoint.class);

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(401);
        ObjectMapper mapper = new ObjectMapper();
        ResultDTO ResultDTO = new ResultDTO(401, authException.getMessage(), "");
        logger.error("JwtAuthenticationEntryPoint", authException);
        response.getWriter().append(mapper.writeValueAsString(ResultDTO));
    }
}
