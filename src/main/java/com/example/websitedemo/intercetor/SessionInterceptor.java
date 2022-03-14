package com.example.websitedemo.intercetor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
public class SessionInterceptor implements WebMvcConfigurer {

    /**
     * 自定义拦截器，添加拦截路径和排除拦截路径
     * addPathPatterns():添加需要拦截的路径
     * excludePathPatterns():添加不需要拦截的路径
     */
    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List list = new ArrayList();
        String filter = "/UserController/toIndex;/UserController/loginUser;/UserController/toRegister;/UserController/register;/error;/static/;" +
                "/UserController/toLogIn;/CommonController/verifyUniqueUser;/**/*.js;/**/*.css";
        String[] filetrArray = filter.split(";");
        list = Arrays.asList(filetrArray);
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**").excludePathPatterns(list);

    }
}