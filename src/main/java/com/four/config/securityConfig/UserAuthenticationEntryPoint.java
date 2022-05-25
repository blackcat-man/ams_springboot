package com.four.config.securityConfig;


import com.alibaba.fastjson.JSON;
import com.four.controller.common.R;
import com.four.utils.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 匿名未登录的时候访问，遇到需要登录认证的时候被调用
 */
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 未登录时，访问需要登录认证的资源
        ResponseUtils.responseJson(httpServletResponse,R.unLogin());
    }
}
