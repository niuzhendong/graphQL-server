package com.niuzhendong.graphql.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niuzhendong.graphql.common.Exception.SmsSecurityException;
import com.niuzhendong.graphql.common.dto.ResultDTO;
import com.niuzhendong.graphql.common.handler.SmsCodeHandler;
import com.niuzhendong.graphql.common.security.BaseAuthenticationToken;
import com.niuzhendong.graphql.common.security.ResponseHandler;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private SmsCodeHandler smsCodeHandler;
    private ResponseHandler responseHandle;

    public SmsAuthenticationFilter() {
        super(new OrRequestMatcher(new RequestMatcher[]{new AntPathRequestMatcher("/smsSendCode", "GET"), new AntPathRequestMatcher("/smsLogin", "POST")}));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (request.getMethod().equals("GET")) {
            String phoneNum = request.getParameter("phoneNum");

            try {
                ResultDTO resultDTO = this.smsCodeHandler.sendSmsCode(phoneNum);
                this.responseHandle.success(request, response, resultDTO);
            } catch (Exception var8) {
                this.responseHandle.fail(request, response, var8);
            }

            return null;
        } else {
            Map body;
            try {
                body = this.getBody(request);
            } catch (Exception var10) {
                this.responseHandle.fail(request, response, new SmsSecurityException(5007, "请使用body传参", new Object[0]));
                return null;
            }

            Boolean check = this.checkParameter(request, response, body);
            if (!check) {
                return null;
            } else {
                String phoneNum = this.obtainPhoneNum(body);
                body.remove("phoneNum");
                BaseAuthenticationToken authRequest = new BaseAuthenticationToken(phoneNum, body);
                this.setDetails(request, authRequest);

                try {
                    return this.getAuthenticationManager().authenticate(authRequest);
                } catch (Exception var9) {
                    this.responseHandle.fail(request, response, var9);
                    return null;
                }
            }
        }
    }

    private Boolean checkParameter(HttpServletRequest request, HttpServletResponse response, Map<String, String> body) {
        if (body.containsKey("phoneCode") && body.containsKey("phoneCodeKey") && body.containsKey("phoneNum")) {
            return true;
        } else {
            String errorMsg = LogMessage.format("必须包含以下参数 phoneNum=%s , phoneCode=%s , phoneCodeKey=%s", body.get("phoneNum"), body.get("phoneCode"), body.get("phoneCodeKey")).toString();
            this.responseHandle.fail(request, response, new SmsSecurityException(5006, errorMsg, new Object[0]));
            return false;
        }
    }

    protected String obtainPhoneNum(Map<String, String> data) {
        String phoneNum = (String)data.get("phoneNum");
        if (phoneNum == null) {
            phoneNum = "";
        }

        return phoneNum.trim();
    }

    protected void setDetails(HttpServletRequest request, BaseAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    private Map<String, String> getBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String str = "";

        String bodyStr;
        for(bodyStr = ""; (str = reader.readLine()) != null; bodyStr = bodyStr + str) {
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = (Map)mapper.readValue(bodyStr, Map.class);
        return data;
    }

    public SmsCodeHandler getSmsCodeHandler() {
        return this.smsCodeHandler;
    }

    public void setSmsCodeHandler(SmsCodeHandler smsCodeHandler) {
        this.smsCodeHandler = smsCodeHandler;
    }

    public ResponseHandler getResponseHandle() {
        return this.responseHandle;
    }

    public void setResponseHandle(ResponseHandler responseHandle) {
        this.responseHandle = responseHandle;
    }
}
