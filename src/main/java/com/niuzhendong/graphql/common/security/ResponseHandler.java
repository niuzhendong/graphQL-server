package com.niuzhendong.graphql.common.security;

import com.niuzhendong.graphql.common.dto.ResultDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ResponseHandler {
    void fail(HttpServletRequest request, HttpServletResponse response, Exception e);

    void success(HttpServletRequest request, HttpServletResponse response, ResultDTO resultDTO);
}
