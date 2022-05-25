package com.four.config.securityConfig;

import com.four.controller.common.R;
import com.four.entity.SecurityUser;
import com.four.utils.JwtUtils;
import com.four.utils.RedisUtils;
import com.four.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登录成功处理
 */
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        // 获取当前用户
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        // 将用户身份写到上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 将用户信息存入redis
        redisUtils.save(user.getUsername(),authentication);

        // 生成token并保存到redis中
        String token = jwtUtils.generateToken(user.getUsername());
        redisUtils.save(user.getUsername(), token);

        // 清楚redis验证码缓存
        redisUtils.deleteCode(httpServletRequest.getParameter("pCode"));

        // 写响应头
        httpServletResponse.setHeader("token",token);

        // 成功响应
        ResponseUtils.responseJson(httpServletResponse, R.success("登录成功！",user));
    }
}
