package com.niuzhendong.graphql.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niuzhendong.graphql.common.converter.BaseJwtTokenConverter;
import com.niuzhendong.graphql.common.dto.ResultDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseJwtSuccessHandler implements AuthenticationSuccessHandler {

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        BaseJwtTokenConverter jwtTokenConverter = new BaseJwtTokenConverter();
        String token = jwtTokenConverter.generateToken(authentication);
        ResultDTO<Object> resultDTO = new ResultDTO("base " + token);
        String resultToken = (new ObjectMapper()).writeValueAsString(resultDTO);
        response.getWriter().write(resultToken);
    }
}