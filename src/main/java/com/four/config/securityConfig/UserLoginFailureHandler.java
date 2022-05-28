package com.four.config.securityConfig;

import com.four.controller.common.R;
import com.four.utils.RedisUtils;
import com.four.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败时调用
 */
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private RedisUtils redisUtils;
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        redisUtils.deleteCode(httpServletRequest.getParameter("pCode"));
        ResponseUtils.responseJson(httpServletResponse,R.error("用户名或密码错误"));
    }
}
