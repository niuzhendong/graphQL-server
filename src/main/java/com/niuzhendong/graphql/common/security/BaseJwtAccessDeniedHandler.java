package com.niuzhendong.graphql.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niuzhendong.graphql.common.dto.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseJwtAccessDeniedHandler implements AccessDeniedHandler {
    private static Logger logger = LoggerFactory.getLogger(BaseJwtAccessDeniedHandler.class);

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        logger.debug("JwtAccessDeniedHandler,{}", accessDeniedException);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(401);
        ObjectMapper mapper = new ObjectMapper();
        ResultDTO ResultDTO = new ResultDTO(401, accessDeniedException.getMessage(), "");
        logger.debug("JwtAccessDeniedHandler,return {}", ResultDTO);
        response.getWriter().append(mapper.writeValueAsString(ResultDTO));
    }
}
