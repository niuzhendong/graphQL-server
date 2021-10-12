package com.niuzhendong.graphql.common.dto;

import lombok.Data;
import org.springframework.util.StringUtils;
import cn.hutool.json.JSONUtil;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Data
public class BaseAuthenticationRequestDTO implements Serializable {
    private static final long serialVersionUID = -8445943548965154778L;
    private String username;
    private String password;
    private String captchaKey;
    private String captchaCode;

    public BaseAuthenticationRequestDTO(HttpServletRequest request, String usernameParameter, String passwordParameter) {
        String username = request.getParameter(usernameParameter);
        if (StringUtils.isEmpty(username)) {
            String json = getPostData(request);
            Map<String, String> map = (Map)JSONUtil.toBean(json, Map.class);
            this.username = (String)map.get(usernameParameter);
            this.password = (String)map.get(passwordParameter);
            this.captchaKey = (String)map.get("captchaKey");
            this.captchaCode = (String)map.get("captchaCode");
        } else {
            this.username = request.getParameter(usernameParameter);
            this.password = request.getParameter(passwordParameter);
            this.captchaKey = request.getParameter("captchaKey");
            this.captchaCode = request.getParameter("captchaCode");
        }

    }

    private static String getPostData(HttpServletRequest request) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;

        try {
            reader = request.getReader();

            while(null != (line = reader.readLine())) {
                data.append(line);
            }
        } catch (IOException var8) {
        } finally {
            ;
        }

        return data.toString();
    }

    public BaseAuthenticationRequestDTO(final String username, final String password, final String captchaKey, final String captchaCode) {
        this.username = username;
        this.password = password;
        this.captchaKey = captchaKey;
        this.captchaCode = captchaCode;
    }
}
