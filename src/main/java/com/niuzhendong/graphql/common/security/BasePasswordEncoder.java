package com.niuzhendong.graphql.common.security;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BasePasswordEncoder extends BCryptPasswordEncoder {
    @Value("${base.security.password.enableSM4:false}")
    private Boolean enableSM4;
    @Value("${base.security.password.SM4SecretKey:SM4SecretKey0000}")
    private String SM4SecretKey;

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (this.enableSM4) {
            SymmetricCrypto sm4 = SmUtil.sm4(this.SM4SecretKey.getBytes());
            rawPassword = sm4.decryptStr(((CharSequence)rawPassword).toString(), CharsetUtil.CHARSET_UTF_8);
        }

        return super.matches((CharSequence)rawPassword, encodedPassword);
    }

    public String decoder(CharSequence rawPassword) {
        if (this.enableSM4) {
            SymmetricCrypto sm4 = SmUtil.sm4(this.SM4SecretKey.getBytes());
            rawPassword = sm4.decryptStr(((CharSequence)rawPassword).toString(), CharsetUtil.CHARSET_UTF_8);
        }

        return ((CharSequence)rawPassword).toString();
    }
}
