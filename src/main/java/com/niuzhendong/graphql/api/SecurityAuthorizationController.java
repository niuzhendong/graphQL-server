package com.niuzhendong.graphql.api;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/4/15
 * @describe
 * 该类只为在swagger中展示登陆相关文档,实际上在swagger中发起请求时走的是security的自定义filter
 */
@RestController
@Api(tags = {"01.登录"})
@ApiSupport(order = -1)
public class SecurityAuthorizationController {

    @ApiOperation(value = "1.用户名密码登录")
    @ApiOperationSupport(order = 1)
    @PostMapping(value = "/login")
    public void userNamePassWord(String username,String password) {

    }

    @ApiOperation(value = "2.发送短信验证码")
    @ApiOperationSupport(order = 2)
    @GetMapping(value = "/smsSendCode")
    public void smsSendCode(String phoneNum) {

    }

    @ApiOperation(value = "3.短信验证码登录",notes = "默认报文格式是body  {\"phoneNum\":\"手机号\",\"phoneCode\": \"短信验证码\",\"phoneCodeKey\": \"发送短信验证码返回的随机数\"}")
    @ApiOperationSupport(order = 3)
    @PostMapping(value = "/smsLogin")
    public void smsLogin(@RequestBody String phoneNum, @RequestBody String phoneCode,@RequestBody String phoneCodeKey) {

    }
    @ApiOperation(value = "4.二维码登录第一步",notes = "生成登录的二维码,前端可以根据qrcodeId使用js动态生成二维码图片")
    @ApiOperationSupport(order = 4)
    @PostMapping(value = "/qrcodeId")
    public void qrcodeId() {

    }
    @ApiOperation(value = "5.二维码登录第二步",notes = "移动端扫描二维码后调用此接口，告知服务端允许PC登录登录")
    @ApiOperationSupport(order = 5)
    @PostMapping(value = "/qrcodeAuth")
    public void qrcodeAuth() {

    }

    @ApiOperation(value = "6.二维码登录第三步",notes = "PC端轮询调用，检测手机端是否已经允许登录")
    @ApiOperationSupport(order = 6)
    @PostMapping(value = "/qrcodeTryAuth")
    public void qrcodeTryAuth() {

    }
}
