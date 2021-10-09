package com.niuzhendong.graphql.conf;

import cn.hutool.core.collection.ListUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config implements WebMvcConfigurer {
    private final String apiKeyName = "普通登录或者OAuth2登录后获取的的Bearer Token";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("swagger-ui.html", "doc.html");
    }

    /**
     * 主要是这个方法，其他的方法是抽出去的
     * 在 basePackage 里面写需要生成文档的 controller 路径
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.com.taiji.sys.api")
                        .or(RequestHandlerSelectors.basePackage("com.sample.tdf.api"))
                        .or(RequestHandlerSelectors.basePackage("cn.com.taiji.log.api"))
                        .or(RequestHandlerSelectors.basePackage("cn.com.taiji.security.api"))
                        .or(RequestHandlerSelectors.basePackage("cn.com.taiji.security.jwt.api")))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(ListUtil.of(securityContextNormal()))
                .securitySchemes(ListUtil.of(normalApiKey()));
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "开发框架",
                "获取swagger的token：" +
                        "curl -i -X POST -d \"username=admin&password=123456&grant_type=password&client_id=swagger&client_secret=swagger\" http://localhost:8769/oauth/token " +
                        "可以使用curl命令或者postman等发起post请求，获取token,然后粘贴到swagger的Authorize中，注意前面要加上Bearer 前缀，并且用空格隔开",
                "1.0.0",
                "http://localhost:7779/",
                new Contact("", "xxx", "xxx@email.com.cn"),
                "License of API", "API license URL", Collections.emptyList());
    }

    // 普通登录swagger
    private ApiKey normalApiKey() {
        return new ApiKey(apiKeyName, "Authorization", "header");
    }

    private SecurityContext securityContextNormal() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes=  {new AuthorizationScope("global", "accessEverything")};
        return ListUtil.of(new SecurityReference(apiKeyName, authorizationScopes));
    }
}
